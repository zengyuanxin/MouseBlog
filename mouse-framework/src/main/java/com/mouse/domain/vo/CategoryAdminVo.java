package com.mouse.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 星星
 * @create 2023-02-19 19:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAdminVo {
    private String description;
    private Long id;
    private String name;
}
