package com.lltech.system.modules.system.controller;

import com.github.pagehelper.PageInfo;
import com.lltech.common.utils.ResultBean;
import com.lltech.system.logging.annotation.Log;
import com.lltech.system.modules.system.model.SysRoleDO;
import com.lltech.system.modules.system.service.SysRoleDOService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CREATED BY L.C.Y on 2019-4-3
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController {
    private final SysRoleDOService sysRoleDOService;

    @Autowired
    public SysRoleController(SysRoleDOService sysRoleDOService) {
        this.sysRoleDOService = sysRoleDOService;
    }

    @Log("删除角色")
    @DeleteMapping("/{roleId}")
    @RequiresPermissions("sys_role_delete")
    public ResultBean delete(@PathVariable Integer roleId) {
        sysRoleDOService.deleteByPrimaryKey(roleId);
        return new ResultBean().ok();
    }

    @Log("新增角色")
    @PostMapping("")
    @RequiresPermissions("sys_role_create")
    public ResultBean create(@RequestBody SysRoleDO role) {
        sysRoleDOService.insertSelective(role);
        return new ResultBean().ok();
    }

    @Log("编辑角色")
    @PutMapping("/{roleId}")
    @RequiresPermissions("sys_role_modify")
    public ResultBean update(@PathVariable Integer roleId, @RequestBody SysRoleDO role) {
        role.setRoleId(roleId);
        sysRoleDOService.updateByPrimaryKeySelective(role);
        return new ResultBean().ok();
    }

    @Log("角色绑定菜单")
    @PutMapping("/{roleId}/menu")
    @RequiresPermissions("sys_role_menu")
    public ResultBean updateRoleMenu(@PathVariable Integer roleId, @RequestBody SysRoleDO role) {
        role.setRoleId(roleId);
        sysRoleDOService.insertRoleMenu(role);
        return new ResultBean().ok();
    }

    @Log("角色详情")
    @GetMapping("/{roleId}")
    @RequiresPermissions("sys_role_detail")
    public ResultBean get(@PathVariable Integer roleId) {
        return new ResultBean().ok(sysRoleDOService.selectByPrimaryKey(roleId));
    }

    @Log("角色列表")
    @GetMapping("/list")
    @RequiresPermissions("sys_role_list")
    public ResultBean list(String roleName, Integer pageNum, Integer pageSize) {
        SysRoleDO role = new SysRoleDO();
        role.setRoleName(roleName);
        PageInfo<SysRoleDO> pageInfo = new PageInfo<>(sysRoleDOService.listSysRole(role, pageNum, pageSize));
        return new ResultBean().ok(pageInfo.getTotal(), pageInfo.getList());
    }

}
