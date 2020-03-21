package com.entity;

/**
 * @auth jian j w
 * @date 2020/3/19 10:31
 * @Description
 */
public class Dept extends BaseEntity {
    private Integer id;
    private String name;
    private Integer count;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
