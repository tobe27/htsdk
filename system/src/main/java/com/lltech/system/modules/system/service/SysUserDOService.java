package com.lltech.system.modules.system.service;

import com.lltech.system.modules.system.model.SysMenuDO;
import com.lltech.system.modules.system.model.SysUserDO;

import java.util.List;
import java.util.Set;

/**
 * @author CREATED BY L.C.Y on 2019-4-2
 */
public interface SysUserDOService {
    /**
     * 删除用户
     * @param userId pk
     * @return 删除条数
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * 动态插入
     * @param record user
     * @return 插入条数
     */
    int insertSelective(SysUserDO record);

    /**
     * 根据主键查询
     * @param userId pk
     * @return user
     */
    SysUserDO selectByPrimaryKey(Long userId);

    /**
     * 根据用户名查询密码,状态
     * @param username 用户名
     * @return user
     */
    SysUserDO getUserByUsername(String username);

    /**
     * 获取用户的所有角色Id
     * @param userId userId
     * @return roleIds
     */
    Set<Integer> listRoleIdByUserId(Long userId);

    /**
     * 查询用户所有权限
     * @param userId userId
     * @return perms
     */
    Set<String> listStringPermsByUserId(Long userId);

    /**
     * 查询用户所有的菜单
     * @param userId userId
     * @return menus
     */
    Set<String> listStringMenuByUserId(Long userId);

    /**
     * 查询用户的菜单和权限
     * @param userId userId
     * @return 菜单列表
     */
    Set<SysMenuDO> listMenuByUserId(Long userId);

    /**
     * 列表,可根据用户名和状态查询
     * @param record username or status
     * @param pageNum 页码
     * @param pageSize 页大小
     * @return 用户列表
     */
    List<SysUserDO> listSysUser(SysUserDO record, Integer pageNum, Integer pageSize);

    /**
     * 根据用户信息，动态更新，密码不更新
     * @param record user
     * @return 条数
     */
    int updateByPrimaryKeySelective(SysUserDO record);

    /**
     * 更新密码
     * @param record userId password
     * @return 条数
     */
    int updatePassword(SysUserDO record);
}
