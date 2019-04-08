package com.lltech.system.modules.system.service.impl;

import com.lltech.system.modules.system.model.SysMenuDO;
import com.lltech.system.modules.system.model.SysRoleDO;
import com.lltech.system.modules.system.service.SysRoleDOService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author CREATED BY L.C.Y on 2019-4-3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysRoleDOServiceImplTest {
    @Autowired
    SysRoleDOService sysRoleDOService;

    @Test
    public void deleteByPrimaryKey() {
        System.out.println(sysRoleDOService.deleteByPrimaryKey(2));
    }

    @Test
    public void insertSelective() {
        SysRoleDO role = new SysRoleDO();
        role.setRoleName("财务")
                .setRemark("角色管理");
        System.out.println(sysRoleDOService.insertSelective(role));
    }

    @Test
    public void insertRoleMenu() {
        SysRoleDO role = new SysRoleDO();
        role.setRoleId(2)
                .setRoleName("普通员工")
                .setRemark("角色管理");
        Set<SysMenuDO> menus = new HashSet<>();
        for (int i = 1; i < 6; i++) {
            SysMenuDO menu = new SysMenuDO();
            menu.setMenuId(i);
            menus.add(menu);
        }
        role.setMenus(menus);
        System.out.println(sysRoleDOService.insertRoleMenu(role));
    }

    @Test
    public void selectByPrimaryKey() {
        System.out.println(sysRoleDOService.selectByPrimaryKey(2));
    }

    @Test
    public void listSysRole() {
        System.out.println(sysRoleDOService.listSysRole(new SysRoleDO().setRoleName("普通"), 1, 10));
    }

    @Test
    public void updateByPrimaryKeySelective() {
        SysRoleDO role = new SysRoleDO();
        role.setRoleId(1)
                .setRoleName("普通员工")
                .setRemark("角色管理");
        System.out.println(sysRoleDOService.updateByPrimaryKeySelective(role));

    }
}