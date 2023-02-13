package com.mouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mouse.domain.entity.User;
import org.springframework.stereotype.Repository;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-12 05:53:28
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}

