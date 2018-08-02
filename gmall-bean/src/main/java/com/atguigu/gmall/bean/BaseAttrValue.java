package com.atguigu.gmall.bean;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 平台属性值表   (注意平台属性和平台属性值是不同的)
 *
 *  通过平台属性 BaseAttrInfo 可以获取 属性值 BaseAttrValue
 */
public class BaseAttrValue implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增长
    /**
     * 1、平台属性值 id
     */
    private String id;
    @Column
    /**
     * 2、平台属性值名称
     */
    private String valueName;
    @Column
    /**
     * 3、平台属性 id   -->  关联 BaseAttrInfo.id
     */
    private String attrId;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }
}
