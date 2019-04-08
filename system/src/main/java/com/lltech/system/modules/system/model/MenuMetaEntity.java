package com.lltech.system.modules.system.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 前端自动生成菜单
 *  "meta": {
 *      "title": "系统监控",
 *     "icon": "monitor"
 *   }
 * @author CREATED BY L.C.Y on 2019-4-8
 */
@Data
@Accessors(chain = true)
public class MenuMetaEntity {
    /**
     *
     */
    private String title;
    private String icon;
}
