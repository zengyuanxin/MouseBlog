package com.mouse.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 星星
 * @create 2023-02-12 4:27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    //标题
    private String title;
    //文章摘要
    private String summary;
    //所属分类名
    private String categoryName;
    //所属分类id
    private Long categoryId;
    //缩略图
    private String thumbnail;

    //文章内容
    private String content;
    //访问量
    private Long viewCount;

    private Date createTime;
}
