package com.atguigu.gmall.bean;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 平台属性表  --> 关联三级菜单 --> 可以通过三级菜单的id值 查询到
 */
public class BaseAttrInfo implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增长
    /**
     * 1、平台属性 id值
     */
    private String id;
    @Column
    /**
     * 2、平台属性名称 name
     */
    private String attrName;
    @Column
    /**
     * 3、三级菜单 ia
     */
    private String catalog3Id;

    @Transient    //数据库非有的字段
    /**
     * 4、用于封装数据
     */
    private List<BaseAttrValue> attrValueList;




    public List<BaseAttrValue> getAttrValueList() {
        return attrValueList;
    }

    public void setAttrValueList(List<BaseAttrValue> attrValueList) {
        this.attrValueList = attrValueList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = catalog3Id;
    }
}
