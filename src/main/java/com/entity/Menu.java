package com.entity;

/**
 * @auth jian j w
 * @date 2020/3/19 21:54
 * @Description
 */
public class Menu {
    //目录ID
    private Integer id;
    //目录归属ID
    private Integer PId;
    //目录名称
    private String name;
    //url
    private String url;
    //目录类型
    private Integer type;
    //排序
    private Integer orderBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPId() {
        return PId;
    }

    public void setPId(Integer PId) {
        this.PId = PId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}
