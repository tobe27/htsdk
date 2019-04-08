package com.lltech.system.modules.system.controller;

import com.github.pagehelper.PageInfo;
import com.lltech.common.utils.ResultBean;
import com.lltech.system.logging.annotation.Log;
import com.lltech.system.modules.system.model.SysUserDO;
import com.lltech.system.modules.system.service.SysUserDOService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CREATED BY L.C.Y on 2019-4-2
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController {

    private final SysUserDOService sysUserDOService;

    @Autowired
    public SysUserController(SysUserDOService sysUserDOService) {
        this.sysUserDOService = sysUserDOService;
    }

    @Log("删除用户")
    @DeleteMapping("/{userId}")
    @RequiresPermissions("sys_user_delete")
    public ResultBean deleteUser(@PathVariable Long userId) {
        sysUserDOService.deleteByPrimaryKey(userId);
        return new ResultBean().ok();
    }

    @Log("新增用户")
    @PostMapping("")
    @RequiresPermissions("sys_user_create")
    public ResultBean createUser(@RequestBody SysUserDO user) {
        sysUserDOService.insertSelective(user);
        return new ResultBean().ok();
    }

    @Log("编辑用户")
    @PutMapping("/{userId}")
    @RequiresPermissions("sys_user_modify")
    public ResultBean modifyUser(@PathVariable Long userId, @RequestBody SysUserDO sysUserDO) {
        sysUserDOService.updateByPrimaryKeySelective(sysUserDO.setUserId(userId));
        return new ResultBean().ok();
    }

    @Log("用户详情")
    @GetMapping("/{userId}")
    @RequiresPermissions("sys_user_detail")
    public ResultBean getUser(@PathVariable Long userId) {
        return new ResultBean().ok(sysUserDOService.selectByPrimaryKey(userId));
    }

    @Log("用户列表")
    @GetMapping("/list")
    @RequiresPermissions("sys_user_list")
    public ResultBean listUser(SysUserDO user, Integer pageNum, Integer pageSize) {
        PageInfo<SysUserDO> pageInfo = new PageInfo<>(sysUserDOService.listSysUser(user, pageNum, pageSize));
        return new ResultBean().ok(pageInfo.getTotal(), pageInfo.getList());
    }

    @Log("修改密码")
    @PutMapping("/{userId}/password")
    @RequiresPermissions("sys_user_password_modify")
    public ResultBean modifyUserPassword(@PathVariable Long userId, @RequestBody SysUserDO sysUserDO) {
        sysUserDOService.updatePassword(sysUserDO.setUserId(userId));
        return new ResultBean().ok();
    }

}
