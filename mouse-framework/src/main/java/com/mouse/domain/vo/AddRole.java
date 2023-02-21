package com.mouse.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 星星
 * @create 2023-02-21 18:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRole {
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    //角色具有的菜单
    private List<Long> menuIds;
    //备注
    private String remark;
}
