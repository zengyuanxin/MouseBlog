package com.mouse.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mouse.constants.SystemConstants;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.dto.MenuDto;
import com.mouse.domain.dto.MenuTreeDto;
import com.mouse.domain.entity.Article;
import com.mouse.domain.entity.Menu;
import com.mouse.domain.vo.MenuTreeVo;
import com.mouse.domain.vo.MenuVo;
import com.mouse.domain.vo.RoutersVo;
import com.mouse.enums.AppHttpCodeEnum;
import com.mouse.exception.SystemException;
import com.mouse.mapper.MenuMapper;
import com.mouse.service.MenuService;
import com.mouse.utils.BeanCopyUtils;
import com.mouse.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-02-18 11:04:27
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有的权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            //如果是 获取所有符合要求的Menu
            menus = menuMapper.selectAllRouterMenu();
        }else{
            //否则  获取当前用户所具有的Menu
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }

        //构建tree
        //先找出第一层的菜单  然后去找他们的子菜单设置到children属性中
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult list(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();

        //如果要查询名称
        if (StringUtils.hasText(menuName)){
            queryWrapper.like(Menu::getMenuName,menuName);
        }
        //如果要查询状态
        if (StringUtils.hasText(status)){
            queryWrapper.like(Menu::getStatus,status);
        }
        //排序
        queryWrapper.orderByAsc(Menu::getParentId);
        queryWrapper.orderByAsc(Menu::getOrderNum);

        List<Menu> list = list(queryWrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(list, MenuVo.class);

        return ResponseResult.okResult(menuVos);
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @Override
    public ResponseResult add(Menu menu) {
        menuMapper.insert(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getMenuDetailById(Long id) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getId,id);
        Menu menu = menuService.getOne(queryWrapper);
        MenuDto menuDto = BeanCopyUtils.copyBean(menu, MenuDto.class);

        return ResponseResult.okResult(menuDto);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {

        if (menu.getParentId().equals(menu.getId())){
            throw new SystemException(AppHttpCodeEnum.PARENT_ILLEGAL);
        }else {
            menuMapper.updateById(menu);
            return ResponseResult.okResult();
        }

    }

    @Override
    public ResponseResult deleteMenuById(Long menuId) {
        //找出这个菜单
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getId,menuId);
        Menu menu = menuMapper.selectOne(queryWrapper);

        LambdaQueryWrapper<Menu> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Menu::getParentId,menu.getId());
        int count = menuService.count(queryWrapper1);
        //如果要删除的菜单有子菜单——>全部parentId中有menu.getId

        if (count>0){
            throw new SystemException(AppHttpCodeEnum.EXIST_CHILDREN);

        }else {
            menuMapper.deleteById(queryWrapper);
            return ResponseResult.okResult();
        }

    }

    @Override
    public ResponseResult treeselect() {
//        //查询menu 结果是tree的形式
////        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(SecurityUtils.getUserId());
//        List<Menu> menus = selectRouterMenuTreeByUserId(SecurityUtils.getUserId());
//
//        List<MenuTreeDto> menuTreeDtos = BeanCopyUtils.copyBeanList(menus, MenuTreeDto.class);
//
//        //将MenuName赋值到Label
//        for (int i=0;i<menus.size();i++){
//            menuTreeDtos.get(i).setLabel(menus.get(i).getMenuName());
//        }
//        //封装数据返回
//        return ResponseResult.okResult(menuTreeDtos);
        List<Menu> menus = menuMapper.selectList(null);
        List<MenuTreeDto> treeVos = menus.stream().map(menu -> new MenuTreeDto(new ArrayList<>(), menu.getId(), menu.getMenuName(), menu.getParentId())).collect(Collectors.toList());
        List<MenuTreeDto> menuTree = buildTreeMenuTree(treeVos, 0L);
        return  ResponseResult.okResult(menuTree);
    }

    private List<MenuTreeDto> buildTreeMenuTree(List<MenuTreeDto> menus, Long parentId) {
        return menus.stream()
                .filter(menu -> parentId.equals(menu.getParentId()))
                //获取该菜单下的子菜单，并设置到children属性中
                .map(menu -> menu.setChildren(getChildrenMenusTree(menu,menus)))
                .collect(Collectors.toList());
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }
    private List<MenuTreeDto> getChildrenMenusTree(MenuTreeDto menu, List<MenuTreeDto> menus) {
        return menus.stream()
                .filter(m ->m.getParentId().equals(menu.getId()))
                //考虑到三级甚至多级菜单的情况下，
                // 需要继续找到子菜单的子菜单...并设置children属性,
                // 于是这里继续遍历每个菜单，寻找其子菜单并添加，调用自身的找子菜单方法，实现递归
                .map(m -> m.setChildren(getChildrenMenusTree(m,menus)))
                .collect(Collectors.toList());
    }


}

