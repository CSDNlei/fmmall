package com.fengmi.fmmall.service;

import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.entity.Orders;

import java.sql.SQLException;

public interface OrderService {
    /**
     * 保存订单接口
     * @return
     */
    ResultVo Orderaddr(String cids, Orders orders) throws SQLException;
}
