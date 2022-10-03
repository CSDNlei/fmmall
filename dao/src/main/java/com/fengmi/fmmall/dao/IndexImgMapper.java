package com.fengmi.fmmall.dao;

import com.fengmi.fmmall.entity.IndexImg;
import com.fengmi.fmmall.general.GeneralDao;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IndexImgMapper extends GeneralDao<IndexImg> {
//        查询轮播图 查找status=1 使用seq排序
    List<IndexImg> listIndexImgs();
}