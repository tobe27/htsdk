package com.lltech.system.modules.order.service;

import com.lltech.system.modules.order.model.OrderInfoDO;

import java.util.List;

/**
 * @author CREATED BY L.C.Y on 2019-4-9
 */
public interface OrderInfoDOService {

    /**
     * 玩家订单列表
     * @param playerId 玩家id
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return 订单列表
     */
    List<OrderInfoDO> listOrderByPlayerId(Long playerId, Integer pageNum, Integer pageSize);
}
