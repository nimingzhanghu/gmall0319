package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 二级菜单  --> 通过 catalog1Id 关联一级菜单
 */
public class BaseCatalog2 implements Serializable {
    @Id
    @Column
    /**
     * 1、二级菜单 id
     */
    private String id;
    @Column
    /**
     * 2、二级菜单 名称 name
     */
    private String name;
    @Column
    /**
     * 3、一级菜单 id : 实现关联查询
     */
    private String catalog1Id;




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

    public String getCatalog1Id() {
        return catalog1Id;
    }

    public void setCatalog1Id(String catalog1Id) {
        this.catalog1Id = catalog1Id;
    }
}
