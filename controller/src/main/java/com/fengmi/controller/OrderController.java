package com.fengmi.controller;

import com.alipay.api.domain.AlipayItemLimitPeriodInfo;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.fengmi.config.MyPayConfig;
import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.entity.Orders;
import com.fengmi.fmmall.service.OrderService;
import com.github.wxpay.sdk.WXPay;
import com.sun.org.apache.bcel.internal.generic.NEW;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/order")
@Api(value = "提供订单提交信息相关接口", tags = "订单管理")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVo add(String cids, @RequestBody Orders orders) {
        try {
            Map<String, String> orderaddr = orderService.Orderaddr(cids, orders);
            if (orderaddr != null) {
                String orderId = orderaddr.get("orderId");
                HashMap<String, String> data = new HashMap<>();
                data.put("body", orderaddr.get("productNames"));  //商品描述
                data.put("out_trade_no", orderId); //将当前的用户订单编号作为当前支付交易的交易号
                data.put("fee_type", "CNY"); //支付币种
//                data.put("total_fee", orders.getActualAmount() * 100 + ""); //支付金额
                data.put("total_fee", "1"); //支付金额
                data.put("trade_type", "NATIVE"); //交易类型
                data.put("notify_url", "http://yhn3zg.natappfree.cc/pay/callback"); //交易类型
//           发送请求  获取回应
//            微信支付 申请支付链接
                WXPay wxPay = new WXPay(new MyPayConfig());
                Map<String, String> unifiedOrder = wxPay.unifiedOrder(data);
                orderaddr.put("payUrl", unifiedOrder.get("code_url"));
                return new ResultVo(ResStauts.OK, "提交订单成功！", orderaddr);
            } else {
                return new ResultVo(ResStauts.NO, "提交订单失败！", null);
            }
        } catch (SQLException e) {
            return new ResultVo(ResStauts.NO, "提交订单失败！", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



     @GetMapping("/status/{oid}")
    public ResultVo getOrderStatus(@PathVariable("oid") String orderId,@RequestHeader("token") String token) {
        return orderService.getOrderById(orderId);
    }


}

