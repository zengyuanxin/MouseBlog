package com.mouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-02-04 23:14:34
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

