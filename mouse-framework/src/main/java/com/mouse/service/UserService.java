package com.mouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-02-13 04:03:58
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}

