package com.mouse.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 星星
 * @create 2023-02-21 11:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    //角色ID@TableId
    private Long roleId;
    //角色状态（0正常 1停用）
    private String status;

}
