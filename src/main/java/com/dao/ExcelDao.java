package com.dao;

import com.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/3/20 21:03
 * @Description
 */
public class ExcelDao extends BaseDao {
    public List<User> dowLoadXml(String username ){
        String sql = "SELECT " +
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
        return template.query(sql,new BeanPropertyRowMapper<>(User.class),"%"+username+"%");
    }
}
