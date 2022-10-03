package com.fengmi.fmmall.dao;

import com.fengmi.fmmall.entity.Category;
import com.fengmi.fmmall.entity.CategoryVo;
import com.fengmi.fmmall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryMapper extends GeneralDao<Category> {
//           连接查询 只能查询一次
        List<CategoryVo> selectAllCategories();
//           子查询  根据parentid查询子分类
        List<CategoryVo> selectAllCategories2(int parentId);

//         查询一级分类下的商品
        List<CategoryVo> selectFirstCategories();
}