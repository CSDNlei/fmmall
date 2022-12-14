package com.fengmi.fmmall.service;

import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.entity.ShoppingCart;

public interface ShopCartService {
    ResultVo shopCartadd(ShoppingCart cart);

    ResultVo listShopCartByUserId(int userId);

    ResultVo updatecartNum(int cartId,
                           String cartNum);

    ResultVo deletecartById(int cartId);

    /**
     * 选择购物车记录
     * @param cids
     * @return
     */
    ResultVo listShopCartByCids(String cids);
}
