package com.lltech.system.modules.order.dao;

import com.lltech.system.modules.order.model.OrderInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderInfoDOMapper {
    int deleteByPrimaryKey(Long orderId);

    int insertSelective(OrderInfoDO record);

    OrderInfoDO selectByPrimaryKey(Long orderId);

    /**
     * 玩家订单列表
     * @param playerId 玩家id
     * @return 订单列表
     */
    List<OrderInfoDO> listOrderByPlayerId(Long playerId);

    int updateByPrimaryKeySelective(OrderInfoDO record);
}
