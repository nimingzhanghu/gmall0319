package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.BaseSaleAttr;
import com.atguigu.gmall.bean.SpuInfo;
import com.atguigu.gmall.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * spu商品信息管理
 */
@Controller
public class SpuManageController {

    @Reference
    private ManageService manageService;
    /**
     * 1、根据三级分类进行查询所有spuInfo信息
     */
    @RequestMapping("spuList")
    @ResponseBody
    public List<SpuInfo> spuList(String catalog3Id){
        //使用 alibaba 的注解 @Reference-->调用 service 方法完成所需业务
        return manageService.getSpuInfoList(catalog3Id);
    }

    /**
     * 2、查询基本属性表
     */
    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<BaseSaleAttr> getBaseSaleAttrList(){
        return manageService.getBaseSaleAttrList();
    }

    /**
     * 3、大保存(day04) --> 保存spuInfo信息
     */
    @RequestMapping(value ="saveSpuInfo",method = RequestMethod.POST)
    @ResponseBody
    public String saveSpuInfo(SpuInfo spuInfo){
        manageService.saveSpuInfo(spuInfo);
        return "success";
    }

}
