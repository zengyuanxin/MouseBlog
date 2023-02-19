package com.mouse.controller;

import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.User;
import com.mouse.domain.vo.AdminUserInfoVo;
import com.mouse.domain.vo.RoutersVo;
import com.mouse.enums.AppHttpCodeEnum;
import com.mouse.exception.SystemException;
import com.mouse.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 星星
 * @create 2023-02-18 9:32
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService LoginService;

    /**
     * 登陆后台
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return LoginService.login(user);
    }

    /**
     * 退出后台
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return LoginService.logout();
    }

    /**
     * 实现不同的用户权限可以看到不同的功能
     * @return
     */
    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        return LoginService.getInfo();

    }

    /**
     * 返回用户所能访问的菜单数据
     * @return
     */
    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        return LoginService.getRouters();

    }

}
