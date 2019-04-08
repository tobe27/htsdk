package com.lltech.system.modules.system.dao;

import com.lltech.system.modules.system.model.SysRoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface SysRoleDOMapper {
    int deleteByPrimaryKey(Integer roleId);

    /**
     * 删除关联的菜单
     * @param roleId 角色ID
     * @return 条数
     */
    int deleteRoleMenuByRoleId(Integer roleId);

    /**
     * 删除关联的用户
     * @param roleId roleId
     * @return count
     */
    int deleteRoleUserByRoleId(Integer roleId);

    int insertSelective(SysRoleDO record);

    /**
     * 插入角色菜单关联表
     * @param record roleId menuId
     * @return count
     */
    int insertRoleMenu(SysRoleDO record);

    SysRoleDO selectByPrimaryKey(Integer roleId);

    SysRoleDO getRoleByRoleName(String roleName);

    /**
     * 列表，可根据角色名查询
     * @param record role
     * @return 角色列表
     */
    List<SysRoleDO> listSysRole(SysRoleDO record);

    /**
     * 查询用户关联的角色
     * @param userId userId
     * @return 用户的关联角色
     */
    Set<SysRoleDO> listSysRoleByUserId(Long userId);

    int updateByPrimaryKeySelective(SysRoleDO record);
}