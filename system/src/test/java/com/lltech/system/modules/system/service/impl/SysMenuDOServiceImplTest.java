package com.lltech.system.modules.system.service.impl;

import com.lltech.system.modules.system.model.SysMenuDO;
import com.lltech.system.modules.system.service.SysMenuDOService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author CREATED BY L.C.Y on 2019-4-3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysMenuDOServiceImplTest {

    @Autowired
    SysMenuDOService sysMenuDOService;

    @Test
    public void deleteByPrimaryKey() {
        System.out.println(sysMenuDOService.deleteByPrimaryKey(2));
    }

    @Test
    public void insertSelective() {
        SysMenuDO menu = new SysMenuDO();
        menu.setMenuName("编辑用户")
                .setParentMenu("用户管理")
                .setPerms("sys_user_modify");
        System.out.println(sysMenuDOService.insertSelective(menu));
    }

    @Test
    public void listMenu() {
        System.out.println(sysMenuDOService.listMenu(new SysMenuDO().setMenuName("用户")));
    }

    @Test
    public void updateByPrimaryKeySelective() {
        SysMenuDO menu = new SysMenuDO();
        menu.setMenuId(1)
                .setMenuName("修改密码")
                .setParentMenu("用户管理")
                .setPerms("sys_user_password_modify");
        System.out.println(sysMenuDOService.updateByPrimaryKeySelective(menu));
    }
}