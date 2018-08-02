package com.atguigu.gmall.manage.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ManageController {
    /**
     * 1、跳转页面
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    /**
     * 2、左侧菜单栏中平台属性 attrListPage (跳转页面) 控制器 返回一个页面--> 在中部区域显示对应页面
     */
    @RequestMapping("attrListPage")
    public String getAttrListPage(){
        //返回值是一个页面--attrListPage.html-->编写页面
        return "attrListPage";
    }

    /**
     * 3、左侧菜单栏中商品信息管理 spuListPage (跳转页面) 控制器 返回一个页面--> 在中部区域显示对应页面
     */
    @RequestMapping("spuListPage")
    public String getSpuListPage(){
        return "spuListPage";
    }


}
