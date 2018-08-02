package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.bean.BaseAttrInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 平台属性表
 */
public interface BaseAttrInfoMapper extends Mapper<BaseAttrInfo> {

    /**
     *  day05 平台属性功能动态加载
     */
    //根据三级分类id查询属性表 -- 通用 Mapper只针对简单查询
    //                       -- 凡是涉及到多表关联查询,通用Mapper不再使用
    List<BaseAttrInfo> getBaseAttrInfoListByCatalog3Id(Long catalog3Id);

}
