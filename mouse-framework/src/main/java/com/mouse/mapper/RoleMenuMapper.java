package com.mouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mouse.domain.entity.RoleMenu;
import org.springframework.stereotype.Repository;


/**
 * 角色和菜单关联表(RoleMenu)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-21 18:37:05
 */
@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}

