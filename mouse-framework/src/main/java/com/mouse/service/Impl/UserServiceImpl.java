package com.mouse.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mouse.domain.entity.User;
import com.mouse.mapper.UserMapper;
import com.mouse.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-02-13 04:03:58
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

