package com.mouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-02-13 03:20:21
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);
}

