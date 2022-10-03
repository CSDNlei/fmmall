package com.fengmi.fmmall.service.impl;

import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.famall.vo.utils.MD5Utils;
import com.fengmi.fmmall.dao.UsersMapper;
import com.fengmi.fmmall.entity.Users;
import com.fengmi.fmmall.service.UserService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    @Transactional
    public ResultVo userRegist(String name, String pwd) {
//        根据用户查询，这个用户是否已经被注册
        synchronized (this) {
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username", name);
            List<Users> users = usersMapper.selectByExample(example);
//         如果没有被注册，则进行保存操作
            if (users.isEmpty()) {
                String md5Pwd = MD5Utils.md5(pwd);
                Users user = new Users();
                user.setUsername(name);
                user.setPassword(md5Pwd);
                user.setUserImg("img/default.png");
                user.setUserModtime(new Date());
                user.setUserRegtime(new Date());
                int i = usersMapper.insertUseGeneratedKeys(user);
                if (i > 0) {
                    return new ResultVo(ResStauts.OK, "注册成功！", user);
                } else {
                    return new ResultVo(ResStauts.NO, "注册失败！", null);

                }

            } else {
                return new ResultVo(ResStauts.NO, "用户已经被注册！", null);
            }
        }
    }

    @Override
    public ResultVo checklogin(String name, String pwd) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", name);
        List<Users> users = usersMapper.selectByExample(example);
        if (users.isEmpty()) {
            return new ResultVo(ResStauts.NO, "登录失败！用户名不存在！", null);
        } else {
            String md5Pwd = MD5Utils.md5(pwd);
            if (md5Pwd.equals(users.get(0).getPassword())) {
//                  如果登录验证成功后 则需要生成验证令牌token（token就是按特定规则生成的字符串）

//                  使用jwt规则生成字符串
                JwtBuilder builder = Jwts.builder();
                HashMap<String, Object> map = new HashMap<>();
                map.put("key1", "value1");
                map.put("key2", "value2");
                String token = builder.setSubject(name)   //主题 就是token中携带的数据
                        .setIssuedAt(new Date())   //设置token的生成时间
                        .setId(users.get(0).getUserId() + "")// 根据用户的id生成token
                        .setClaims(map)//   map中可以存放的用户权限信息
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))//设置token过期时间  当前时间+一天时间过期
//                        .signWith(SignatureAlgorithm.ES256 ,"leilei66")//
                        .signWith(SignatureAlgorithm.HS256, "leilei66")//设置加密方式和加密密码
                        .compact();

                return new ResultVo(ResStauts.OK, token, users.get(0));

            } else {
                return new ResultVo(ResStauts.NO, "登录失败！，密码错误！", null);
            }
        }
    }
}
