package com.mouse.controller;

import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.Article;
import com.mouse.domain.vo.ArticleAdminVo;
import com.mouse.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 星星
 * @create 2023-02-19 16:20
 */
@RestController
@RequestMapping("/content")
public class ArticleController {
    //可选分类列表
        //选择分类
    //可选标签接口
        //选择标签
    //上传文件接口
    //发布、草稿接口（新增博文接口）

    @Autowired
    private ArticleService articleService;

    /**
     * 新增博文接口
     * @param articleAdminVo
     * @return
     */
    @PostMapping("/article")
    public ResponseResult article(@RequestBody ArticleAdminVo articleAdminVo){
        return articleService.article(articleAdminVo);
    }

}
