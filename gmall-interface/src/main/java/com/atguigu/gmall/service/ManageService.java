package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.*;

import java.util.List;

public interface ManageService {

    /**
     * 1、获取一级菜单的全部信息
     */
    public List<BaseCatalog1> getCatalog1();

    /**
     * 2、根据一级菜单的id 获取 对应二级菜单的信息
     */
    public List<BaseCatalog2> getCatalog2(String catalog1Id);

    /**
     * 3、根据二级菜单的id 获取 对应三级菜单的信息
     */
    public List<BaseCatalog3> getCatalog3(String catalog2Id);

    /**
     * 4、根据三级菜单id 获取 对应平台属性名称
     */
    public List<BaseAttrInfo> getAttrList(String catalog3Id);

    /**
     * 5、保存 (BaseAttrInfo平台属性、BaseAttrValue平台属性值) --> 保存数据到数据库 saveAttrInfo()
     */
    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    //6、查询平台属性信息 --> 根据 id 查询 对应的 BaseAttrInfo
    BaseAttrInfo getAttrInfo(String attrId);


    /*======================================== spu 商品信息管理 ==============================================*/


    /**
     * 1、根据三级分类进行查询所有spuInfo信息
     */
    List<SpuInfo> getSpuInfoList(String catalog3Id);


    /**
     * 2、查询基本销售属性表
     */
    List<BaseSaleAttr> getBaseSaleAttrList();


    /**
     * 3、大保存(day04) --> 保存spuInfo信息
     */
    void saveSpuInfo(SpuInfo spuInfo);

    //====================================== sku 库存信息管理 =========================================

    /**
     * 1、图片加载功能
     *
     *  根据spuId获取spuImage中的所有图片列表
     */
    List<SpuImage> getSpuImageList(String spuId);



    /**
     * 3、动态获取销售属性 SpuSaleAttr 根据商品id 查询销售属性
     */
    List<SpuSaleAttr> getSpuSaleAttrList(String spuId);

    /**
     * 4、大保存(day05)
     */
    void saveSkuInfo(SkuInfo skuInfo);




    //===================================== 商品详情页 =========================================

    /**
     * 1、根据商品id查找商品信息，图片信息并在页面显示
     */
    SkuInfo getSkuInfo(String skuId);

    /**
     * 2、根据商品的 skuId spuId 查询商品的销售属性
     *
     *    商品销售属性查询 --> 目的确定 sku 的销售属性 --> sku对应spu的销售属性(已经被选中的销售属性值)
     */
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(SkuInfo skuInfo);

    /**
     * 3、根据 spuId 查询销售属性值列表
     */
    List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu(String spuId);
}
