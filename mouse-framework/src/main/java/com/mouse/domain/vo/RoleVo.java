package com.mouse.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 星星
 * @create 2023-02-21 10:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {

    private List rows;
    private Long total;

}
