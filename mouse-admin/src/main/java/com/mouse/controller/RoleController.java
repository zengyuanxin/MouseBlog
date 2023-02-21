package com.mouse.controller;

import com.mouse.domain.ResponseResult;
import com.mouse.domain.dto.RoleDto;
import com.mouse.domain.vo.RoleVo;
import com.mouse.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.web.bind.annotation.*;

/**
 * @author 星星
 * @create 2023-02-21 10:47
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String roleName,String status){
        return roleService.roleList(pageNum,pageSize,roleName,status);
    }

    @GetMapping("{id}")
    public ResponseResult getRoleById(@PathVariable Long id){
        return roleService.getRoleById(id);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleDto roleDto){
        return roleService.changeStatus(roleDto);
    }

}
