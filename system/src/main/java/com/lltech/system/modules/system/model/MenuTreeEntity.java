package com.lltech.system.modules.system.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 菜单树形结构，以便于前端自动生成菜单
 * {
 * 	"name": "系统监控",
 * 	"path": "/monitor",
 * 	"redirect": "noredirect",
 * 	"component": "Layout",
 * 	"alwaysShow": true,
 * 	"meta": {
 * 		"title": "系统监控",
 * 		"icon": "monitor"
 *        },
 * 	"children": [{
 * 		"name": "操作日志",
 * 		"path": "logs",
 * 		"component": "monitor/log/index",
 * 		"meta": {
 * 			"title": "操作日志",
 * 			"icon": "log"
 *        }
 *    }]
 * }
 * @author CREATED BY L.C.Y on 2019-4-8
 */
@Data
@Accessors(chain = true)
public class MenuTreeEntity {
    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 重定向地址
     */
    private String redirect;

    /**
     * vue组件
     */
    private String component;

    /**
     * 是否总是显示
     * true - 是， false - 否
     */
    private String alwaysShow;

    /**
     * 菜单图标
     */
    private MenuMetaEntity meta;

    /**
     * 子菜单
     */
    private List<MenuTreeEntity> children;

}
