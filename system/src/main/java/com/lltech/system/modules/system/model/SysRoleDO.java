package com.lltech.system.modules.system.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class SysRoleDO {
    /**
     * pk
     */
    private Integer roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     *创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 备注
     */
    private String remark;

    /**
     * 关联的权限，多对多
     */
    private List<SysMenuDO> menus;

    /**
     * 关联的权限ID
     */
    private Integer menuId;

}
