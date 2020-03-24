package com.dao;

import com.entity.Meet;
import com.entity.Page;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;
import java.util.Random;

/**
 * @auth jian j w
 * @date 2020/3/21 20:01
 * @Description
 */
public class MeetDao extends BaseDao{

    /*
     * @Description 会议详情集合
     * @author jian j w
     * @date 2020/3/21
     */
     public List<Meet> meetListAll(Meet meet, Page page ,String status){
         //不选择会议状态
         if ("-1".equals(status)){
             String sql = "select id,dept_name,dept_id,title,content,publish_date,start_time,end_time,status,make_user " +
                     " from meeting " +
                     "where title like ? " +
                     "limit ?,?" ;
             return template.query(sql,new BeanPropertyRowMapper<>(Meet.class),"%"+meet.getTitle()+"%",(page.getPagecurrent()-1)*page.getPagesize(),page.getPagesize());
         }
         //选择了会议状态
         else {
             String sql = "select id,dept_name,dept_id,title,content,publish_date,start_time,end_time,status,make_user " +
                     " from meeting " +
                     "where title like ? " +
                     "and status = (?)" +
                     "limit ?,?";
             return template.query(sql, new BeanPropertyRowMapper<>(Meet.class), "%" + meet.getTitle() + "%", status, (page.getPagecurrent() - 1) * page.getPagesize(), page.getPagesize());
         }
     }

     /*
      * @Description 分页总记录数
      * @author jian j w
      * @date 2020/3/21
      */
      public Integer countList(Meet meet,String status){
          if ("-1".equals(status)){
              String sql = "select count(id) from meeting where title like ? ";
              return template.queryForObject(sql,Integer.class,"%"+meet.getTitle()+"%");
          }
          else {
              String sql = "select count(id) from meeting where title like ? and `status` in(?)";
              return template.queryForObject(sql,Integer.class,"%"+meet.getTitle()+"%",status);
          }
      }

      /*
       * @Description 发布会议数据添加
       * @author jian j w
       * @date 2020/3/22
       */
      public Integer meetAddContent(Meet meet){
          String sql=" insert into meeting (id,content,dept_id,start_time,status,end_time,title,make_user,publish_date,dept_name) " +
                  " values (null ,?,?,?,?,?,?,?,?,?)";

          try {
              return template.update(sql,meet.getContent(),meet.getDeptID(),meet.getStartTime(),meet.getStatus(),meet.getEndTime(),meet.getTitle(),meet.getMakeUser(),meet.getPublicTime(),meet.getDeptName());
          } catch (DataAccessException e) {
              e.printStackTrace();
              return 0;
          }
      }

      /*
       * @Description 会议细节
       * @author jian j w
       * @date 2020/3/22
       */
       public Meet meetDetil(Integer id){
           String sql = " select id,title,make_user,dept_name,content,start_time,status,end_time from meeting where id=?";
           return template.queryForObject(sql,new BeanPropertyRowMapper<>(Meet.class),id);
       }
    /*
     * @Description 会议参会人数
     * @author jian j w
     * @date 2020/3/22
     */
       public Integer meetJoinCount(Integer id){
           String sql = " select count(1) from meeting_join where c_id = ?";

           try {
               return template.queryForObject(sql,Integer.class,id);
           } catch (DataAccessException e) {
               e.printStackTrace();
               return 0;
           }
       }

       /*
        * @Description 查询该用户是否参加过该会议
        * @author jian j w
        * @date 2020/3/23
        * @param null
        * @return
        */
       public Integer meetJoinCheck(Integer userID,Integer cID){

           String sql = " select count(1) from meeting_join where make_id = ? and c_id = ?";

           try {
               return template.queryForObject(sql,Integer.class,userID,cID);
           } catch (DataAccessException e) {
               e.printStackTrace();
               return 0;
           }
       }

       /*
        * @Description 插入参会标识
        * @author jian j w
        * @date 2020/3/23
        * @param null
        * @return
        */
        public void meetJoinAdd(Integer userID,Integer cID){
            String sql = " insert into meeting_join (make_id, c_id) values (?,?)";
            try {
                template.update(sql,userID,cID);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }

        /*
         * @Description 会议细节
         * @author jian j w
         * @date 2020/3/22
         */
        public List<Meet> meetStatusDetil(){
            String sql = " select id,start_time,status,end_time from meeting ";

            try {
                return template.query(sql,new BeanPropertyRowMapper<>(Meet.class));
            } catch (DataAccessException e) {
                e.printStackTrace();
                return  null;
            }
        }
        /*
         * @Description 会议状态改变
         * @author jian j w
         * @date 2020/3/23
         */
        public void meetStatusChange(Integer status,Integer meetID){
            String sql = " update meeting set status = ? where id = ?";

            try {
                template.update(sql,status,meetID);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }


}
