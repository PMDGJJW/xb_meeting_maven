package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.constants.SysConstant;
import com.entity.*;
import com.service.DeptService;
import com.service.MeetService;
import com.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/3/21 20:14
 * @Description
 */
@WebServlet("/html/meet/*")
public class MeetServlet extends BaseServlet {
    private MeetService meetService = new MeetService();
    private DeptService deptService = new DeptService();
    private UserService userService = new UserService();
    /**
     * @Description 会议集合
     * @author jian j w
     * @date 2020/3/21
     * @param request
     * @param response
     * @return void
     */
    protected void meetManager  (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Meet meet = new Meet();
        Page page = new Page();

        String title = request.getParameter("meetTitle");
        title=title==null?"":title;
        meet.setTitle(title);

        String status = request.getParameter("status");
        if (status==null||status==""){
           status="-1";
        }
        //每页显示多少个
        page.setPagesize(3);

        //当前页
        String pagecurrent = request.getParameter("pagecurrent");
        pagecurrent=pagecurrent==null?"1":pagecurrent;
        //总记录数
        Integer count = meetService.countList(meet,status);
        page.setCount(count);
        page.setPagecurrent(Integer.valueOf(pagecurrent));

        //会议集合
        List<Meet> list = meetService.meetListAll(meet,page,status);

        request.setAttribute(SysConstant.MEETLISTALL,list);
        request.setAttribute("page",page);
        request.setAttribute("meet",meet);
        request.setAttribute("status",status);
        request.getRequestDispatcher("/html/main/meeting.jsp").forward(request,response);
    }

    /**
     * @Description 查询会议选择部门
     * @author jian j w
     * @date 2020/3/22
     * @param request
     * @param response
     * @return void
     */
    protected void meetAdd (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List <Dept> deptName = deptService.deptNameList();
        request.setAttribute("deptName",deptName);
        request.getRequestDispatcher("/html/main/meetingadd.jsp").forward(request,response);
    }
    /**
     * @Description 根据部门查询相关的员工
     * @author jian j w
     * @date 2020/3/22
     * @param request
     * @param response
     * @return void
     */
    protected void userFindByDeptID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //部门ID
        String deptID = request.getParameter("deptID");
        //查询相关部门员工
        List<User> usersName = userService.userNameList(Integer.valueOf(deptID));
        String userNameJSONStr = JSON.toJSONString(usersName);
        //写回前端数据
        PrintWriter out = response.getWriter();
        out.write(userNameJSONStr);
    }

    /**
     * @Description 发布会议表单处理
     * @author jian j w
     * @date 2020/3/22
     * @param request
     * @param response
     * @return void
     */
    protected void  meetAddContent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //会议标题
        String title = request.getParameter("title");
        //参会部门
        String dept = request.getParameter("dept");

        String user="";
        //参会人员
        String [] users = request.getParameterValues("users");
        //开始时间
        String stareTime = request.getParameter("stareTime");
        //结束时间
        String endTime = request.getParameter("endTime");
        //会议内容
        String content = request.getParameter("content");
        //部门名称
        String deptName ="";
        //将参会人员转换成以逗号隔开字符串

        user = Arrays.toString(users);
        if (title==null||dept==null||users==null||stareTime==null||endTime==null||content==null){
            response.sendRedirect("/html/main/meetingadd.jsp");
        }
        else if (title==""||dept==""||users.length==0||stareTime==""||endTime==""||content==""){
            response.sendRedirect("/html/main/meetingadd.jsp");
        }

        List<Dept> depts = deptService.deptNameList();

        //部门名称
        for (int i = 0; i <depts.size() ; i++) {
            if (Integer.valueOf(dept)==depts.get(i).getId()){
                deptName=depts.get(i).getName();
            }
        }

        Meet meet = new Meet();
        meet.setTitle(title);
        meet.setDeptID(Integer.valueOf(dept));
        meet.setStartTime(stareTime);
        meet.setEndTime(endTime);
        meet.setContent(content);
        meet.setMakeUser(user);
        meet.setDeptName(deptName);
        meet.setStatus(0);

        //发布成功标识码
        Integer flag = meetService.meetAddContent(meet);
        if (flag==1){
            response.sendRedirect("/html/meet/meetManager");
            return;
        }
        else {
            response.sendRedirect("/html/main/meetingadd.jsp");
            return;
        }
    }

    /**
     * @Description 参加会议
     * @author jian j w
     * @date 2020/3/22
     * @param request
     * @param response
     * @return void
     */
    protected void  meetJoin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //会议id
        String meetID  = request.getParameter("id");
        //会议数据
        Meet Detil = meetService.meetDetil(Integer.valueOf(meetID));

        //获取会议数据中的参会必要人员
        String makeUser = Detil.getMakeUser();
        makeUser=makeUser.substring(1,makeUser.length()-1);
        String [] makeUsers = makeUser.split(",");

        //获得当前用户信息
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(SysConstant.SESSION_LOGIN_CHECK);
        Integer userID = user.getId();

        //是否需要参加会议标识 1是，0否
        Integer needjoin = 0 ;

//        if (makeUsers.toString().contains(String.valueOf(userID))){
//            //需要参加会议
//            needjoin = 1;
//        }

        for (int i = 0; i <makeUsers.length ; i++) {
            if (makeUsers[i].equals(String.valueOf(userID))){
                needjoin = 1;
            }
        }

        //获取会议状态
        Integer meetStatus = Detil.getStatus();

        request.setAttribute("Detil",Detil);
        request.setAttribute("needjoin",needjoin);
        request.setAttribute("meetStatus",meetStatus);

        request.getRequestDispatcher("/html/main/meetJoin.jsp").forward(request,response);
    }

    /**
     * @Description 参会人员处理
     * @author jian j w
     * @date 2020/3/23
     * @param request
     * @param response
     * @return void
     */
    protected void  meetDetil(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //会议id
        String meetID  = request.getParameter("id");
        //用户id
        String userID = request.getParameter("userid");

        //会议数据
        Meet Detil = meetService.meetDetil(Integer.valueOf(meetID));

        //获取会议数据中的参会必要人员
        String makeUser = Detil.getMakeUser();
        makeUser=makeUser.substring(1,makeUser.length()-1);
        String [] makeUsers = makeUser.split(",");

        //该会议加入人数
        Integer joinCount = 0;
        joinCount = meetService.meetJoinCount(Integer.valueOf(meetID));

        List<String> join_maker = new ArrayList<>();

        //是否参加过会议标识 1是 0否
        Integer joinOnce = 0;

        //校验当前用户是否已经参会
        if (joinCount<=makeUsers.length){
               Integer isJoin = meetService.meetJoinCheck(Integer.valueOf(userID),Integer.valueOf(meetID));
               if (isJoin==1){
                   joinOnce = 1;
               }else {
                   joinOnce = 0;
                   meetService.meetJoinAdd(Integer.valueOf(userID),Integer.valueOf(meetID));
               }
        }
        else {
            response.sendRedirect("/html/main/meeting.jsp");
        }
        MeetDetil meetDetil = new MeetDetil();
        meetDetil.setMakeUsers(makeUsers);
        //应到人数
        meetDetil.setMakeSize(makeUsers.length);
        //实到人数
        Integer needSize = joinCount;
        //缺席人数
        meetDetil.setMissSize(makeUsers.length-needSize);

        request.setAttribute("Detil",Detil);
        request.setAttribute("meetDetil",meetDetil);
        request.setAttribute("needSize",needSize);
        request.setAttribute("joinOnce",joinOnce);
        request.getRequestDispatcher("/html/main/meetDetil.jsp").forward(request,response);
    }
}
