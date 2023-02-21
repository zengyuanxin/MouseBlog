package com.mouse.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mouse.constants.RedisConstants;
import com.mouse.constants.SystemConstants;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.dto.AddArticleDto;
import com.mouse.domain.entity.Article;
import com.mouse.domain.entity.ArticleTag;
import com.mouse.domain.entity.Category;
import com.mouse.domain.vo.*;
import com.mouse.mapper.ArticleMapper;
import com.mouse.mapper.ArticleTagMapper;
import com.mouse.service.ArticleService;
import com.mouse.service.ArticleTagService;
import com.mouse.service.CategoryService;
import com.mouse.utils.BeanCopyUtils;
import com.mouse.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 星星
 * @create 2023-02-03 1:42
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    @Lazy
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序

        //todo 从redis中获取viewCount

        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条
        Page<Article> page = new Page(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();
        //bean拷贝
//        List<HotArticleVo> articleVos = new ArrayList<>();
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article,vo);
//            articleVos.add(vo);
//        }

        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(vs);


    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //如果有categoryId 就要 查询时要和传入的相同
        wrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正式发布的
        wrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行排序
        wrapper.orderByDesc(Article::getIsTop,Article::getViewCount);
        //分页查询
        Page<Article> page = new Page<>();
        page(page,wrapper);
        //查询categoryName
        List<Article> articles = page.getRecords();
        //articleId去查询categoryIdName进行设置
        //查询categoryName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
//        for (Article article:articles){
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue(RedisConstants.ARTICLE_VIEWCOUNT, id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装响应返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应 id的浏览量
        redisCache.incrementCacheMapValue(RedisConstants.ARTICLE_VIEWCOUNT,id.toString(),1);
        return ResponseResult.okResult();
    }

    /**
     * 新增博文
     * @param articleDto
     * @return
     */
    @Override
    @Transactional
    public ResponseResult add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);


        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    /**
     * 文章列表
     * @param pageNum
     * @param pageSize
     * @param title
     * @param summary
     * @return
     */
    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, String title, String summary) {
        //查询所有文章中带有指定title或者summary的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果没输入查询条件
        if (!StringUtils.hasText(title)&&!StringUtils.hasText(summary)){
            queryWrapper.orderByAsc(Article::getId);
        }else {
            queryWrapper.like(Article::getTitle,title).or().like(Article::getSummary,summary);
            queryWrapper.orderByAsc(Article::getId);
        }

        //分页查询
        Page<Article> page = new Page(pageNum, pageSize);
        page(page,queryWrapper);

        //封装返回的格式
        List<Article> list = list(queryWrapper);
        List<AdminArticleListVo> adminArticleListVos = BeanCopyUtils.copyBeanList(list, AdminArticleListVo.class);

        return ResponseResult.okResult(new PageVo(adminArticleListVos,page.getTotal()));
    }

    /**
     * 管理员查询文章
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleById(Integer id) {
        //数据库中找到该文章
        Article article = articleService.getById(id);
        //封装返回
        AdminArticleVo adminArticleVo = BeanCopyUtils.copyBean(article, AdminArticleVo.class);
        //此时tags[]为null
        //找到文章所属的标签
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, id);
        queryWrapper.select(ArticleTag::getTagId);
        List<ArticleTag> list = articleTagService.list(queryWrapper);
        List<Long> tags = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            tags.add(list.get(i).getTagId());
        }
        //填充tags
        adminArticleVo.setTags(tags);

        return ResponseResult.okResult(adminArticleVo);
    }

    @Override
    public ResponseResult updateArticle(AdminArticleVo adminArticleVo) {
        //更新文章详情
        Article article = BeanCopyUtils.copyBean(adminArticleVo, Article.class);
        articleService.updateById(article);
        //更新ArticleTag
        List<ArticleTag> articleTags = adminArticleVo.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //删除原来的 博客和标签的关联
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,adminArticleVo.getId());
        articleTagMapper.delete(queryWrapper);
        //添加新的 博客和标签的关联

        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();

    }

    @Override
    public ResponseResult delete(Integer id) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId,id);
        articleMapper.delete(queryWrapper);
        return ResponseResult.okResult();
    }
}
