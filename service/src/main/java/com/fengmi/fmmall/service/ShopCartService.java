package com.fengmi.fmmall.service;

import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.entity.ShoppingCart;

public interface ShopCartService {
      ResultVo shopCartadd(ShoppingCart cart);

      ResultVo listShopCartByUserId(int userId);
}
