package com.fengmi.fmmall.service.impl;

import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import com.fengmi.fmmall.dao.UserAddrMapper;
import com.fengmi.fmmall.entity.UserAddr;
import com.fengmi.fmmall.service.UserAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserAddrServiceImpl implements UserAddrService {
    @Autowired
    private UserAddrMapper userAddrMapper;

    @Override
    public ResultVo listAddrById(int userId) {
        Example example = new Example(UserAddr.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("status", 1);
        List<UserAddr> userAddrs = userAddrMapper.selectByExample(example);
        return new ResultVo(ResStauts.OK, "success", userAddrs);
    }
}
