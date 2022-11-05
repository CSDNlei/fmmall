package com.fengmi.fmmall.dao;

import com.fengmi.fmmall.entity.ProductSku;
import com.fengmi.fmmall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSkuMapper extends GeneralDao<ProductSku> {
    /**
     * 根据商品id  查询当前商品下套餐价格最低的套餐
     * @param productId
     * @return
     */
    List<ProductSku> selectLowerestByProductId(String productId);
}