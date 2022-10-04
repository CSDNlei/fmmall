package com.fengmi.fmmall.service.impl;

import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.dao.ProductImgMapper;
import com.fengmi.fmmall.dao.ProductMapper;
import com.fengmi.fmmall.dao.ProductParamsMapper;
import com.fengmi.fmmall.dao.ProductSkuMapper;
import com.fengmi.fmmall.entity.*;
import com.fengmi.fmmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImgMapper productImgMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductParamsMapper productParamsMapper;

    @Override
    public ResultVo selectProductVo() {
        List<ProductVo> productVos = productMapper.selectProductVo();
        return new ResultVo(ResStauts.OK, "success", productVos);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ResultVo getProductbasicinfo(String productId) {
//        商品基本信息
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        criteria.andEqualTo("productStatus", 1);//状态1表示上架商品
        List<Product> products = productMapper.selectByExample(example);
        if (products.size() > 0) {
//         商品图片

            Example example1 = new Example(ProductImg.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("itemId", productId);
            List<ProductImg> productImgs = productImgMapper.selectByExample(example1);

//        商品套餐
            Example example2 = new Example(ProductSku.class);
            Example.Criteria criteria2 = example2.createCriteria();
            criteria2.andEqualTo("productId", productId);
            criteria2.andEqualTo("status", 1);
            List<ProductSku> productSkus = productSkuMapper.selectByExample(example2);

            HashMap<String, Object> map = new HashMap<>();
            map.put("product", products.get(0));
            map.put("productImgs", productImgs);
            map.put("productSkus", productSkus);
            return new ResultVo(ResStauts.OK, "success", map);
        } else {


            return new ResultVo(ResStauts.NO, "您查找的商品信息不存在！", null);
        }
    }

    @Override
    public ResultVo getProductParamsByid(String productId) {
        Example example = new Example(ProductParams.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        List<ProductParams> productParams = productParamsMapper.selectByExample(example);
        if (productParams.size() > 0) {
            return new ResultVo(ResStauts.OK, "success", productParams.get(0));
        } else {
            return new ResultVo(ResStauts.NO, "此产品可能为三无产品！", null);
        }
    }
}