package com.mouse.controller;

import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.Tag;
import com.mouse.domain.vo.PageVo;
import com.mouse.domain.dto.TagDto;
import com.mouse.domain.vo.TagVo;
import com.mouse.service.TagService;
import com.mouse.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagDto tagDto){

        return tagService.pageTageList(pageNum,pageSize, tagDto);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagDto TagDto){
        Tag tag = BeanCopyUtils.copyBean(TagDto, Tag.class);
        return tagService.addTag(tag);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteTagById(@PathVariable("id") Integer id){
        return tagService.deleteTagById(id);
    }

    /**
     * 获取标签信息
     * @param id
     * @return
     */
    @GetMapping ("/{id}")
    public ResponseResult getTagById(@PathVariable("id") Integer id){

        return tagService.getTagById(id);
    }
    /**
     * 修改标签
     */
    @PutMapping  ()
    public ResponseResult updateTag(@RequestBody Tag tag){

        return tagService.updateTag(tag);
    }
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }

}