package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.config.RedisUtil;
import com.atguigu.gmall.manage.constant.ManageConst;
import com.atguigu.gmall.manage.mapper.*;
import com.atguigu.gmall.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * ManageService 接口接口的实现类
 *
 * 要调用Mapper来实现需求
 */
@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private BaseAttrInfoMapper baseAttrInfoMapper;

    @Autowired
    private BaseAttrValueMapper baseAttrValueMapper;

    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;

    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private  SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private  SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private RedisUtil redisUtil;



    /**
     * 1、获取一级菜单的信息
     */
    public List<BaseCatalog1> getCatalog1() {

        return baseCatalog1Mapper.selectAll();
    }

    /**
     * 2、通过一级菜单id 获取二级菜单信息
     */
    public List<BaseCatalog2> getCatalog2(String catalog1Id) {

        //通用Mapper的参数传递
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();

        baseCatalog2.setCatalog1Id(catalog1Id);

        return baseCatalog2Mapper.select(baseCatalog2);

    }

    /**
     * 3、通过二级菜单id 获取 三级菜单信息
     */
    public List<BaseCatalog3> getCatalog3(String catalog2Id) {
        //通用Mapper的参数传递
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();

        baseCatalog3.setCatalog2Id(catalog2Id);

        return baseCatalog3Mapper.select(baseCatalog3);
    }


    /**
     * 4、通过三级菜单id 获取 平台属性名称(在平台属性表)
     */
    public List<BaseAttrInfo> getAttrList(String catalog3Id) {

        return baseAttrInfoMapper.getBaseAttrInfoListByCatalog3Id(Long.parseLong(catalog3Id));
    }



    /**
     * 5、大保存 (BaseAttrInfo平台属性、BaseAttrValue平台属性值) --> 保存数据到数据库 saveAttrInfo()
     */
    //此处将 1、添加-->保存  2、编辑-->保存  编写在一起 ==> 在存储数据时进行分析--数据库中是否存在

    //      1、添加-->保存 : 创建新的数据、数据库中没有库存
    //      2、编辑-->保存 : 数据库中以前就存在该数据,现在进行的是修改保存的操作
    /*保存过程: 1、先保存平台属性 --> 2、再保存平台属性值     */
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //添加数据-->对数据进行分析
        /*-------------------------1、保存平台属性---------------------------*/
        if(baseAttrInfo.getId()!=null && baseAttrInfo.getId().length()>0){

            //说明数据在数据库中已存在-->只能进行更新操作(修改数据-->保存)
            baseAttrInfoMapper.updateByPrimaryKey(baseAttrInfo);

        }else{
            if(baseAttrInfo.getId()==null || baseAttrInfo.getId().length()==0){
                //目的是为了不影响主键的自增设置,将id设置为 null
                baseAttrInfo.setId(null);
            }
            //说明数据在数据库中的不存在-->进行的是添加操作(新建数据-->保存)
            baseAttrInfoMapper.insertSelective(baseAttrInfo);

        }


        /*-------------------------2、保存平台属性值---------------------------*/
        //先删除、再新增
        //1、删除数据库中原有的平台属性值(根据平台属性attrId baseAttrInfo.getId() )
        BaseAttrValue baseAttrValue = new BaseAttrValue();

        baseAttrValue.setAttrId(baseAttrInfo.getId());
        //删除库中原有的数据
        baseAttrValueMapper.delete(baseAttrValue);

        //2、插入数据
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        //对集合中的数据进行判断
        if(attrValueList!=null && attrValueList.size()>0){
            for (BaseAttrValue attrValue : attrValueList) {
                if(attrValue.getId()==null || attrValue.getId().length()==0){
                    attrValue.setId(null);
                }

                attrValue.setAttrId(baseAttrInfo.getId());

                baseAttrValueMapper.insertSelective(attrValue);

            }
        }



    }

    /**
     * 6、查询平台属性信息 --> 根据 id 查询 对应的 BaseAttrInfo
     *
     *  注意这里的 attrId --> BaseAttrInfo.id  (就是平台属性的id)
     */
    public BaseAttrInfo getAttrInfo(String attrId) {
        //1、根据 attrId 获取 BaseAttrInfo 对象
        BaseAttrInfo baseAttrInfo = baseAttrInfoMapper.selectByPrimaryKey(attrId);

        //2、查询对应的 属性值信息 BaseAttrValue
        //2.1、创建 BaseAttrValue 对象 --> 构造通用 Mapper 的参数
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfo.getId());
        //2.2、通过 通用Mapper 查询 平台属性值 BaseAttrValue
        List<BaseAttrValue> attrValueList = baseAttrValueMapper.select(baseAttrValue);

        //3、将查询到的结构赋值给 baseAttrInfo --> 返回
        baseAttrInfo.setAttrValueList(attrValueList);
        return baseAttrInfo;
    }


    /*=================================== spu 商品管理 =======================================*/


    /**
     * 1、根据三级分类进行查询所有spuInfo信息
     */
    public List<SpuInfo> getSpuInfoList(String catalog3Id) {

        SpuInfo spuInfo = new SpuInfo();

        spuInfo.setCatalog3Id(catalog3Id);

        return spuInfoMapper.select(spuInfo);
    }

    /**
     * 2、查询基本销售属性表
     */
    public List<BaseSaleAttr> getBaseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }


    /**
     * 1、=================================== 大保存(day04) =======================================!!!
     */
    public void saveSpuInfo(SpuInfo spuInfo) {

        /*------------------------------- 保存 spu 商品表信息 -----------------------------------*/

        // 判断当前的spuInfo.id
        if (spuInfo.getId()==null || spuInfo.getId().length()==0){
            spuInfo.setId(null);
            spuInfoMapper.insertSelective(spuInfo);
        }else{
            spuInfoMapper.updateByPrimaryKey(spuInfo);
        }

        /*---------------------------------- 图片信息保存 --------------------------------------*/

        // 思想 --> 先删除，后保存
        SpuImage spuImage = new SpuImage();
        // 根据商品id进行删除数据
        spuImage.setSpuId(spuInfo.getId());
        spuImageMapper.delete(spuImage);

        // 添加数据
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        for (SpuImage image : spuImageList) {
            // image.id="";
            if (image.getId()!=null && image.getId().length()==0){
                // 保证表的主键自动增长
                image.setId(null);
            }
            // 设置一下商品id=supId
            image.setSpuId(spuInfo.getId());
            spuImageMapper.insertSelective(image);
        }

        /*----------------------------- 销售属性、销售属性值保存 --------------------------------*/

        // 思想 --> 先删除、再保存

        // 销售属性,删除
        SpuSaleAttr spuSaleAttr = new SpuSaleAttr();
        spuSaleAttr.setSpuId(spuInfo.getId());
        spuSaleAttrMapper.delete(spuSaleAttr);

        // 销售属性值删除
        SpuSaleAttrValue spuSaleAttrValue = new SpuSaleAttrValue();
        spuSaleAttrValue.setSpuId(spuInfo.getId());
        spuSaleAttrValueMapper.delete(spuSaleAttrValue);

        // 保存信息，保存属性，再保存属性值
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        for (SpuSaleAttr saleAttr : spuSaleAttrList) {
            if (saleAttr.getId()!=null && saleAttr.getId().length()==0){
                saleAttr.setId(null);
            }
            saleAttr.setSpuId(spuInfo.getId());
            spuSaleAttrMapper.insertSelective(saleAttr);
            // 取得属性，对应的属性值集合
            for (SpuSaleAttrValue saleAttrValue : saleAttr.getSpuSaleAttrValueList()) {
                if (saleAttrValue.getId()!=null && saleAttrValue.getId().length()==0){
                    saleAttrValue.setId(null);
                }
                saleAttrValue.setSpuId(spuInfo.getId());
                spuSaleAttrValueMapper.insertSelective(saleAttrValue);
            }
        }
    }


    //=================================  sku 库存信息管理(day05) ===============================================

    /**
     * 1、图片加载功能
     *
     *  根据spuId获取spuImage中的所有图片列表
     */
    public List<SpuImage> getSpuImageList(String spuId) {

        SpuImage spuImage = new SpuImage();

        spuImage.setSpuId(spuId);

        return spuImageMapper.select(spuImage);
    }

    /**
     * 3、动态获取销售属性 SpuSaleAttr 根据商品id 查询销售属性
     */
    public List<SpuSaleAttr> getSpuSaleAttrList(String spuId) {
        return spuSaleAttrMapper.selectSpuSaleAttrList(Long.parseLong(spuId));
    }


    /**
     * 4、大保存(day05)
     */
    public void saveSkuInfo(SkuInfo skuInfo) {
        /*------------- 1、skuInfo保存 -------------------------*/
        //判断skuInfo.id信息
        if(skuInfo.getId()==null || skuInfo.getId().length()==0){
            //将id设置为空-->满足主键自增
            skuInfo.setId(null);
            //保存数据
            skuInfoMapper.insertSelective(skuInfo);
        }else{
            skuInfoMapper.updateByPrimaryKeySelective(skuInfo);
        }

        /*------------- 2、skuImage 图片保存 --------------------*/
        //思路: 删除 --> 保存    (skuInfo中有属性skuImageList用于封装图片数据)

        //删除  --> (根据skuInfo.id 删除数据库中原有的图片信息)
        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuInfo.getId());
        skuImageMapper.delete(skuImage);

        //保存  --> (将封装在skuInfo中的图片集合遍历保存到对应的数据库中)
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        //遍历图片集合-->数据插入
        for (SkuImage image : skuImageList) {
            if(image.getId()!=null && image.getId().length()==0){
                image.setId(null);
            }
            image.setSkuId(skuInfo.getId());
            skuImageMapper.insertSelective(image);
        }

        /*------------------- 3、销售属性、平台属性保存 --------------------------*/
        //基本思路 :  删除 旧 -->  保存 新
        SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
        skuSaleAttrValue.setSkuId(skuInfo.getId());
        skuSaleAttrValueMapper.delete(skuSaleAttrValue);

        SkuAttrValue skuAttrValue = new SkuAttrValue();
        skuAttrValue.setSkuId(skuInfo.getId());
        skuAttrValueMapper.delete(skuAttrValue);

        //平台属性值 skuAttrValue 插入
        //获取封装在 skuInfo 中的SkuAttrValueList
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        for (SkuAttrValue attrValue : skuAttrValueList) {
            if(attrValue.getId()!=null && attrValue.getId().length()==0){
                attrValue.setId(null);
            }
            //将attrValue和skuInfo构造联系
            attrValue.setSkuId(skuInfo.getId());
            //插入数据
            skuAttrValueMapper.insertSelective(attrValue);
        }

        //销售属性值 skuSaleAttrValue 插入
        //获取封装在 skuInfo 中的SkuSaleAttrValueList
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
        for (SkuSaleAttrValue saleAttrValue : skuSaleAttrValueList) {
            if(saleAttrValue.getId()!=null && saleAttrValue.getId().length()==0){
                saleAttrValue.setId(null);
            }
            //将 skuSaleAttrValue 和 skuInfo 构造联系
            saleAttrValue.setSkuId(skuInfo.getId());
            //插入数据
            skuSaleAttrValueMapper.insertSelective(saleAttrValue);
        }
    }


    /*================================ sku 商品详情页(day06) ====================================*/

    /**
     * 1、根据商品id查找商品信息，图片信息并在页面显示 skuInfo skuImage
     */
    /*public SkuInfo getSkuInfo(String skuId) {
        //根据 skuId 查询 skuInfo
        SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);

        //根据 skuId 查询 skuImage
        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuId);
        List<SkuImage> skuImageList = skuImageMapper.select(skuImage);

        //将skuImageList数据封装到skuInfo对象中
        skuInfo.setSkuImageList(skuImageList);

        return skuInfo;
    }

    */
    /**
     * 2、根据商品的 skuId spuId 查询商品的销售属性
     *
     *    商品销售属性查询 --> 目的确定 sku 的销售属性 --> sku对应spu的销售属性(已经被选中的销售属性值)
     */
    public List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(SkuInfo skuInfo) {
        return spuSaleAttrMapper.selectSpuSaleAttrListCheckBySku(Long.parseLong(skuInfo.getId()),Long.parseLong(skuInfo.getSpuId()));
    }

    /**
     * 3、根据 spuId 查询销售属性值列表
     */
    public List<SkuSaleAttrValue> getSkuSaleAttrValueListBySpu(String spuId) {
        return skuSaleAttrValueMapper.selectSkuSaleAttrValueListBySpu(spuId);
    }



    /*=========================== 整合redis重写getSkuInfo方法(day07) =============================*/

    public SkuInfo getSkuInfo(String skuId){
        //通过工具类获取jedis
        Jedis jedis = redisUtil.getJedis();
        SkuInfo skuInfo =null;

        //创建skuInfoKey
        String skuInfoKey = ManageConst.SKUKEY_PREFIX+skuId+ManageConst.SKUKEY_SUFFIX;

        //检测redis中是否有对应的缓存存在，及skuInfoKey在redis中是否存在
        if(jedis.exists(skuInfoKey)){
            //redis中存在对应的缓存，将数据取出，转换成对象返回
            //根据 K,V 中的 K 取出对应的 value 注意取出的是json类型的字符串
            String skuInfoJson = jedis.get(skuInfoKey);

            //将json类型的字符串转换为对象的形式
            //判断
            if(skuInfoJson!=null && !"".equals(skuInfoJson)){
                //将 json 字符串转换为 Object 对象
                skuInfo = JSON.parseObject(skuInfoJson,SkuInfo.class);
                return skuInfo;
            }

        }else{
            //redis中没有对应的缓存时，只能从数据库中查询所需的数据
            //查询到数据后: 1、返回数据 2、将数据保存在缓存中

            //从数据库中获取数据(方法提取到下面)
            skuInfo = getSkuInfoDB(skuId);

            //将数据保存到 redis 中 --> 将 skuInfo 转换为 Json 字符串
            String skuInfoStr = JSON.toJSONString(skuInfo);
            //保存数据，并设置过期时间
            jedis.setex(skuInfoKey,ManageConst.SKUKEY_TIMEOUT,skuInfoStr);
        }

        //返回数据
        return null;
    }

    //从数据库中获取数据
    //根据 skuId 获取mysql中的skuInfo等信息
    //根据商品id查找商品信息，图片信息并在页面显示 skuInfo skuImage
    private SkuInfo getSkuInfoDB(String skuId){
        //1、获取 skuInfo
        SkuInfo skuInfo = skuInfoMapper.selectByPrimaryKey(skuId);

        //2、获取 skuImage 图片信息
        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuId);
        List<SkuImage> skuImageList = skuImageMapper.select(skuImage);

        //3、将查询到的skuImageList存放到skuInfo中返回
        skuInfo.setSkuImageList(skuImageList);

        return skuInfo;
    }

}
