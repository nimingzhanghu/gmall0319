package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.bean.SpuSaleAttr;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpuSaleAttrMapper extends Mapper<SpuSaleAttr> {
    /**
     * 1、动态获取销售属性 SpuSaleAttr 根据商品id 查询销售属性
     */
    List<SpuSaleAttr> selectSpuSaleAttrList(long spuId);

    /**
     * 2、根据商品的 skuId spuId 查询商品的销售属性
     */
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(long skuId,long spuId);


}
