package com.mouse.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 星星
 * @create 2023-02-19 19:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminArticleVo {
    //所属分类id
    private Long categoryId;
    //文章内容
    private String content;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

    private Long id;
    //是否允许评论 1是，0否
    private String isComment;
    //是否置顶（0否，1是）
    private String isTop;
    //状态（0已发布，1草稿）
    private String status;
    //文章摘要
    private String summary;
    //标签，要连表
    private List<Long> tags;
    //缩略图
    private String thumbnail;
    //标题
    private String title;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //访问量
    private Long viewCount;










}
