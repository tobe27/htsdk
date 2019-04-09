package com.lltech.system.modules.player.controller;

import com.github.pagehelper.PageInfo;
import com.lltech.common.utils.ResultBean;
import com.lltech.system.logging.annotation.Log;
import com.lltech.system.modules.order.model.OrderInfoDO;
import com.lltech.system.modules.order.service.OrderInfoDOService;
import com.lltech.system.modules.player.model.PlayerDO;
import com.lltech.system.modules.player.service.PlayerDOService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author CREATED BY L.C.Y on 2019-4-9
 */
@RestController
@RequestMapping("/player")
public class PlayerDOController {
    private final PlayerDOService playerDOService;
    private final OrderInfoDOService orderInfoDOService;

    @Autowired
    public PlayerDOController(PlayerDOService playerDOService, OrderInfoDOService orderInfoDOService) {
        this.playerDOService = playerDOService;
        this.orderInfoDOService = orderInfoDOService;
    }

    @Log("更改玩家状态")
    @PutMapping("/{playerId}/status")
    @RequiresPermissions("player_modify_status")
    public ResultBean updateStatus(@PathVariable Long playerId) {
        playerDOService.updateStatus(playerId);
        return new ResultBean().ok();
    }

    @Log("绑定玩家手机")
    @PutMapping("/{playerId}/mobile")
    @RequiresPermissions("player_modify_mobile")
    public ResultBean updateMobile(@PathVariable Long playerId, @RequestBody PlayerDO player) {
        PlayerDO updatePlayer = new PlayerDO();
        updatePlayer.setPlayerId(playerId).setMobile(player.getMobile());
        playerDOService.updateByPrimaryKeySelective(updatePlayer);
        return new ResultBean().ok();
    }

    @Log("修改玩家密码")
    @PutMapping("/{playerId}/password")
    @RequiresPermissions("player_modify_password")
    public ResultBean updatePassword(@PathVariable Long playerId, @RequestBody PlayerDO player) {
        PlayerDO updatePlayer = new PlayerDO();
        updatePlayer.setPlayerId(playerId).setPassword(player.getPassword());
        playerDOService.updateByPrimaryKeySelective(updatePlayer);
        return new ResultBean().ok();
    }

    @Log("玩家详情")
    @GetMapping("/{playerId}")
    @RequiresPermissions("player_detail")
    public ResultBean get(@PathVariable Long playerId) {
        return new ResultBean().ok(playerDOService.selectByPrimaryKey(playerId));
    }

    @Log("玩家列表")
    @GetMapping("/list")
    @RequiresPermissions("player_list")
    public ResultBean list(PlayerDO player, Integer pageNum, Integer pageSize) {
        PageInfo<PlayerDO> pageInfo = new PageInfo<>(playerDOService.listPlayerSelective(player, pageNum, pageSize));
        return new ResultBean().ok(pageInfo.getTotal(), pageInfo.getList());
    }

    @Log("查询玩家订单")
    @GetMapping("/order/info/list")
    @RequiresPermissions("player_order_info_list")
    public ResultBean list(Long playerId, Integer pageNum, Integer pageSize) {
        PageInfo<OrderInfoDO> pageInfo = new PageInfo<>(orderInfoDOService.listOrderByPlayerId(playerId, pageNum, pageSize));
        return new ResultBean().ok(pageInfo.getTotal(), pageInfo.getList());
    }

}
