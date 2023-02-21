package com.mouse.runner;

import com.mouse.constants.RedisConstants;
import com.mouse.domain.entity.Article;
import com.mouse.mapper.ArticleMapper;
import com.mouse.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 星星
 * @create 2023-02-16 13:48
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 id viewcount
        List<Article> articles = articleMapper.selectList(null);
        Map<String,Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
//最终的map集合要存储到redis当中，而viewcount要自增，long类型不能自增(1L)
        redisCache.setCacheMap(RedisConstants.ARTICLE_VIEWCOUNT,viewCountMap);

        //存储到redis中
    }
}
