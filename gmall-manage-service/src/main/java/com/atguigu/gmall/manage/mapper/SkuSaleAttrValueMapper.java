package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.bean.SkuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkuSaleAttrValueMapper extends Mapper<SkuSaleAttrValue> {

    /**
     * 1、根据 spuId 查询销售属性值列表
     */
    List<SkuSaleAttrValue> selectSkuSaleAttrValueListBySpu(String spuId);


}
