package com.fengmi.fmmall.service.impl;

import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.dao.CategoryMapper;
import com.fengmi.fmmall.entity.CategoryVo;
import com.fengmi.fmmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public ResultVo listcategories() {
        List<CategoryVo> categoryVos = categoryMapper.selectAllCategories();
           return new ResultVo(ResStauts.OK,"success",categoryVos);
    }

    @Override
    public ResultVo listFirstcategories() {
        List<CategoryVo> categoryVos = categoryMapper.selectFirstCategories();
        return new ResultVo(ResStauts.OK,"success",categoryVos);
    }
}
