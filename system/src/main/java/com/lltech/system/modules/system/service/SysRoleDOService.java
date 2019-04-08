package com.lltech.system.modules.system.service;

import com.lltech.system.modules.system.model.SysRoleDO;

import java.util.List;

/**
 * @author CREATED BY L.C.Y on 2019-4-3
 */
public interface SysRoleDOService {
    int deleteByPrimaryKey(Integer roleId);

    int insertSelective(SysRoleDO record);

    /**
     * 插入角色菜单关联表
     * @param record roleId menuId
     * @return count
     */
    int insertRoleMenu(SysRoleDO record);

    SysRoleDO selectByPrimaryKey(Integer roleId);

    /**
     * 列表,分页，可根据角色名查询
     * @param record role
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 角色列表
     */
    List<SysRoleDO> listSysRole(SysRoleDO record, Integer pageNum, Integer pageSize);

    int updateByPrimaryKeySelective(SysRoleDO record);
}
