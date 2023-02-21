package com.mouse.service.Impl;

import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.LoginUser;
import com.mouse.domain.entity.Menu;
import com.mouse.domain.entity.User;
import com.mouse.domain.vo.AdminUserInfoVo;
import com.mouse.domain.vo.RoutersVo;
import com.mouse.domain.vo.UserInfoVo;
import com.mouse.service.LoginService;
import com.mouse.service.RoleService;
import com.mouse.utils.BeanCopyUtils;
import com.mouse.utils.JwtUtil;
import com.mouse.utils.RedisCache;
import com.mouse.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.mouse.service.MenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 星星
 * @create 2023-02-18 9:37
 */
@Service
public class SystemLoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    /**
     * 登陆后台
     * @param user
     * @return
     */
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        //前面是判断是否通过userName能否从mysql中查到，这里通过认证是否通过,前面只是用用户名没查到，查到了再比对密码，密码可能对不上
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);

        //把token封装 返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    /**
     * 退出后台
     * @return
     */
    @Override
    public ResponseResult logout() {
        //获取当前用户userid
        Long userId = SecurityUtils.getUserId();
        //删除redis中的用户信息
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }

    /**
     * 实现不同的用户权限可以看到不同的功能
     * @return
     */
    @Override
    public ResponseResult<AdminUserInfoVo> getInfo() {
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    /**
     * 返回用户所能访问的菜单数据
     * @return
     */
    @Override
    public ResponseResult<RoutersVo> getRouters() {
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }


}
