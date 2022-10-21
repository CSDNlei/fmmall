package com.fengmi.controller;

import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.entity.Orders;
import com.fengmi.fmmall.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;

@RestController
@CrossOrigin
@RequestMapping("/order")
@Api(value = "提供订单提交信息相关接口", tags = "订单管理")
public class OrderController {
    @Resource
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVo add(String cids, @RequestBody Orders orders) {
        try {
            return orderService.Orderaddr(cids, orders);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultVo(ResStauts.NO, "提交订单失败！", null);
        }

    }

}

