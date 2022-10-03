package com.fengmi.fmmall.service;

import com.fengmi.famall.vo.ResultVo;

public interface ProductService {
    ResultVo selectProductVo();

    ResultVo getProductbasicinfo(String productId);

    ResultVo getProductParamsByid(String productId);
}
