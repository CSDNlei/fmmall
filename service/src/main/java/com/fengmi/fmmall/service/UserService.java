package com.fengmi.fmmall.service;

import com.fengmi.famall.vo.ResultVo;


public interface UserService {
    //                 用户注册
    ResultVo userRegist(String name, String pwd);
//
//    //     用户登录
    ResultVo checklogin(String name, String pwd);
}
