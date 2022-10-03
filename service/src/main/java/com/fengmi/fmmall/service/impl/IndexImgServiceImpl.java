package com.fengmi.fmmall.service.impl;

import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.dao.IndexImgMapper;
import com.fengmi.fmmall.entity.IndexImg;
import com.fengmi.fmmall.service.IndexImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexImgServiceImpl implements IndexImgService {
    @Autowired
    private IndexImgMapper indexImgMapper;

    @Override
    public ResultVo listIndexImgs() {
        List<IndexImg> indexImgs = indexImgMapper.listIndexImgs();
        if (indexImgs.isEmpty()) {
            return new ResultVo(ResStauts.NO, "fail", null);
        } else {
            return new ResultVo(ResStauts.OK, "success", indexImgs);

        }

    }
}
