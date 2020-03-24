package com.dao;

import com.alibaba.fastjson.JSONObject;
import com.entity.Page;
import com.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/3/17 18:04
 * @Description
 */
public class UserDao extends BaseDao {

    //用户登录
    public User userlogin(User user){
        String sql="select * from user where username=?and password=?";

        try {
            return template.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),user.getUsername(),user.getPassword());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    //用户登录修改登录时间
    public void loginTimeUpdate(User user){
        String sql="update user set login_time=? where username=?";

        try {
            template.update(sql,user.getLoginTime(),user.getUsername());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    //用户是否首次微信登录
    public User WeChatLoginByOpenid(String wx_openid){
        try {
            String sql = " select * from user where wx_openid=?";
            return template.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),wx_openid);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    //用户名验证
    public User checkUsername(String username){
        String  sql = "select * from user where username=?";

        try {
            return template.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),username);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * @Description 查询用户名称与ID
     * @author jian j w
     * @date 2020/3/22
     */
    public List<User> userNameList(Integer deptID){
        if (deptID==-1){
            String sql="select id,username from user ";
            return template.query(sql,new BeanPropertyRowMapper<>(User.class));
        }else {
            String sql="select id,username from user where dept_id=?";
            return template.query(sql,new BeanPropertyRowMapper<>(User.class),deptID);
        }
    }

    //邮箱验证
    public User checkEmail(String email){

        String  sql = "select * from user where email=?";
        try {
            return template.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),email);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    //用户注册
    public Integer userRegister(User user){
        String sql = "insert into user " +
                "( id,dept_id,username,password,email,qq_openid,wx_openid,real_name,age,phone,gender,description,register_time,login_time,pic,look,is_secret,create_time,create_by,del_flag) " +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            return template.update(sql,
                     user.getId(),
                     user.getDeptId(),
                     user.getUsername(),
                     user.getPassword(),
                     user.getEmail(),
                     user.getQqOpenid(),
                     user.getWxOpenid(),
                     user.getRealName(),
                     user.getAge(),
                     user.getPhone(),
                     user.getGender(),
                     user.getDesc(),
                     user.getRegisterTime(),
                     user.getLoginTime(),
                     user.getPic(),
                     user.getLook(),
                     user.getIsSecret(),
                     user.getCreateTime(),
                     user.getCreateBy(),
                     user.getDelFlag()
             );
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //用户管理根据查询情况
    public List<User> userList(User user, Page page,String minage,String maxage){

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

        if (minage==null||minage==""&&maxage==null||maxage==""){
            String sql =
                    demo+
                    "limit ?,?";
            return template.query(sql,new BeanPropertyRowMapper<User>(User.class),
                    "%"+user.getUsername()+"%",(page.getPagecurrent()-1)*page.getPagesize(),page.getPagesize());
        }
       else if (maxage==null||maxage==""){
            String sql = demo+
                    "and u.age>= ?" +
                    "limit ?,?";
            return template.query(sql,new BeanPropertyRowMapper<User>(User.class),
                    "%"+user.getUsername()+"%",minage,(page.getPagecurrent()-1)*page.getPagesize(),page.getPagesize());
        }
        else if (minage==null||minage==""){
            String sql = demo+
                    "and u.age <= ?" +
                    "limit ?,?";
            return template.query(sql,new BeanPropertyRowMapper<User>(User.class),
                    "%"+user.getUsername()+"%",maxage,(page.getPagecurrent()-1)*page.getPagesize(),page.getPagesize());
        }
        else {
            String sql = demo+
                    "and u.age >= ? and  u.age<= ?" +
                    "limit ?,?";
            return template.query(sql,new BeanPropertyRowMapper<User>(User.class),
                    "%"+user.getUsername()+"%",minage,maxage,(page.getPagecurrent()-1)*page.getPagesize(),page.getPagesize());
        }
    }

    //分页总记录数根据查询情况
    public Integer count(User user,String minage,String maxage){
        String demo = "select count(*) from user where username like ?";
        if (minage==null||minage==""&&maxage==null||maxage==""){
            String sql = demo;
            return template.queryForObject(sql,Integer.class,"%"+user.getUsername()+"%");
        }
        else if (maxage==null||maxage==""){
            String sql = demo+" and age >= ?";
            return template.queryForObject(sql,Integer.class,"%"+user.getUsername()+"%",minage);
        }
        else if (minage==null||minage==""){
            String sql = demo+" and age >= ?";
            return template.queryForObject(sql,Integer.class,"%"+user.getUsername()+"%",maxage);
        }
        else {
            String sql = demo+" and age >= ? and age <= ?";
            return template.queryForObject(sql,Integer.class,"%"+user.getUsername()+"%",minage,maxage);
        }
    }

    //获取用户图像路径
    public User userDetail(Integer id){
        String sql="select * from  user where id=?";

        try {
            return template.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    //更新用户头像
    public void uploadHeadImg(String username,String fileName){
        String sql="update user set pic=? where username=?";

        try {
            template.update(sql,fileName,username);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    //更新用户信息
    public Integer updateUserDetail(User user){
        String sql="update user set real_name=?,age=?,gender=?,dept_id=?,is_secret=? where id=?";

        try {
            return template.update(sql,user.getRealName(),user.getAge(),user.getGender(),user.getDeptId(),user.getIsSecret(),user.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*
     * @Description 检验是否为用户注册邮箱
     * @author jian j w
     * @date 2020/3/21
     */
     public Integer usernameEmailCheck(User user){
         String sql="select count(id) from user where username=? and email=? ";

         try {
             return template.queryForObject(sql,Integer.class,user.getUsername(),user.getEmail());
         } catch (DataAccessException e) {
             e.printStackTrace();
             return 0;
         }
     }

    /*
     * @Description 密码更改
     * @author jian j w
     * @date 2020/3/21
     */
     public Integer passWordForget(User user){
         String sql="update user set password=?where username=?";

         try {
             return template.update(sql,user.getPassword(),user.getUsername());
         } catch (DataAccessException e) {
             e.printStackTrace();
             return 0;
         }
     }

     /*
      * @Description 查询用户是否是第一次QQ登录
      * @author jian j w
      * @date 2020/3/21
      */
     public User QQLoginByOpenid(String qq_openid){
         try {
             String sql = " select * from user where qq_openid=?";
             return template.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),qq_openid);
         } catch (DataAccessException e) {
             e.printStackTrace();
             return null;
         }
     }
}
