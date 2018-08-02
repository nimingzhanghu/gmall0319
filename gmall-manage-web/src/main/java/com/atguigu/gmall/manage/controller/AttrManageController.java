package com.atguigu.gmall.manage.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 针对平台属性的操作
 *
 * Controller的作用就是调用Service方法实现所需的业务
 */
@Controller
public class AttrManageController {

    //数据展示，获得到gmall-manage-service中的服务，要使用 Reference
    @Reference     //不同项目之间的调用必须使用@Reference不是@Autowired
    private ManageService manageService;

    /**
     * 1、获得一级分类菜单
     */
    @ResponseBody
    @RequestMapping("getCatalog1")
    public List<BaseCatalog1> getCatalog1(){
        return manageService.getCatalog1();
    }

    /**
     * 2、根据一级菜单的id获得 二级分类菜单
     */
    @ResponseBody
    @RequestMapping("getCatalog2")
    public List<BaseCatalog2> getCatalog2(String catalog1Id){
        return manageService.getCatalog2(catalog1Id);
    }

    /**
     * 3、根据二级菜单的id获得 三级分类菜单
     */
    @ResponseBody
    @RequestMapping("getCatalog3")
    public List<BaseCatalog3> getCatalog3(String catalog2Id){
        return manageService.getCatalog3(catalog2Id);
    }
    /**
     * 4、根据三级菜单id 获得 平台属性名称
     */
    @ResponseBody
    @RequestMapping("attrInfoList")
    public List<BaseAttrInfo> getAttrInfoList(String catalog3Id){
        return manageService.getAttrList(catalog3Id);
    }

    /**
     * 5、保存平台属性 (三级菜单弹窗,数据修改后,进行的保存操作)
     */
    @RequestMapping(value="saveAttrInfo",method = RequestMethod.POST)
    @ResponseBody
    //可以对象的特性  -->  封装数据  -->  传递所需要的数据
    public String saveAttrInfo(BaseAttrInfo baseAttrInfo){

        //保存数据时,数据必须从页面传递进来
        //用对象来封装所需要的数据,再进行传递
        manageService.saveAttrInfo(baseAttrInfo);

        return "success";
    }

    /**
     * 6、根据 平台属性BaseAttrInfo.id 获取对应的全部 平台属性值信息 BaseAttrValue
     */
    @RequestMapping("getAttrValueList")
    @ResponseBody
    public List<BaseAttrValue> getAttrValueList(String attrId){
        //属性--属性值  是一一对应的
        //实体类 BaseAttrInfo 中有属性 AttrValueList (属性对应的全部属性值信息)
        //-->所以可以先找到对应的 BaseAttrInfo --> 再调用其属性值 AttrValueList 做返回值
        BaseAttrInfo baseAttrInfo = manageService.getAttrInfo(attrId);

        return baseAttrInfo.getAttrValueList();
    }




}
