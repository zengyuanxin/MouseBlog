package com.mouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mouse.domain.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-18 11:09:39
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);

}

