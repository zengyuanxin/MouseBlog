package com.mouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.Article;

/**
 * @author 星星
 * @create 2023-02-03 1:41
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();
}
