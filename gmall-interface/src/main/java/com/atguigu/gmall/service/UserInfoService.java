package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.bean.UserInfo;

import java.util.List;

public interface UserInfoService {
    //1、查询用户信息(测试)
    List<UserInfo> getUserInfoList();

    //2、根据id值查询用户地址信息
    public List<UserAddress> getUserAddressList(String userId);
}
