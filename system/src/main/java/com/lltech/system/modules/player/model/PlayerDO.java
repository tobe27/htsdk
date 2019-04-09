package com.lltech.system.modules.player.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class PlayerDO {
    /**
     * 玩家id
     */
    private Long playerId;

    /**
     * 玩家账号，与gameId联合唯一索引
     */
    private String playerAccount;

    /**
     * 玩家密码，md5加密
     */
    private String password;

    /**
     * 玩家类型，0-注册用户 1-游客
     */
    private String playType;

    /**
     * 账号状态，1-正常 0-失效
     */
    private Byte status;

    /**
     * 玩家手机，与gameId联合唯一索引
     */
    private String mobile;

    /**
     * 玩家性别，0-男 1-女 2-其他
     */
    private String sex;

    /**
     * 玩家年龄
     */
    private Integer age;

    /**
     * 玩家生日
     */
    private String birthday;

    /**
     * 玩家注册设备IMEI
     */
    private String imeiCode;

    /**
     * 玩家游戏
     */
    private String email;

    /**
     * 游戏id
     */
    private Long gameId;

    /**
     * 注册游戏渠道
     */
    private String channelId;

    /**
     * 游戏推广主链
     */
    private String extensionMainchain;

    /**
     * 游戏推广子链
     */
    private String extensionSubchain;

    /**
     * 游戏的BundleId
     */
    private String bundleId;

    /**
     * 玩家注册设备IDFA
     */
    private String idfa;

    /**
     * 玩家注册IP
     */
    private String registerIp;

    /**
     * 玩家头像
     */
    private String picture;

    /**
     * 玩家国籍
     */
    private String country;

    /**
     * 玩家省份
     */
    private String province;

    /**
     * 玩家城市
     */
    private String city;

    /**
     * 玩家职业
     */
    private String career;

    /**
     * 玩家公司
     */
    private String company;

    /**
     * 最近登录时间
     */
    private Date lastLoginTime;

    /**
     * 最近登录IP
     */
    private String lastLoginIp;

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 首次登录时间
     */
    private Date firstLoginTime;

    /**
     * 下载时间
     */
    private Date downloadTime;

    /**
     * 最近充值金额
     */
    private Integer recentRechargeAmount;

    /**
     * 最近充值时间
     */
    private Date recentRechargeTime;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;


    // ==========关联查询使用，以及查询条件使用============

    /**
     * 游戏名称
     */
    private String gameName;

    /**
     * 注册渠道名称
     */
    private String channelName;

    /**
     * 开始时间
     */
    private String startDate;

    /**
     * 结束时间
     */
    private String endDate;

}
