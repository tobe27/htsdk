package com.lltech.system.modules.system.controller;

import com.lltech.common.utils.ResultBean;
import com.lltech.system.logging.annotation.Log;
import com.lltech.system.modules.system.model.SysMenuDO;
import com.lltech.system.modules.system.service.SysMenuDOService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CREATED BY L.C.Y on 2019-4-3
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController {
    private final SysMenuDOService sysMenuDOService;

    @Autowired
    public SysMenuController(SysMenuDOService sysMenuDOService) {
        this.sysMenuDOService = sysMenuDOService;
    }

    @Log("删除菜单")
    @DeleteMapping("/{menuId}")
    @RequiresPermissions("sys_menu_delete")
    public ResultBean delete(@PathVariable Integer menuId) {
        sysMenuDOService.deleteByPrimaryKey(menuId);
        return new ResultBean().ok();
    }

    @Log("新增菜单")
    @PostMapping("")
    @RequiresPermissions("sys_menu_create")
    public ResultBean create(@RequestBody SysMenuDO menu) {
        sysMenuDOService.insertSelective(menu);
        return new ResultBean().ok();
    }

    @Log("编辑菜单")
    @PutMapping("/{menuId}")
    @RequiresPermissions("sys_menu_modify")
    public ResultBean update(@PathVariable Integer menuId, @RequestBody SysMenuDO menu) {
        menu.setMenuId(menuId);
        sysMenuDOService.updateByPrimaryKeySelective(menu);
        return new ResultBean().ok();
    }

    @Log("菜单列表")
    @GetMapping("/list")
    @RequiresPermissions("sys_menu_list")
    public ResultBean list(SysMenuDO menu) {
        return new ResultBean().ok(sysMenuDOService.listMenu(menu));
    }

}
