package com.lltech.system.modules.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.lltech.common.exception.BadRequestException;
import com.lltech.common.utils.Md5Util;
import com.lltech.system.modules.system.dao.SysMenuDOMapper;
import com.lltech.system.modules.system.dao.SysRoleDOMapper;
import com.lltech.system.modules.system.dao.SysUserDOMapper;
import com.lltech.system.modules.system.model.SysMenuDO;
import com.lltech.system.modules.system.model.SysRoleDO;
import com.lltech.system.modules.system.model.SysUserDO;
import com.lltech.system.modules.system.service.SysUserDOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author CREATED BY L.C.Y on 2019-4-2
 */
@Slf4j
@Service
public class SysUserDOServiceImpl implements SysUserDOService {
    private final SysUserDOMapper sysUserDOMapper;
    private final SysRoleDOMapper sysRoleDOMapper;
    private final SysMenuDOMapper sysMenuDOMapper;

    @Autowired
    public SysUserDOServiceImpl(SysUserDOMapper sysUserDOMapper, SysRoleDOMapper sysRoleDOMapper, SysMenuDOMapper sysMenuDOMapper) {
        this.sysUserDOMapper = sysUserDOMapper;
        this.sysRoleDOMapper = sysRoleDOMapper;
        this.sysMenuDOMapper = sysMenuDOMapper;
    }

    /**
     * 删除用户
     *
     * @param userId pk
     * @return 删除条数
     */
    @Override
    @Transactional
    public int deleteByPrimaryKey(Long userId) {
        // 删除用户
        int count = sysUserDOMapper.deleteByPrimaryKey(userId);

        // 删除用户角色关联表
        sysUserDOMapper.deleteUserRoleByUserId(userId);

        return count;
    }

    /**
     * 动态插入
     *
     * @param record user
     * @return 插入条数
     */
    @Override
    @Transactional
    public int insertSelective(SysUserDO record) {
        // 判断用户名是否已存在
        SysUserDO user = sysUserDOMapper.getUserByUsername(record.getUsername());
        if (user != null) {
            throw new BadRequestException("用户名已存在{username=" + record.getUsername() + "}");
        }
        // 创建时间
        record.setGmtCreate(new Date()).setGmtModified(new Date());

        // 密码加密
        record.setPassword(Md5Util.generateMD5(record.getPassword()));

        // 插入用户
        int count = sysUserDOMapper.insertSelective(record);
        log.info("插入后的USER: " + record);
        // 插入用户角色关联表
        insertUserRole(record);

        return count;
    }

    /**
     * 查询用户及其角色
     *
     * @param userId pk
     * @return 用户及其角色
     */
    @Override
    public SysUserDO selectByPrimaryKey(Long userId) {
        // 查询用户
        SysUserDO user = sysUserDOMapper.selectByPrimaryKey(userId);
        // 查询关联的角色
        Set<SysRoleDO> roles = sysRoleDOMapper.listSysRoleByUserId(userId);
        if (roles == null || roles.size() == 0) {
            return user;
        }
        user.setRoles(roles);
        return user;
    }

    /**
     * 根据用户名查询密码,状态
     *
     * @param username 用户名
     * @return user
     */
    @Override
    public SysUserDO getUserByUsername(String username) {
        return sysUserDOMapper.getUserByUsername(username);
    }

    /**
     * 获取用户的所有角色Id
     *
     * @param userId userId
     * @return roleIds
     */
    @Override
    public Set<Integer> listRoleIdByUserId(Long userId) {
        return sysUserDOMapper.listRoleIdByUserId(userId);
    }

    /**
     * 查询用户所有权限
     *
     * @param userId userId
     * @return perms
     */
    @Override
    public Set<String> listStringPermsByUserId(Long userId) {
        return sysUserDOMapper.listStringPermsByUserId(userId);
    }

    /**
     * 查询用户所有的菜单
     *
     * @param userId userId
     * @return menus
     */
    @Override
    public Set<String> listStringMenuByUserId(Long userId) {
        return sysUserDOMapper.listStringMenuByUserId(userId);
    }

    /**
     * 查询用户的菜单和权限
     *
     * @param userId userId
     * @return 菜单列表
     */
    @Override
    public List<SysMenuDO> listMenuByUserId(Long userId) {
        return sysMenuDOMapper.listMenuByUserId(userId);
    }

    /**
     * 列表,可根据用户名和状态查询
     *
     * @param record username or status
     * @return 用户列表
     */
    @Override
    public List<SysUserDO> listSysUser(SysUserDO record, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return sysUserDOMapper.listSysUser(record);
    }

    /**
     * 根据用户信息，动态更新
     *
     * @param record user
     * @return 条数
     */
    @Override
    @Transactional
    public int updateByPrimaryKeySelective(SysUserDO record) {
        // 判断用户名是否已存在且与更新的ID不同
        SysUserDO user = sysUserDOMapper.getUserByUsername(record.getUsername());
        if (user != null && !record.getUserId().equals(user.getUserId())) {
            throw new BadRequestException("用户名已存在{username=" + record.getUsername() + "}");
        }

        // 更新时间
        record.setGmtModified(new Date());

        // 密码不更新
        record.setPassword(null);

        // 先删除用户角色关联表
        sysUserDOMapper.deleteUserRoleByUserId(record.getUserId());

        // 插入用户角色关联表
        insertUserRole(record);

        // 更新用户
        return sysUserDOMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 更新密码
     * @param record userId password
     * @return 条数
     */
    @Override
    public int updatePassword(SysUserDO record) {
        SysUserDO user = new SysUserDO();
        user.setUserId(record.getUserId())
                .setPassword(Md5Util.generateMD5(record.getPassword()));
        return sysUserDOMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 插入用户角色关联表
     * @param recode user
     */
    private void insertUserRole(SysUserDO recode) {
        // 获取所有的角色
        Set<SysRoleDO> roles = recode.getRoles();
        if (roles == null || roles.size() == 0) {
            throw new BadRequestException("请为用户分配角色！");
        }

        // 插入用户角色关联表
        for (SysRoleDO role : roles) {
            SysUserDO user = new SysUserDO();
            user.setUserId(recode.getUserId()).setRoleId(role.getRoleId());
            sysUserDOMapper.insertUserRole(user);
        }
    }
}
