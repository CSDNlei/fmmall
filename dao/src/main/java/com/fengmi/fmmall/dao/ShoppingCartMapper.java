package com.fengmi.fmmall.dao;

import com.fengmi.fmmall.entity.ShoppingCart;
import com.fengmi.fmmall.entity.ShoppingCartVo;
import com.fengmi.fmmall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
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

     /**
      * 根据购物车id修改购物车中商品数量
      * @param cartId
      * @param cartNum
      * @return
      */
     int updatecartNumById(@Param("cartId") int cartId,
                          @Param("cartNum") String cartNum);

     /**
      * 根据购物车id删除购物车数据
      * @param cartId
      * @return
      */
     int deleteshopcartById(int cartId);

     /**
      * 查询购物车记录
      * @param cids
      * @return
      */
     List<ShoppingCartVo> selectShopcartByCids(List<Integer> cids);
}