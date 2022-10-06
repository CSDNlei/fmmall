package com.fengmi.controller;

import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.service.UserAddrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/addr")
@RestController
@Api(value = "提供收货地址相关接口", tags = "收货地址管理")
public class UserAddrController {
      @Autowired
    private UserAddrService userAddrService;
      @GetMapping("/list")
      @ApiImplicitParam(dataType = "int", name = "userId", value = "用户id", required = true)
      public ResultVo listAddr(Integer userId, @RequestHeader("token")String token){
          return userAddrService.listAddrById(userId);
      }
}
