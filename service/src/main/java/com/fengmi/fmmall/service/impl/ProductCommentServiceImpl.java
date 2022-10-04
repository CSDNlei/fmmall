package com.fengmi.fmmall.service.impl;

import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.famall.vo.utils.PageHelper;
import com.fengmi.fmmall.dao.ProductCommentsMapper;
import com.fengmi.fmmall.entity.ProductComments;
import com.fengmi.fmmall.entity.ProductCommentsVo;
import com.fengmi.fmmall.service.ProductCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class ProductCommentServiceImpl implements ProductCommentService {
    private static final String ProductId = "productId";
    private static final String CommType = "commType";

    @Autowired
    private ProductCommentsMapper productCommentsMapper;

    @Override
    public ResultVo listProductCommentById(String productId, int pageNum, int limit) {
//        1.根据商品id查询总记录数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(ProductId);
        int count = productCommentsMapper.selectCountByExample(example);
//        2.计算总页数  必须确定每页显示多少条 pagesize=limit
        int pageCount = count % limit == 0 ? count / limit : count / limit + 1;
//          3.查询当前页的数据（因为评论中需要用户信息 因此需要连表查询 ----自定义）
        int start = (pageNum - 1) * limit;
        List<ProductCommentsVo> list = productCommentsMapper.selectCommentsById(productId, start, limit);
        return new ResultVo(ResStauts.OK, "success", new PageHelper<ProductCommentsVo>(count, pageCount, list));


    }

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    @Override
    public ResultVo getCommentcountById(String productId) {
//            1.根据商品id查询总评价数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo(ProductId);
        int total = productCommentsMapper.selectCountByExample(example);


//          2.查询好评数
        criteria.andEqualTo(CommType, 1);
        int goodTotal = productCommentsMapper.selectCountByExample(example);


//         3.查询中评数
        Example example1 = new Example(ProductComments.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo(ProductId);
        criteria1.andEqualTo("commType", 0);
        int midTotal = productCommentsMapper.selectCountByExample(example1);


//        4.查询差评数
        Example example2 = new Example(ProductComments.class);
        Example.Criteria criteria2 = example2.createCriteria();
        criteria2.andEqualTo(productId);
        criteria2.andEqualTo("commType", -1);
        int badTotal = productCommentsMapper.selectCountByExample(example2);


//        5.计算好评率
        //noinspection AlibabaLowerCamelCaseVariableNaming
        double Praiserate = (Double.parseDouble(goodTotal + "") / Double.parseDouble(total + "")) * 100;
        String substring = (Praiserate + "").substring(0, (Praiserate + "").lastIndexOf(".") + 3);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("goodTotal", goodTotal);
        map.put("midTotal", midTotal);
        map.put("badTotal", badTotal);
        map.put("Praiserate", substring);
        return new ResultVo(ResStauts.OK, "success", map);
    }
}
