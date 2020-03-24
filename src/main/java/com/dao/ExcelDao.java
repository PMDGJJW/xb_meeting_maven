package com.dao;

import com.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/3/20 21:03
 * @Description 导出为Excel
 */
public class ExcelDao extends BaseDao {
    //拼接sql
    String demo = "SELECT " +
            " u.username username, " +
            " u.real_name realName, " +
            " u.gender gender, " +
            " u.age age, " +
            " u.create_time createTime, " +
            " su.username createBy  " +
            " FROM  " +
            " user u  " +
            " LEFT JOIN user su ON su.id = u.create_by " +
            "where u.username like ? ";
    //根据年龄和姓名查询结果
    public List<User> dowLoadXml(String username,String minage,String maxage){
        if (minage==null||minage==""&&maxage==null||maxage==""){
            String sql = demo;
            return template.query(sql,new BeanPropertyRowMapper<>(User.class),"%"+username+"%");
        }
        else if (maxage==null||maxage==""){
            String sql = demo+" and u.age >= ? ";
            return template.query(sql,new BeanPropertyRowMapper<>(User.class),"%"+username+"%",minage);
        }
        else if (minage==null||minage==""){
            String sql = demo+" and u.age <= ? ";
            return template.query(sql,new BeanPropertyRowMapper<>(User.class),"%"+username+"%",maxage);
        }
        else {
            String sql = demo+" and  u.age >= ? and u.age <= ?";
            return template.query(sql,new BeanPropertyRowMapper<>(User.class),"%"+username+"%",minage,maxage);
        }
    }
}
