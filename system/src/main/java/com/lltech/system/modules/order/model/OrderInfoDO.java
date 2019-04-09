package com.lltech.system.modules.order.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class OrderInfoDO {
    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单号，生成规则 = 时间戳 + 8位随机数字，保证唯一
     */
    private String orderNo;

    /**
     * 订单金额，单位-分
     */
    private Integer orderAmount;

    /**
     * 订单支付方式， 0- 微信， 1- 支付宝， 2- 苹果
     */
    private Byte payMethod;

    /**
     * 订单支付状态，0- 待支付， 1- 支付失败, 2- 已支付待发货， 3- 发货失败， 4- 发货成功订单完成
     */
    private Byte status;

    /**
     * 游戏服务器响应码
     */
    private String responseCode;

    /**
     * 游戏服务器响应信息
     */
    private String responseMsg;

    /**
     * 向游戏服务器发送订单时间
     */
    private Date sendTime;

    /**
     * 游戏服务器响应时间
     */
    private Date responseTime;

    /**
     * 订单时间
     */
    private Date orderTime;

    /**
     * 订单支付内容
     */
    private String payContent;

    /**
     * 订单的支付玩家id
     */
    private Long playerId;

    /**
     * 支付流水号 -第三方支付流水号
     */
    private String payFlowCode;

    /**
     * 校验异常订单请求
     */
    private String checkExceptionRequest;

    /**
     * 订单创建时间
     */
    private Date gmtCreate;

    /**
     * 订单变更时间
     */
    private Date gmtModified;
}
