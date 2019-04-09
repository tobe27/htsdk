package com.lltech.system.modules.player.service;

import com.lltech.system.modules.player.model.PlayerDO;

import java.util.List;

/**
 * @author CREATED BY L.C.Y on 2019-4-9
 */
public interface PlayerDOService {

    /**
     * 查看玩家详情
     * 包括 充值记录、登录记录
     * @param playerId playerId
     * @return 玩家信息
     */
    PlayerDO selectByPrimaryKey(Long playerId);

    /**
     * 玩家列表，可根据账号、手机号、IMEI码、游戏id、渠道id、账号状态、注册时间区间查询
     * @param record 账号、手机号、IMEI码、游戏id、渠道id、账号状态、注册时间区间
     * @return 玩家列表
     */
    List<PlayerDO> listPlayerSelective(PlayerDO record, Integer pageNum, Integer pageSize);

    /**
     * 更新玩家信息
     * @param record 手机号、密码
     * @return count
     */
    int updateByPrimaryKeySelective(PlayerDO record);

    /**
     * 更新状态， 1- 正常， 0- 冻结
     * @param playerId
     * @return
     */
    int updateStatus(Long playerId);
}
