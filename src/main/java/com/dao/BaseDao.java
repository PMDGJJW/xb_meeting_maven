package com.dao;

import com.utils.DBUtil;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @auth jian j w
 * @date 2020/3/17 18:05
 * @Description
 */
public class BaseDao {
    public JdbcTemplate template = new JdbcTemplate(DBUtil.getDataSource());
}
