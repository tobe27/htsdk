package com.lltech.system.modules.player.dao;

import com.lltech.system.modules.player.model.PlayerDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlayerDOMapper {
    /**
     * 删除玩家
     * @param playerId 玩家id
     * @return count
     */
    int deleteByPrimaryKey(Long playerId);

    /**
     * 新增玩家 -玩家注册，此接口暂不使用
     * @param record
     * @return
     */
    int insertSelective(PlayerDO record);

    /**
     * 查看玩家详情
     * @param playerId
     * @return
     */
    PlayerDO selectByPrimaryKey(Long playerId);

    /**
     * 玩家列表，可根据账号、手机号、IMEI码、游戏id、渠道id、账号状态、注册时间区间查询
     * @param record 账号、手机号、IMEI码、游戏id、渠道id、账号状态、注册时间区间
     * @return 玩家列表
     */
    List<PlayerDO> listPlayerSelective(PlayerDO record);

    /**
     * 更新玩家信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(PlayerDO record);
}
