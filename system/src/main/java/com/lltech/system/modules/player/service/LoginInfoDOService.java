package com.lltech.system.modules.player.service;

import com.lltech.system.modules.player.model.LoginInfoDO;

import java.util.List;

/**
 * @author CREATED BY L.C.Y on 2019-4-9
 */
public interface LoginInfoDOService {

    /**
     * 登录记录列表
     * @param playerId 玩家id
     * @param pageNum 页码
     * @param pageSize 页行数
     * @return 列表
     */
    List<LoginInfoDO> listByPlayerId(Long playerId, Integer pageNum, Integer pageSize);
}
