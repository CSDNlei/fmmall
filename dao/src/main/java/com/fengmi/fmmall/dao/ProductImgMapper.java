package com.fengmi.fmmall.dao;

import com.fengmi.fmmall.entity.Product;
import com.fengmi.fmmall.entity.ProductImg;
import com.fengmi.fmmall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductImgMapper extends GeneralDao<ProductImg> {
     List<Product> selectProductimgs(int productId);
}