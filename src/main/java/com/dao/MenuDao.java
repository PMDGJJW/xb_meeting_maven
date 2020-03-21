package com.dao;

import com.entity.Menu;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/3/19 21:57
 * @Description
 */
public class MenuDao extends BaseDao {
    //查询目录表数据
    public List<Menu> menuListALL(){
        String sql = "SELECT id,p_id,name,url,type,order_by FROM sys_menu";
       return template.query(sql,new BeanPropertyRowMapper<>(Menu.class));
    }

}
