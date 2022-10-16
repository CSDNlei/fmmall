package com.fengmi.fmmall.service.impl;

import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.dao.ShoppingCartMapper;
import com.fengmi.fmmall.entity.ShoppingCart;
import com.fengmi.fmmall.entity.ShoppingCartVo;
import com.fengmi.fmmall.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listShopCartByUserId(int userId) {
        List<ShoppingCartVo> shoppingCartVos = shoppingCartMapper.selectshopcartByUserId(userId);
        return new ResultVo(ResStauts.OK, "success", shoppingCartVos);
    }

    @Override
    public ResultVo updatecartNum(int cartId, String cartNum) {
        int i = shoppingCartMapper.updatecartNumById(cartId, cartNum);
        if (i > 0) {
            return new ResultVo(ResStauts.OK, "update.success", i);
        } else {
            return new ResultVo(ResStauts.NO, "update.fail", null);

        }
    }

    @Override
    public ResultVo deletecartById(int cartId) {
        int i = shoppingCartMapper.deleteshopcartById(cartId);
        if (i > 0) {
            return new ResultVo(ResStauts.OK, "delete.success", i);
        } else {
            return new ResultVo(ResStauts.NO, "delete.fail", null);
        }
    }

    @Override
    public ResultVo listShopCartByCids(String cids) {
        String[] arr = cids.split(",");
        List<Integer> cartIds  = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            cartIds.add(Integer.parseInt(arr[i]));
        }
        List<ShoppingCartVo> shoppingCartVos = shoppingCartMapper.selectShopcartByCids(cartIds);
        return new ResultVo(ResStauts.OK, "success", shoppingCartVos);
    }
}
