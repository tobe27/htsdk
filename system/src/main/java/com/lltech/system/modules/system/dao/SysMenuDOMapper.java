package com.lltech.system.modules.system.dao;

import com.lltech.system.modules.system.model.SysMenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface SysMenuDOMapper {
    int deleteByPrimaryKey(Integer menuId);

    /**
     * 删除该菜单关联的角色
     * @param menuId menuId
     * @return count
     */
    int deleteMenuRoleByMenuId(Integer menuId);

    int insertSelective(SysMenuDO record);

    SysMenuDO selectByPrimaryKey(Integer menuId);

    SysMenuDO getMenuByMenuName(String menuName);

    /**
     * 菜单权限列表，不分页，可根据名称查询
     * @return 列表
     */
    Set<SysMenuDO> listMenu(SysMenuDO record);

    /**
     * 查询角色的关联菜单
     * @param roleId roleId
     * @return 角色的关联菜单
     */
    Set<SysMenuDO> listMenuByRoleId(Integer roleId);

    /**
     * 查询用户的菜单和权限
     * @param userId userId
     * @return 菜单列表
     */
    Set<SysMenuDO> listMenuByUserId(Long userId);

    int updateByPrimaryKeySelective(SysMenuDO record);

}
