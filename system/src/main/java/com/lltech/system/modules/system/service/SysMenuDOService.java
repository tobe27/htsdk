package com.lltech.system.modules.system.service;

import com.lltech.system.modules.system.model.SysMenuDO;

import java.util.List;
import java.util.Set;

/**
 * @author CREATED BY L.C.Y on 2019-4-3
 */
public interface SysMenuDOService {

    int deleteByPrimaryKey(Integer menuId);

    int insertSelective(SysMenuDO record);

    /**
     * 菜单权限列表，不分页，可根据名称查询
     * @return 列表
     */
    Set<SysMenuDO> listMenu(SysMenuDO record);

    int updateByPrimaryKeySelective(SysMenuDO record);
}
