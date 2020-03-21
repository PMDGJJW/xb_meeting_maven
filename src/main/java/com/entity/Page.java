package com.entity;

/**
 * @auth jian j w
 * @date 2020/3/18 20:26
 * @Description
 */
public class Page {
    //每页显示多少
    private Integer pagesize=5;
    //总记录数
    private Integer count;
    //总页数
    private Integer pagecount;
    //当前页
    private Integer pagecurrent;

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPagecount() {
        return (this.count%this.pagesize==0?this.count/this.pagesize:(this.count/this.pagesize)+1);
    }

    public void setPagecount(Integer pagecount) {
        this.pagecount = pagecount;
    }

    public Integer getPagecurrent() {
        return pagecurrent;
    }

    public void setPagecurrent(Integer pagecurrent) {
        this.pagecurrent = pagecurrent;
    }
}
