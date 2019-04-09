package com.lltech.system.modules.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.lltech.common.exception.BadRequestException;
import com.lltech.system.modules.system.dao.SysMenuDOMapper;
import com.lltech.system.modules.system.dao.SysRoleDOMapper;
import com.lltech.system.modules.system.model.SysMenuDO;
import com.lltech.system.modules.system.model.SysRoleDO;
import com.lltech.system.modules.system.service.SysRoleDOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author CREATED BY L.C.Y on 2019-4-3
 */
@Slf4j
@Service
public class SysRoleDOServiceImpl implements SysRoleDOService {
    private static final int SUPER_ADMIN = 1;
    private final SysRoleDOMapper sysRoleDOMapper;
    private final SysMenuDOMapper sysMenuDOMapper;

    @Autowired
    public SysRoleDOServiceImpl(SysRoleDOMapper sysRoleDOMapper, SysMenuDOMapper sysMenuDOMapper) {
        this.sysRoleDOMapper = sysRoleDOMapper;
        this.sysMenuDOMapper = sysMenuDOMapper;
    }

    /**
     *  删除角色，同时删除用户、菜单关联
     * @param roleId  roleId
     * @return count
     */
    @Override
    @Transactional
    public int deleteByPrimaryKey(Integer roleId) {
        if (roleId == SUPER_ADMIN) {
            throw new BadRequestException("不能删除超级管理员！");
        }
        // 删除关联用户
        sysRoleDOMapper.deleteRoleUserByRoleId(roleId);
        // 删除关联菜单
        sysRoleDOMapper.deleteRoleMenuByRoleId(roleId);
        // 删除角色
        return sysRoleDOMapper.deleteByPrimaryKey(roleId);
    }

    /**
     * 新增角色
     * @param record role
     * @return count
     */
    @Override
    public int insertSelective(SysRoleDO record) {
        // 判断角色名是否已存在
        SysRoleDO role = sysRoleDOMapper.getRoleByRoleName(record.getRoleName());
        if (role != null) {
            throw new BadRequestException("角色名已存在 {roleName=" + record.getRoleName() +"}");
        }
        // 新增时间
        record.setGmtCreate(new Date()).setGmtModified(new Date());

        // 新增到数据库
        return sysRoleDOMapper.insertSelective(record);
    }

    /**
     * 插入角色菜单关联表
     *
     * @param record roleId menuId
     * @return count
     */
    @Override
    @Transactional
    public int insertRoleMenu(SysRoleDO record) {
        log.info("绑定菜单：" + record);
        // 删除之前的关联菜单
        sysRoleDOMapper.deleteRoleMenuByRoleId(record.getRoleId());

        // 新增关联菜单
        List<SysMenuDO> menus = record.getMenus();
        if (menus == null || menus.size() == 0) {
            return 0;
        }

        for (SysMenuDO menu : menus) {
            record.setMenuId(menu.getMenuId());
            sysRoleDOMapper.insertRoleMenu(record);
        }

        return menus.size();
    }

    /**
     * 查询角色及其菜单
     * @param roleId roleId
     * @return 角色及菜单
     */
    @Override
    public SysRoleDO selectByPrimaryKey(Integer roleId) {
        // 查询角色详情
        SysRoleDO role = sysRoleDOMapper.selectByPrimaryKey(roleId);
        // 如果是超级管理员，返回所有菜单
        List<SysMenuDO> menus;
        if (roleId == SUPER_ADMIN) {
            menus = sysMenuDOMapper.listMenu(new SysMenuDO());
        }
        // 否则查询该角色的菜单
        else {
            menus = sysMenuDOMapper.listMenuByRoleId(roleId);
            if (menus == null || menus.size() == 0) {
                return role;
            }
        }
        role.setMenus(menus);
        return role;
    }

    /**
     * 列表,分页，可根据角色名查询
     *
     * @param record   role
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return 角色列表
     */
    @Override
    public List<SysRoleDO> listSysRole(SysRoleDO record, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return sysRoleDOMapper.listSysRole(record);
    }

    /**
     * 修改角色
     * @param record role
     * @return count
     */
    @Override
    public int updateByPrimaryKeySelective(SysRoleDO record) {
        if (record.getRoleId() == SUPER_ADMIN) {
            throw new BadRequestException("不能编辑超级管理员！");
        }
        // 判断角色名是否存在
        SysRoleDO role = sysRoleDOMapper.getRoleByRoleName(record.getRoleName());
        if (role != null && !record.getRoleId().equals(role.getRoleId())) {
            throw new BadRequestException("角色名已存在 {roleName=" + record.getRoleName() +"}");
        }
        // 变更时间
        record.setGmtModified(new Date());

        // 变更信息存入数据库
        return sysRoleDOMapper.updateByPrimaryKeySelective(record);
    }
}
