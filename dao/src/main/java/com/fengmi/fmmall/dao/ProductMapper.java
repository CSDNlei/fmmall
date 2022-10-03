package com.fengmi.fmmall.dao;

import com.fengmi.fmmall.entity.Product;
import com.fengmi.fmmall.entity.ProductVo;
import com.fengmi.fmmall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductMapper extends GeneralDao<Product> {
    List<ProductVo> selectProductVo();
    /**
     *  查询指定一级分类下销量前6的商品
     * @param cid
     * @return
     */
    List<ProductVo> selectTop6Byproduct(int cid);
}
