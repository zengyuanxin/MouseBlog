package com.mouse.job;

import com.mouse.constants.RedisConstants;
import com.mouse.domain.entity.Article;
import com.mouse.service.ArticleService;
import com.mouse.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 星星
 * @create 2023-02-16 15:12
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void updateViewCount(){

        //获取redis中的浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(RedisConstants.ARTICLE_VIEWCOUNT);
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);
    }
}
