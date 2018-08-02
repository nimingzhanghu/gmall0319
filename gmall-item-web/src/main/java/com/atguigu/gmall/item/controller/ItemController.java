package com.atguigu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.SkuInfo;
import com.atguigu.gmall.bean.SkuSaleAttrValue;
import com.atguigu.gmall.bean.SpuSaleAttr;
import com.atguigu.gmall.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


/**
 * 商品详情页编写
 */
@Controller
public class ItemController {

    @Reference
    private ManageService manageService;


    @RequestMapping("{skuId}.html")
    public String item(@PathVariable(value = "skuId")String skuId, HttpServletRequest request){

        /*----- 1、根据商品id查找商品信息，图片信息并在页面显示 ----*/
        //根据商品 id--> skuId 进行数据查询 skuInfo skuImage
        SkuInfo skuInfo = manageService.getSkuInfo(skuId);

        //将查询到的商品信息设置在request域中,可以在页面中获取并显示
        request.setAttribute("skuInfo",skuInfo);


        /*-------------- 2、商品销售属性查询 ------------*/
        //商品销售属性查询 --> 目的确定 sku 的销售属性 --> sku对应spu的销售属性(已经被选中的销售属性值)
        List<SpuSaleAttr> spuSaleAttrList = manageService.selectSpuSaleAttrListCheckBySku(skuInfo);
        //将查询到的信息设置在request域中，便于在页面显示
        request.setAttribute("spuSaleAttrList",spuSaleAttrList);


        /*-------------- 3、销售属性组合拼接 ------------*/
        //商品销售属性值查询 --> 销售属性值集合
        List<SkuSaleAttrValue> skuSaleAttrValueListBySpu = manageService.getSkuSaleAttrValueListBySpu(skuInfo.getSpuId());

        /*----Json字符串拼接----*/
        //创建一个空的 json
        //销售属性值1 + 销售属性值2 + ... ==> 确定一个 唯一的 skuId 库存商品实例
        /*
        # 91|94 = 27   91|93 = 28
        # map.put("91|94",skuId);
        # {"91|94":27, "91|93":28}
        */
        String jsonKey="";

        HashMap<String,String> map = new HashMap<>();

        //for循环进行字符串拼接(不是 foreach 遍历)
        for(int i = 0 ; i < skuSaleAttrValueListBySpu.size() ; i++){

            SkuSaleAttrValue skuSaleAttrValue = skuSaleAttrValueListBySpu.get(i);
            //当jsonKey不为空的时候
            if(jsonKey.length()!=0){
                jsonKey+="|";
            }

            jsonKey = jsonKey+skuSaleAttrValue.getSaleAttrValueId();

            //字符串拼接终止条件设置
            //循环完成的条件:
            //     1、(i+1)==skuSaleAttrValueListBySpu.size() --> skuSaleAttrValueListBySpu.size() : 循环的次数
            //                                                   当 i 达到循环次数的上限时，循环结束
            //     2、!skuSaleAttrValue.getSkuId().equals(skuSaleAttrValueListBySpu.get(i+1).getSkuId())
            //                                               --> 当skuId发生改变时，说明商品实例已经发生改变，不是同一件商品，循环结束
            if((i+1)==skuSaleAttrValueListBySpu.size() || !skuSaleAttrValue.getSkuId().equals(skuSaleAttrValueListBySpu.get(i+1).getSkuId())){

                //将拼接的字符串保存在 map 中
                map.put(jsonKey,skuSaleAttrValue.getSkuId());

                //将字符串清空
                jsonKey="";
            }
        }

        //将map集合转换成json对象
        String valuesSkuJson = JSON.toJSONString(map);

        //将字符串信息保存到后台，前台取得数据进行匹配 一组相关的spuId 对应的销售属性值以及skuId
        request.setAttribute("valuesSkuJson",valuesSkuJson);

        return "item";
    }

}
