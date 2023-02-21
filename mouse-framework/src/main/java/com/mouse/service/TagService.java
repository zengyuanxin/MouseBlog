package com.mouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.dto.TagDto;
import com.mouse.domain.entity.Tag;
import com.mouse.domain.vo.PageVo;
import com.mouse.domain.vo.TagVo;

import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-02-17 13:44:09
 */
public interface TagService extends IService<Tag> {


    ResponseResult<PageVo> pageTageList(Integer pageNum, Integer pageSize, TagDto tagDto);

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTagById(Integer id);

    ResponseResult getTagById(Integer id);

    ResponseResult updateTag(Tag tag);

    List<TagVo> listAllTag();
}

