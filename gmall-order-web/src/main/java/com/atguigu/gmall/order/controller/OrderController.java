package com.atguigu.gmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.service.UserInfoService;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class OrderController {

    @Reference
    private UserInfoService userInfoService;

    @ResponseBody
    @RequestMapping("trade")
    public List<UserAddress> trade(HttpServletRequest request){
        //获取参数userId
        String userId = request.getParameter("userId");
        //调用service方法，实现需求
        return userInfoService.getUserAddressList(userId);
    }
}
