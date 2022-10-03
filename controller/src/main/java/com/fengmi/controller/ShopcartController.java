package com.fengmi.controller;

import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.entity.ShoppingCart;
import com.fengmi.fmmall.service.ShopCartService;
import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/shopcart")
@Api(value = "提供购物车业务相关接口", tags = "购物车管理")

public class ShopcartController {
    @Autowired
    private ShopCartService shopCartService;


    @GetMapping("/list")
    @ApiImplicitParam(dataType = "string", name = "token", value = "授权令牌", required = true)
    public ResultVo listcarts(String token) {

//          1.获取token
//          2.校验token
        if (token == null) {
            return new ResultVo(ResStauts.NO, "请先登录！", "null");
        } else {

            try {
//                验证token
                JwtParser parser = Jwts.parser();
//            解析token  必须和生成的token密码一致
                parser.setSigningKey("leilei66");
//            如果token正确（密码正确 有效期内）则正常执行 否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
//                获取token中的用户数据
                Claims body = claimsJws.getBody();
//                获取生成token中subject的值
//                获取生成token时存储的claims的map中的值
                String keys = body.get("key1", String.class);
                return new ResultVo(ResStauts.OK, "success", keys);
            } catch (ExpiredJwtException e) {
                return new ResultVo(ResStauts.NO, "登录过期，请重新登录！", "null");
            } catch (UnsupportedJwtException e) {
                return new ResultVo(ResStauts.NO, "Token不合法！请自重！", "null");
            } catch (Exception e) {
                return new ResultVo(ResStauts.NO, "请重新登录！", "null");

            }
        }

    }

    @PostMapping("/add")
    @ApiOperation("购物车信息添加接口")
    public ResultVo shopcartadd(@RequestBody ShoppingCart cart, @RequestHeader("token") String token) {
        return shopCartService.shopCartadd(cart);

    }
}
