package com.mouse.service.Impl;

import com.mouse.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 星星
 * @create 2023-02-20 14:51
 */
@Service("ps")
public class PermissionService {
    /**
     * 判断当前用户是否具有permission
     * @param permission 要判断的权限
     * @return
     */
    public boolean hasPermisson(String permission){
        //如果是超级管理员 直接返回true
        if (SecurityUtils.isAdmin()){
            return true;
        }
        //否则获取当前登录用户所具有的权限列表 然后进行判断
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);

    }
}
