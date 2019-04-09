package com.lltech.system.modules.player.service.impl;

import com.github.pagehelper.PageHelper;
import com.lltech.common.utils.Md5Util;
import com.lltech.common.utils.StringUtils;
import com.lltech.system.modules.player.model.PlayerDO;
import com.lltech.system.modules.player.dao.PlayerDOMapper;
import com.lltech.system.modules.player.service.PlayerDOService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author CREATED BY L.C.Y on 2019-4-9
 */
@Slf4j
@Service
public class PlayerDOServiceImpl implements PlayerDOService {
    private final PlayerDOMapper playerDOMapper;

    @Autowired
    public PlayerDOServiceImpl(PlayerDOMapper playerDOMapper) {
        this.playerDOMapper = playerDOMapper;
    }

    /**
     * 查看玩家详情
     * @param playerId playerId
     * @return 玩家信息
     */
    @Override
    public PlayerDO selectByPrimaryKey(Long playerId) {
        return playerDOMapper.selectByPrimaryKey(playerId);
    }

    /**
     * 玩家列表，可根据账号、手机号、IMEI码、游戏id、渠道id、账号状态、注册时间区间查询
     * 分页
     * @param record 账号、手机号、IMEI码、游戏id、渠道id、账号状态、注册时间区间
     * @return 玩家列表
     */
    @Override
    public List<PlayerDO> listPlayerSelective(PlayerDO record, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return playerDOMapper.listPlayerSelective(record);
    }

    /**
     * 更新玩家信息，手机号、密码
     *
     * @param record 手机号、密码
     * @return count
     */
    @Override
    public int updateByPrimaryKeySelective(PlayerDO record) {
        // 只变更手机号和密码
        PlayerDO player = new PlayerDO();
        player.setPlayerId(record.getPlayerId())
                .setMobile(record.getMobile())
                .setGmtModified(new Date());

        // 如果密码不为空，则MD5加密
        if (StringUtils.isNotBlank(record.getPassword())) {
            player.setPassword(Md5Util.generateMD5(record.getPassword()));
        }

        return playerDOMapper.updateByPrimaryKeySelective(player);
    }

    /**
     * 更新状态， 1- 正常， 0- 冻结
     *
     * @param playerId
     * @return
     */
    @Override
    public int updateStatus(Long playerId) {
        // 查询状态，并置为相反状态
        PlayerDO updatePlayer = new PlayerDO();
        PlayerDO player = playerDOMapper.selectByPrimaryKey(playerId);
        updatePlayer.setPlayerId(playerId)
                .setStatus((byte) (player.getStatus() == 0 ? 1 : 0));

        return playerDOMapper.updateByPrimaryKeySelective(updatePlayer);
    }
}
