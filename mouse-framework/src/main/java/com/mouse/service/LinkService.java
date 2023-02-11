package com.mouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-02-12 05:09:15
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

