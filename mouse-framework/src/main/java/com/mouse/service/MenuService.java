package com.mouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-02-18 11:04:27
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult list(String status, String menuName);

    ResponseResult add(Menu menu);

    ResponseResult getMenuDetailById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteMenuById(Long menuId);

//    ResponseResult treeselect();
}

