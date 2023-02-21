package com.mouse.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mouse.constants.SystemConstants;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.dto.RoleDto;
import com.mouse.domain.entity.Menu;
import com.mouse.domain.entity.Role;
import com.mouse.domain.entity.Tag;
import com.mouse.domain.entity.User;
import com.mouse.domain.vo.PageVo;
import com.mouse.domain.vo.RoleVo;
import com.mouse.mapper.RoleMapper;
import com.mouse.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-02-18 11:09:40
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是 返回 集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);

    }

    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        //查询角色表,如果传入了关键字，根据关键字进行查找
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        //如果要查询名称
        if (StringUtils.hasText(roleName)){
            queryWrapper.like(Role::getRoleName,roleName);
        }
        //如果要查询状态
        if (StringUtils.hasText(status)){
            queryWrapper.like(Role::getStatus,status);
        }
        //排序
        queryWrapper.orderByAsc(Role::getRoleSort);
        //分页
        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);

        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult changeStatus(RoleDto roleDto) {

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getId,roleDto.getRoleId());
        Role role = roleMapper.selectOne(queryWrapper);
        role.setStatus(roleDto.getStatus());

        roleService.updateById(role);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = roleMapper.selectById(id);
        return ResponseResult.okResult(role);
    }
}

