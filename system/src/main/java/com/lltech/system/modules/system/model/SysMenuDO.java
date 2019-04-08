package com.lltech.system.modules.system.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class SysMenuDO {
    private Integer menuId;

    private String menuName;

    private String parentMenu;

    private String perms;

    private Date gmtCreate;

    private Date gmtModified;

}