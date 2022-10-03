package com.fengmi.controller;

import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.service.ProductCommentService;
import com.fengmi.fmmall.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/product")
@Api(value = "提供商品信息相关接口", tags = "商品管理")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCommentService productCommentService;

    @GetMapping("/detail/{cid}")
    @ApiOperation("商品基本信息查询接口")
    public ResultVo getProductbasicinfo(@PathVariable("cid") String cid) {
        return productService.getProductbasicinfo(cid);
    }

    @GetMapping("/detail_params/{cid}")
    @ApiOperation("商品参数信息查询接口")
    public ResultVo getProductParams(@PathVariable("cid") String cid) {
        return productService.getProductParamsByid(cid);
    }

    @GetMapping("/detail_comments/{oid}")
    @ApiOperation("商品评论信息查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int", name = "pageNum", value = "当前页码", required = true),
            @ApiImplicitParam(dataType = "int", name = "limit", value = "每页显示条数", required = true),
    })
    public ResultVo getProductComments(@PathVariable("oid") String oid,int pageNum,int limit) {
        return productCommentService.listProductCommentById(oid,pageNum,limit);
    }
    @GetMapping("/detail_paramsCounts/{cid}")
    @ApiOperation("商品评价信息查询接口")
    public ResultVo getProductParamsCounts(@PathVariable("cid") String cid) {
        return productCommentService.getCommentcountById(cid);
    }
}