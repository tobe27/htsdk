package com.lltech.system.modules.player.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class LoginInfoDO {

    /**
     * 登录记录id
     */
    private Long id;

    /**
     * 登录玩家id
     */
    private Long playerId;

    /**
     * 登录游戏id
     */
    private Long gameId;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 登录设备IMEI码
     */
    private String imeiCode;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录设备类型，0- Android, 1- iOS
     */
    private Byte deviceType;

    /**
     * 登录token
     */
    private String token;

    // ============关联参数==================

    /**
     * 游戏名称
     */
    private String gameName;

}
