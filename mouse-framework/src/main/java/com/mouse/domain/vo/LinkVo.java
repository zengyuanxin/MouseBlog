package com.mouse.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 星星
 * @create 2023-02-12 5:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {
    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;

}
