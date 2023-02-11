package com.mouse.controller;

import com.mouse.domain.ResponseResult;
import com.mouse.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 星星
 * @create 2023-02-12 5:05
 */
@RestController
@RequestMapping("link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return  linkService.getAllLink() ;
    }

}
