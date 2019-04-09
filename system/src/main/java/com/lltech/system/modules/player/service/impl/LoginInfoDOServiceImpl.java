package com.lltech.system.modules.player.service.impl;

import com.github.pagehelper.PageHelper;
import com.lltech.system.modules.player.dao.LoginInfoDOMapper;
import com.lltech.system.modules.player.model.LoginInfoDO;
import com.lltech.system.modules.player.service.LoginInfoDOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CREATED BY L.C.Y on 2019-4-9
 */
@Service
public class LoginInfoDOServiceImpl implements LoginInfoDOService {
    private final LoginInfoDOMapper loginInfoDOMapper;

    @Autowired
    public LoginInfoDOServiceImpl(LoginInfoDOMapper loginInfoDOMapper) {
        this.loginInfoDOMapper = loginInfoDOMapper;
    }

    /**
     * 登录记录列表
     *
     * @param playerId 玩家id
     * @param pageNum  页码
     * @param pageSize 页行数
     * @return 列表
     */
    @Override
    public List<LoginInfoDO> listByPlayerId(Long playerId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return loginInfoDOMapper.listByPlayerId(playerId);
    }
}
