package com.fengmi.fmmall.service;

import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;

public interface ShopCartService {
    ResultVo shopCartadd(ShoppingCart cart);

    ResultVo listShopCartByUserId(int userId);

    ResultVo updatecartNum(int cartId,
                           String cartNum);
}
