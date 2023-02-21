package com.mouse.service;

import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.User;
import com.mouse.domain.vo.AdminUserInfoVo;
import com.mouse.domain.vo.RoutersVo;

/**
 * @author 星星
 * @create 2023-02-18 9:33
 */

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();


    ResponseResult<AdminUserInfoVo> getInfo();

    ResponseResult<RoutersVo> getRouters();
}
