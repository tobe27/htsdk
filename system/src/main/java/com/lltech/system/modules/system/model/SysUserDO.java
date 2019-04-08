package com.lltech.system.modules.system.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Set;

@Data
@Accessors(chain = true)
public class SysUserDO {
    /**
     * 主键pk
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码，MD5加密
     */
    private String password;

    /**
     * 用户状态，1-正常，0-失效
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 关联的角色, 多对多关系
     */
    private Set<SysRoleDO> roles;

    /**
     * 角色Id，用于关联表
     */
    private Integer roleId;

}