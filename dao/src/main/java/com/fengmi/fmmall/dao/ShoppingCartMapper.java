package com.fengmi.fmmall.dao;

import com.fengmi.fmmall.entity.ShoppingCart;
import com.fengmi.fmmall.entity.ShoppingCartVo;
import com.fengmi.fmmall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartMapper extends GeneralDao<ShoppingCart> {
     /**
      * 根据用户id查询购物车用户数据
      * @param userId
      * @return
      */
     List<ShoppingCartVo> selectshopcartByUserId(int userId);
}