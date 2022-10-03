package com.fengmi.fmmall.dao;

import com.fengmi.fmmall.entity.ProductComments;
import com.fengmi.fmmall.entity.ProductCommentsVo;
import com.fengmi.fmmall.general.GeneralDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCommentsMapper extends GeneralDao<ProductComments> {
    /**
     * 根据商品id分页查询评论信息
     *
     * @param productId 用户id
     * @param start     起始索引
     * @param limit     查询条数
     * @return
     */
    List<ProductCommentsVo> selectCommentsById(@Param("productId") String productId,
                                               @Param("start") int start,
                                               @Param("limit") int limit);
}