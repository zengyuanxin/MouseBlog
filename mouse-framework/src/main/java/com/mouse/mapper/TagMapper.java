package com.mouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mouse.domain.entity.Tag;
import org.springframework.stereotype.Repository;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-17 13:44:08
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {

}

