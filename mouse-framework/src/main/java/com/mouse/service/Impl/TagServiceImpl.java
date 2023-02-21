package com.mouse.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mouse.constants.SystemConstants;
import com.mouse.domain.ResponseResult;
import com.mouse.domain.dto.TagDto;
import com.mouse.domain.entity.Tag;
import com.mouse.domain.vo.CommentVo;
import com.mouse.domain.vo.PageVo;
import com.mouse.domain.vo.TagVo;
import com.mouse.enums.AppHttpCodeEnum;
import com.mouse.exception.SystemException;
import com.mouse.mapper.TagMapper;
import com.mouse.service.TagService;
import com.mouse.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-02-17 13:44:10
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {
    @Autowired
    private TagService tagService;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult<PageVo> pageTageList(Integer pageNum, Integer pageSize, TagDto tagDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        //如果没传入查询关键字,返回整个表？
        //如果传入了关键字,根据关键字返回tag
        queryWrapper.eq(StringUtils.hasText(tagDto.getName()),Tag::getName, tagDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagDto.getRemark()),Tag::getName, tagDto.getRemark());
//
//        queryWrapper
//                .like(Tag::getName,tagDto.getName())
//                .or()
//                .like(Tag::getRemark,tagDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装数据
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        //评论内容不能为空、敏感词设置等
        if (!StringUtils.hasText(tag.getName())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_CANNOT_NULL);
        }
        //查询数据库中是否已经存在此标签，若已存在，则添加失败
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.notIn(Tag::getName,tag.getName());
        queryWrapper.eq(Tag::getName,tag.getName());
        int count = tagService.count(queryWrapper);

        if (count>0){
            throw new SystemException(AppHttpCodeEnum.TAG_EXISTED);
        }

        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTagById(Integer id) {

        tagMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getTagById(Integer id) {

        Tag tag = tagService.getById(id);

//        tagService.updateById(tag);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTag(Tag tag) {
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return tagVos;
    }


}

