package com.fengmi.fmmall.service;

import com.fengmi.famall.vo.ResultVo;

public interface CategoryService {
     /**
      * 查询分类列表（包括三个级别的分类）
      * @return
      */
     ResultVo listcategories();

     /**
      * 查询所有一级分类 并查询当前一级分类下销量最高的六个商品
      * @return
      */
     ResultVo listFirstcategories();
}
