package com.mouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.dto.AddArticleDto;
import com.mouse.domain.entity.Article;
import com.mouse.domain.vo.AdminArticleVo;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author 星星
 * @create 2023-02-03 1:41
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    ResponseResult list(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult getArticleById(Integer id);

    ResponseResult updateArticle(AdminArticleVo adminArticleVo);

    ResponseResult delete(Integer id);
}
