package com.fengmi.controller;

import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.entity.Users;
import com.fengmi.fmmall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Api(value = "提供用户的登录、注册接口", tags = "用户管理")
@CrossOrigin
public class UserController {
    @Resource
    private UserService userService;

    //url  http：//ip:port/fmmall/user/login
    //params    参数类型   参数个数   参数含义
    //method          GET
    //响应数据说明
    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "username", value = "用户登录账号", required = true),
            @ApiImplicitParam(dataType = "string", name = "password", value = "用户登录密码", required = true),

    })
    @GetMapping("/login")
    public ResultVo login(@RequestParam("username") String name,
                          @RequestParam("password") String pwd) {
        return userService.checklogin(name, pwd);

    }


    @ApiOperation("用户注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string", name = "username", value = "用户注册账号", required = true),
            @ApiImplicitParam(dataType = "string", name = "password", value = "用户注册密码", required = true)
    })
    @PostMapping("/regist")
    public ResultVo regist(@RequestBody Users users) {
        return userService.userRegist(users.getUsername(), users.getPassword());

    }

    @ApiOperation("校验token是否过期接口")
    @GetMapping("/check")
    public ResultVo userTokencheck(@RequestHeader("token") String token) {
        return new ResultVo(ResStauts.OK, "success", null);
    }
}
