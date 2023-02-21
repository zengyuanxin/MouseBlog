package com.mouse.controller;

import com.mouse.domain.ResponseResult;
import com.mouse.domain.entity.Menu;
import com.mouse.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 星星
 * @create 2023-02-20 19:47
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("/list")
    public ResponseResult list(String status,String menuName){
        return menuService.list(status,menuName);
    }
    @PostMapping
    private ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.add(menu);
    }
    @GetMapping("{id}")
    public ResponseResult getMenuDetailByid(@PathVariable Long id){
        return menuService.getMenuDetailById(id);
    }
    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }
    @DeleteMapping("{menuId}")
    public ResponseResult deleteMenuById(@PathVariable Long menuId){
        return menuService.deleteMenuById(menuId);
    }
//    @GetMapping("/treeselect")
//    public ResponseResult treeselect(){
//        return menuService.treeselect();
//    }
}
