package com.lltech.system.modules.system.service.impl;

import com.lltech.common.exception.BadRequestException;
import com.lltech.system.modules.system.dao.SysMenuDOMapper;
import com.lltech.system.modules.system.model.SysMenuDO;
import com.lltech.system.modules.system.service.SysMenuDOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
}
