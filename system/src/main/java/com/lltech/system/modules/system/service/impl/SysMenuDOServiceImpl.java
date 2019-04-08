package com.lltech.system.modules.system.service.impl;

import com.lltech.common.exception.BadRequestException;
import com.lltech.system.modules.system.dao.SysMenuDOMapper;
import com.lltech.system.modules.system.model.MenuMetaEntity;
import com.lltech.system.modules.system.model.MenuTreeEntity;
import com.lltech.system.modules.system.model.SysMenuDO;
import com.lltech.system.modules.system.service.SysMenuDOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author CREATED BY L.C.Y on 2019-4-3
 */
@Service
public class SysMenuDOServiceImpl implements SysMenuDOService {
    private final SysMenuDOMapper sysMenuDOMapper;

    @Autowired
    public SysMenuDOServiceImpl(SysMenuDOMapper sysMenuDOMapper) {
        this.sysMenuDOMapper = sysMenuDOMapper;
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(Integer menuId) {
        // 删除关联的角色
        sysMenuDOMapper.deleteMenuRoleByMenuId(menuId);
        // 删除菜单
        return sysMenuDOMapper.deleteByPrimaryKey(menuId);
    }

    @Override
    @Transactional
    public int insertSelective(SysMenuDO record) {
        SysMenuDO menu = sysMenuDOMapper.getMenuByMenuName(record.getMenuName());
        if (menu != null) {
            throw new BadRequestException("菜单名已存在 {menuName=" + record.getMenuName() + "}");
        }
        // 新增时间
        record.setGmtCreate(new Date()).setGmtModified(new Date());
        return sysMenuDOMapper.insertSelective(record);
    }

    /**
     * 菜单权限列表，不分页，可根据名称查询
     *
     * @param record
     * @return 列表
     */
    @Override
    public Set<SysMenuDO> listMenu(SysMenuDO record) {
        return sysMenuDOMapper.listMenu(record);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(SysMenuDO record) {
        SysMenuDO menu = sysMenuDOMapper.getMenuByMenuName(record.getMenuName());
        if (menu != null && !record.getMenuId().equals(menu.getMenuId())) {
            throw new BadRequestException("菜单名已存在 {menuName=" + record.getMenuName() + "}");
        }
        record.setGmtModified(new Date());
        return sysMenuDOMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * // 遍历出菜单，并按照结构整理
     *         // {
     *         //	"name": "系统监控",
     *         //	"path": "/monitor",
     *         //	"redirect": "noredirect",
     *         //	"component": "Layout",
     *         //	"alwaysShow": true,
     *         //	"meta": {
     *         //		"title": "系统监控",
     *         //		"icon": "monitor"
     *         //	},
     *         //	"children": [{
     *         //		"name": "操作日志",
     *         //		"path": "logs",
     *         //		"component": "monitor/log/index",
     *         //		"meta": {
     *         //			"title": "操作日志",
     *         //			"icon": "log"
     *         //		}
     *         //	}]
     *         // }
     *         //
     * @param menus
     * @return
     */
    public List<MenuTreeEntity> convertMenuToTree(Set<SysMenuDO> menus) {
        // 树形结构列表
        List<MenuTreeEntity> returnTree = new ArrayList<>();
        String[] systemManageArr = {"用户管理", "角色管理", "菜单管理", "操作日志"};
        String[] gameManageArr = {"游戏管理"};
        String[] playerManageArr = {"玩家管理"};

        // 遍历相应的菜单名
        Set<String> menuComponentList = new HashSet<>();
        for (SysMenuDO menu : menus) {
            menuComponentList.add(menu.getParentMenu());
        }

        // 系统管理
        MenuTreeEntity systemMenu = new MenuTreeEntity();
        systemMenu.setName("系统管理")
                .setPath("/system")
                .setRedirect("noredirect")
                .setComponent("Layout")
                .setAlwaysShow("true")
                .setMeta(new MenuMetaEntity().setTitle("系统管理").setIcon("system"));

        // 系统管理子菜单
        List<MenuTreeEntity> systemChildren = new ArrayList<>();

        for (String menuComponent : menuComponentList) {

            // 系统管理 ===================================================
            if ("用户管理".equals(menuComponent)) {
                MenuTreeEntity user = new MenuTreeEntity();
                user.setName("用户管理")
                        .setPath("/user")
                        .setComponent("system/user/index")
                        .setMeta(new MenuMetaEntity().setTitle("用户管理").setIcon("peoples"));
                systemChildren.add(user);
            }

            if ("角色管理".equals(menuComponent)) {
                MenuTreeEntity role = new MenuTreeEntity();
                role.setName("角色管理")
                        .setPath("/role")
                        .setComponent("system/role/index")
                        .setMeta(new MenuMetaEntity().setTitle("角色管理").setIcon("role"));
                systemChildren.add(role);
            }

            if ("菜单管理".equals(menuComponent)) {
                MenuTreeEntity menu = new MenuTreeEntity();
                menu.setName("菜单管理")
                        .setPath("menu")
                        .setComponent("system/menu/index")
                        .setMeta(new MenuMetaEntity().setTitle("菜单管理").setIcon("menu"));
                systemChildren.add(menu);
            }

            if ("操作日志".equals(menuComponent)) {
                MenuTreeEntity logs = new MenuTreeEntity();
                logs.setName("操作日志")
                        .setPath("logs")
                        .setComponent("system/log/index")
                        .setMeta(new MenuMetaEntity().setTitle("操作日志").setIcon("log"));
                systemChildren.add(logs);
            }
            // 系统管理 ====================Over============================

        }

        // 系统管理添加子菜单
        systemMenu.setChildren(systemChildren);

        // 总的菜单树
        returnTree.add(systemMenu);

        return returnTree;
    }
}
