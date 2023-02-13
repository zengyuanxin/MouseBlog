package com.mouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author 星星
 * @create 2023-02-12 6:14
 */
@Repository
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
