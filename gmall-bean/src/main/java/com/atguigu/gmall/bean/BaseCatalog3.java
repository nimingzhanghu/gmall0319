package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 三级菜单  --> 三级菜单通过 catalog2Id 关联 二级菜单
 */
public class BaseCatalog3 implements Serializable {

    @Id
    @Column
    /**
     * 1、三级菜单 id
     */
    private String id;
    @Column
    /**
     * 2、三级菜单名称 name
     */
    private String name;
    @Column
    /**
     * 3、二级菜单 id 值
     */
    private String catalog2Id;




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

    public String getCatalog2Id() {
        return catalog2Id;
    }

    public void setCatalog2Id(String catalog2Id) {
        this.catalog2Id = catalog2Id;
    }
}
