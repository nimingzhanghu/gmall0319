package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.BaseAttrInfo;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SpuImage;
import com.atguigu.gmall.bean.SpuSaleAttr;
import com.atguigu.gmall.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SkuManageController {

    @Reference
    private ManageService manageService;

    @RequestMapping("spuImageList")
    @ResponseBody
    /**
     * 1、图片信息展示
     *
     *  根据spuId获取spuImage中的所有图片列表
     */
    public List<SpuImage> spuImageList(String spuId){
        return manageService.getSpuImageList(spuId);
    }



    @RequestMapping(value ="attrInfoList",method = RequestMethod.GET)
    @ResponseBody
    /**
     *  2、动态获取平台属性 BaseAttrInfo
     */
    public List<BaseAttrInfo> attrInfoList(String catalog3Id){
        return manageService.getAttrList(catalog3Id);
    }

    /**
     *  3、动态获取销售属性 SpuSaleAttr
     */
    @RequestMapping(value = "spuSaleAttrList",method = RequestMethod.GET)
    @ResponseBody
    public List<SpuSaleAttr> spuSaleAttrList(String spuId){
        return manageService.getSpuSaleAttrList(spuId);
    }

    /**
     * 4、大保存(day05)
     */
    @RequestMapping(value = "saveSku",method = RequestMethod.POST)
    @ResponseBody
    public String saveSkuInfo(SkuInfo skuInfo){
        manageService.saveSkuInfo(skuInfo);
        return "success";
    }


}
