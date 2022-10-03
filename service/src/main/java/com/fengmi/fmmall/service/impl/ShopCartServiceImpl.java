package com.fengmi.fmmall.service.impl;

import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.dao.ShoppingCartMapper;
import com.fengmi.fmmall.entity.ShoppingCart;
import com.fengmi.fmmall.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ShopCartServiceImpl implements ShopCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public ResultVo shopCartadd(ShoppingCart cart) {
        cart.setCartTime(sdf.format(new Date()));
        int insert = shoppingCartMapper.insert(cart);
        if (insert > 0) {
            return new ResultVo(ResStauts.OK, "success", null);
        } else {
            return new ResultVo(ResStauts.NO, "fail", null);

        }

    }
}
