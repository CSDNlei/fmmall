package com.fengmi.fmmall.service;

import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.entity.Orders;

import java.sql.SQLException;
import java.util.Map;

public interface OrderService {
    /**
     * 保存订单接口
     * @return
     */
    Map<String, String> Orderaddr(String cids, Orders orders) throws SQLException;

    /**
     * 用户支付成功以后  修改订单状态
     */
     int UpdateOrderStauts(String orderId,String status);
    /**
     * 显示订单支付结果
     */
    ResultVo getOrderById(String orderId);
}
