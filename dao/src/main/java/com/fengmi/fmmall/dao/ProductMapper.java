package com.fengmi.fmmall.dao;

import com.fengmi.fmmall.entity.Product;
import com.fengmi.fmmall.entity.ProductVo;
import com.fengmi.fmmall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 根据三级分类id分页查询商品信息
     * @param cid 三级分类id
     * @param start 起始索引
     * @param limit 查询记录数
     * @return
     */
    List<ProductVo> selectcategoryById(@Param("cid") int cid,
                                       @Param("start")int start,
                                       @Param("limit")int limit);
}
