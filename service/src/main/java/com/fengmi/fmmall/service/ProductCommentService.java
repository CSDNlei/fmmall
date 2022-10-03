package com.fengmi.fmmall.service;

import com.fengmi.famall.vo.ResultVo;

public interface ProductCommentService {
    /**
     * 根据商品id实现评论的分页查询
     * @param productId 商品id
     * @param pageNum 查询页码
     * @param limit 每页显示条数
     * @return
     */
    ResultVo listProductCommentById(String productId, int pageNum, int limit);

    /**
     * 根据商品id查询当前商品的评价信息
     * @param productId
     * @return
     */
    ResultVo getCommentcountById(String productId);
}
