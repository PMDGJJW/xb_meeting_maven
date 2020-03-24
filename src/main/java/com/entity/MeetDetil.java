package com.entity;

/**
 * @auth jian j w
 * @date 2020/3/22 20:54
 * @Description
 */
public class MeetDetil {
    //必要参会人员
    private String [] makeUsers;
    //必到人数
    private Integer makeSize;
    //缺席人数
    private Integer missSize;
    public String[] getMakeUsers() {
        return makeUsers;
    }

    public void setMakeUsers(String[] makeUsers) {
        this.makeUsers = makeUsers;
    }

    public Integer getMakeSize() {
        return makeSize;
    }

    public void setMakeSize(Integer makeSize) {
        this.makeSize = makeSize;
    }

    public Integer getMissSize() {
        return missSize;
    }

    public void setMissSize(Integer missSize) {
        this.missSize = missSize;
    }
}
