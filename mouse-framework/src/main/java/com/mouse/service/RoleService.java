package com.mouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.dto.RoleDto;
import com.mouse.domain.entity.Role;
import com.mouse.domain.vo.AddRole;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-02-18 11:09:40
 */

public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(RoleDto roleDto);

    ResponseResult getRoleById(Long id);

    ResponseResult addRole(AddRole addRole);
}

