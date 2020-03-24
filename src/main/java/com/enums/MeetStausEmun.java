package com.enums;

/**
 * @auth jian j w
 * @date 2020/3/23 20:08
 * @Description
 */
public enum  MeetStausEmun  {
    PUBLISH("未开始",0),
    STARE("进行中",1),
    END("结束",2),
    ;

    private String name;
   private Integer value;

    MeetStausEmun() {
    }

    MeetStausEmun(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
