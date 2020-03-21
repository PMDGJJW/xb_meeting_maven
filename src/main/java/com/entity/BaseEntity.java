package com.entity;

/**
 * @auth jian j w
 * @date 2020/3/17 16:23
 * @Description
 */
public class BaseEntity {
    //创建时间
    private String createTime;
    //创建人
    private String createBy;
    //逻辑删除
    private String delFlag;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
