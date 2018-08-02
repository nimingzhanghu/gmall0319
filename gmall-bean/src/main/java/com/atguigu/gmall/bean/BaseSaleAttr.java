package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 销售属性表
 */
public class BaseSaleAttr implements Serializable {
    @Id
    @Column
    /**
     * 1、销售属性 id
     */
    String id ;

    @Column
    /**
     * 2、销售属性名称
     */
    String name;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
