package com.dao;

import com.entity.Dept;
import com.entity.Page;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/3/19 10:32
 * @Description
 */
public class DeptDao extends BaseDao{
    //部门详情
    public List<Dept> deptList(Dept dept,Page page){
        try {
            String sql="SELECT " +
                    " d.id id, " +
                    " d.NAME NAME , " +
                    " COUNT(u.dept_id) count " +
                    " FROM " +
                    " dept d " +
                    " LEFT JOIN `user` u ON u.dept_id = d.id  " +
                    " WHERE d.`name` like ? "+
                    " GROUP BY d.id " +
                    " limit ?,? ";
            return template.query(sql,new BeanPropertyRowMapper<>(Dept.class),"%"+dept.getName()+"%",(page.getPagecurrent()-1)*page.getPagesize(),page.getPagesize());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    //部门分页
    public Integer deptCount(Dept dept){
        try {
            String sql="select count(*) from dept where  name like ? ";
            return template.queryForObject(sql,Integer.class,"%"+dept.getName()+"%");
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //部门添加
    public Integer deptAdd(Dept dept){

        try {
            String sql="insert into dept (id,name) values (null ,?)";
            return template.update(sql,dept.getName());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }
    //部门名称检查
    public Dept deptCheck(String deptname){

        try {
            String sql = "select * from  dept where name=?";
            return template.queryForObject(sql,new BeanPropertyRowMapper<>(Dept.class),deptname);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
    //部门删除
    public Integer deptDelete(Integer id){
        String sql="delete from dept where id=?";
        try {
            return template.update(sql,id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //部门编辑
    public List<Dept> deptWrite(Integer id){
        String sql="select * from dept where id = ?";
        return template.query(sql,new BeanPropertyRowMapper<Dept>(Dept.class),id);
    }

    //部门编辑提交
    public Integer deptRewrite(Dept dept){

        try {
            String sql="update dept set name=? where id=?";
            return template.update(sql,dept.getName(),dept.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //部门集合
    public List<Dept> deptListAll(){
        String sql ="select * from dept";
       return template.query(sql,new BeanPropertyRowMapper<>(Dept.class));
    }
}
