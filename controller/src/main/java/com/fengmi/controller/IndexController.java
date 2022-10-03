package com.fengmi.controller;

import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.service.CategoryService;
import com.fengmi.fmmall.service.IndexImgService;
import com.fengmi.fmmall.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/index")
@Api(value = "提供首页数据显示所用接口", tags = "首页管理")
public class IndexController {
    @Autowired
    private IndexImgService indexImgService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @GetMapping("/indeximg")
    @ApiOperation("首页轮播图接口")

    public ResultVo listIndexImgs() {
        return indexImgService.listIndexImgs();
    }


    @GetMapping("/cotegory")
    @ApiOperation("商品分类查询接口")
    public ResultVo listcategories() {
        return categoryService.listcategories();
    }

    @GetMapping("/list-commend")
    @ApiOperation("新品推荐查询接口")
    public ResultVo listProduct() {
        return productService.selectProductVo();
    }

    @GetMapping("/first-commend")
    @ApiOperation("分类推荐查询接口")
    public ResultVo listFirstBycategoryies() {
        return categoryService.listFirstcategories();
    }
}
