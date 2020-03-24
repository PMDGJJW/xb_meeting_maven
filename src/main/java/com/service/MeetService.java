package com.service;

import com.dao.MeetDao;
import com.entity.Meet;
import com.entity.Page;
import com.enums.MeetStausEmun;
import com.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/3/21 20:11
 * @Description
 */
public class MeetService  {
    //注入
    private MeetDao meetDao = new MeetDao();

    /*
     * @Description 会议详情集合
     * @author jian j w
     * @date 2020/3/21
     */
    public List<Meet>meetListAll(Meet meet, Page page,String status){
        return meetDao.meetListAll(meet,page,status);
    }

    /*
     * @Description 分页总记录数
     * @author jian j w
     * @date 2020/3/21
     */
    public Integer countList(Meet meet,String status){
        return meetDao.countList(meet,status);
    }

    /*
     * @Description 发布会议数据添加
     * @author jian j w
     * @date 2020/3/22
     */
    public Integer meetAddContent(Meet meet){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String publishTime = simpleDateFormat.format(new Date());
        meet.setPublicTime(publishTime);
        return meetDao.meetAddContent(meet);
    }

    /*
     * @Description 会议细节
     * @author jian j w
     * @date 2020/3/22
     */
    public Meet meetDetil(Integer id){
        return meetDao.meetDetil(id);
    }

    /*
     * @Description 会议参会人数
     * @author jian j w
     * @date 2020/3/22
     */
    public Integer meetJoinCount (Integer id){
        return meetDao.meetJoinCount(id);
    }

    /*
     * @Description 检验是否参加过会议
     * @author jian j w
     * @date 2020/3/23
     * @param null
     * @return
     */
    public Integer meetJoinCheck(Integer userID,Integer cID){
        return meetDao.meetJoinCheck(userID,cID);
    }

    /*
     * @Description 插入参会标识
     * @author jian j w
     * @date 2020/3/23
     * @param null
     * @return
     */
    public void meetJoinAdd(Integer userID,Integer cID){
        meetDao.meetJoinAdd(userID,cID);
    }

    public void meetStatusChange(){
        List<Meet> list = meetDao.meetStatusDetil();
        DateUtil dateUtil = new DateUtil();

        for (Meet meet :list){
            long stareTime = dateUtil.time(meet.getStartTime());
            long endTime = dateUtil.time(meet.getEndTime());
            long nowTime = new Date().getTime();
            if (nowTime>stareTime){
                if (endTime>nowTime){
                    meetDao.meetStatusChange(MeetStausEmun.STARE.getValue(),meet.getId());
                }
                else {
                    meetDao.meetStatusChange(MeetStausEmun.END.getValue(),meet.getId());
                }
            }

        }
    }

}
