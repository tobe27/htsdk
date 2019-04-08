package com.lltech.system.modules.system.dao;

import com.lltech.system.modules.system.model.SysUserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface SysUserDOMapper {
    /**
     * 删除用户
     * @param userId pk
     * @return 删除条数
     */
    int deleteByPrimaryKey(Long userId);

    /**
     * 删除用户角色关联表
     * @param userId pk
     * @return 删除条数
     */
    int deleteUserRoleByUserId(Long userId);

    /**
     * 动态插入
     * @param record user
     * @return 插入条数
     */
    int insertSelective(SysUserDO record);

    /**
     * 插入用户角色关联表
     * @param record userId, roleId
     * @return 数量
     */
    int insertUserRole(SysUserDO record);

    /**
     * 根据主键查询
     * @param userId pk
     * @return user
     */
    SysUserDO selectByPrimaryKey(Long userId);

    /**
     * 根据用户名查询密码,状态
     * @param username username
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
     * 列表,可根据用户名和状态查询
     * @param record username or status
     * @return 用户列表
     */
    List<SysUserDO> listSysUser(SysUserDO record);

    /**
     * 根据用户信息，动态更新
     * @param record user
     * @return 条数
     */
    int updateByPrimaryKeySelective(SysUserDO record);

}
