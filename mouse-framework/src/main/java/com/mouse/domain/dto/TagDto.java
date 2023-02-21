package com.mouse.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 星星
 * @create 2023-02-19 11:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    private String name;
    private String remark;

}
