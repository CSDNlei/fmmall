package com.fengmi.fmmall.service.TImeJob;

import com.fengmi.fmmall.dao.OrdersMapper;
import com.fengmi.fmmall.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Component
public class OrderTimeoutCheckJob {
    @Autowired
    private OrdersMapper ordersMapper;
    @Scheduled(cron = "0/3 * * * * ? ")
    public void checkoutJob(){
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");
        Date date = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
        criteria.andLessThan("createTime",date);
        List<Orders> orders = ordersMapper.selectByExample(example);
    }
}
