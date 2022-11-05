package com.fengmi.fmmall.service.TImeJob;

import com.fengmi.fmmall.dao.OrderItemMapper;
import com.fengmi.fmmall.dao.OrdersMapper;
import com.fengmi.fmmall.dao.ProductSkuMapper;
import com.fengmi.fmmall.entity.OrderItem;
import com.fengmi.fmmall.entity.Orders;
import com.fengmi.fmmall.entity.ProductSku;
import com.fengmi.fmmall.service.OrderService;
import com.github.wxpay.sdk.WXPay;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderTimeoutCheckJob {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderService orderService;
    private WXPay wxPay = new WXPay(new MyPayConfig());

    @Scheduled(cron = "0/60 * * * * ? ")
    public void checkoutJob() {
        /**查询订单超过30min依然为未支付状态的订单**/
        try {
            System.out.println("---------1");
            Example example = new Example(Orders.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("status", "1");
            Date date = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
            criteria.andLessThan("createTime", date);
            List<Orders> orders = ordersMapper.selectByExample(example);

            /**访问微信平台接口 确认当前订单的最终支付状态**/

            for (int i = 0; i < orders.size(); i++) {
                Orders order = orders.get(i);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("out_trade_no", order.getOrderId());
                Map<String, String> query = wxPay.orderQuery(hashMap);
                if ("SUCCESS".equalsIgnoreCase(query.get("trade_state"))) {
                    /**如果订单已经支付 则修改支付状态 未发货/已支付  status=1  ===>  status=2**/
                    Orders orders1 = new Orders();
                    orders1.setOrderId(order.getOrderId());
                    orders1.setStatus("2");
                    ordersMapper.updateByPrimaryKeySelective(orders1);
                } else if ("NOTPAY".equalsIgnoreCase(query.get("trade_state"))) {
                    /**如果确实未支付 则取消订单**/
                 /* 1.向微信平台发送请求 关闭当前订单的支付链接*/
                    Map<String, String> map = wxPay.closeOrder(hashMap);
                    System.out.println(map);
                /*2.关闭订单*/
                    orderService.closeOrder(order.getOrderId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
