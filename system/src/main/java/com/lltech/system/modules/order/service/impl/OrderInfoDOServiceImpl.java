package com.lltech.system.modules.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.lltech.system.modules.order.dao.OrderInfoDOMapper;
import com.lltech.system.modules.order.model.OrderInfoDO;
import com.lltech.system.modules.order.service.OrderInfoDOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CREATED BY L.C.Y on 2019-4-9
 */
@Service
public class OrderInfoDOServiceImpl implements OrderInfoDOService {
    private final OrderInfoDOMapper orderInfoDOMapper;

    @Autowired
    public OrderInfoDOServiceImpl(OrderInfoDOMapper orderInfoDOMapper) {
        this.orderInfoDOMapper = orderInfoDOMapper;
    }


    /**
     * 玩家订单列表
     *
     * @param playerId 玩家id
     * @param pageNum  页码
     * @param pageSize 页大小
     * @return 订单列表
     */
    @Override
    public List<OrderInfoDO> listOrderByPlayerId(Long playerId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return orderInfoDOMapper.listOrderByPlayerId(playerId);
    }
}
