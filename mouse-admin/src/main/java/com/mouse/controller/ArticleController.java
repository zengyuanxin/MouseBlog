package com.mouse.controller;

import com.mouse.domain.ResponseResult;
import com.mouse.domain.dto.AddArticleDto;
import com.mouse.domain.vo.AdminArticleVo;
import com.mouse.mapper.ArticleMapper;
import com.mouse.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 星星
 * @create 2023-02-19 16:20
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String title,String summary){
        return  articleService.list(pageNum,pageSize,title,summary);
    }
    @GetMapping("{id}")
    public ResponseResult getArticleById(@PathVariable Integer id){
        return articleService.getArticleById(id);
    }
    @PutMapping
    public ResponseResult updateArticle(@RequestBody AdminArticleVo adminArticleVo){
        return articleService.updateArticle(adminArticleVo);
    }
    @DeleteMapping("{id}")
    public ResponseResult deleteArticleById(@PathVariable Integer id){
        return articleService.delete(id);
    }

}