package com.atguigu.gmall.usermanage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.bean.UserInfo;

import com.atguigu.gmall.service.UserInfoService;
import com.atguigu.gmall.usermanage.mapper.UserAddressMapper;
import com.atguigu.gmall.usermanage.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserAddressMapper userAddressMapper;

    //1、获取用户信息
    public List<UserInfo> getUserInfoList() {

        return userInfoMapper.selectAll();
    }

    //2、通过userId值获取用户地址信息
    public List<UserAddress> getUserAddressList(String userId) {
        //通用mapper参数传递
        Example example = new Example(UserAddress.class);
        example.createCriteria().andEqualTo("userId",userId);
        return userAddressMapper.selectByExample(example);
    }


}
