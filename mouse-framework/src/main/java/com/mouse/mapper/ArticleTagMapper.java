package com.mouse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mouse.domain.entity.ArticleTag;
import org.springframework.stereotype.Repository;


/**
 * 文章标签关联表(ArticleTag)表数据库访问层
 *
 * @author makejava
 * @since 2023-02-19 19:53:42
 */
@Repository
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}

