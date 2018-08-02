package com.atguigu.gmall.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 一级菜单
 */
public class BaseCatalog1 implements Serializable {
    @Id
    @Column
    /**
     * 1、一级菜单 id
     */
    private String id;
    @Column
    /**
     * 2、一级菜单 name
     */
    private String name;





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
