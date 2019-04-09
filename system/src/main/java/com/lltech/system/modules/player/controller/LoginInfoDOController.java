package com.lltech.system.modules.player.controller;

import com.github.pagehelper.PageInfo;
import com.lltech.common.utils.ResultBean;
import com.lltech.system.logging.annotation.Log;
import com.lltech.system.modules.player.model.LoginInfoDO;
import com.lltech.system.modules.player.service.LoginInfoDOService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CREATED BY L.C.Y on 2019-4-9
 */
@RestController
@RequestMapping("/player/login/info")
public class LoginInfoDOController {

    private final LoginInfoDOService loginInfoDOService;

    @Autowired
    public LoginInfoDOController(LoginInfoDOService loginInfoDOService) {
        this.loginInfoDOService = loginInfoDOService;
    }

    @Log("查看登录记录")
    @GetMapping("/list")
    @RequiresPermissions("player_login_info_list")
    public ResultBean list(Long playerId, Integer pageNum, Integer pageSize) {
        PageInfo<LoginInfoDO> pageInfo =
                new PageInfo<>(loginInfoDOService.listByPlayerId(playerId, pageNum, pageSize));

        return new ResultBean().ok(pageInfo.getTotal(), pageInfo.getList());
    }
}
