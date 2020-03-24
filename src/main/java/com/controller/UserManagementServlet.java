package com.controller;

import com.entity.Page;
import com.entity.User;
import com.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/3/18 19:28
 * @Description 用户管理
 */
@WebServlet("/html/manage/*")
public class UserManagementServlet extends BaseServlet {

    //注入userService
    private UserService userService = new UserService();

    /**
     * @Description 用户管理
     * @author jian j w
     * @date 2020/3/23
     * @param request
     * @param response
     * @return void
     */

    protected void userList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //用户名
        String username = request.getParameter("username");
        //当前页
        String pagecurrent = request.getParameter("pagecurrent");
        //年龄范围
        String minage = request.getParameter("minage");
        String maxage = request.getParameter("maxage");

        //当前页
        if (pagecurrent==null||pagecurrent==""){
            pagecurrent="1";
        }
        //查询条件
        User user = new User();
        if (username==null||username==""){
            username="";
        }
        user.setUsername(username);

        //总记录数
        Integer pagecode = Integer.valueOf(pagecurrent);
        Integer count = userService.count(user,minage,maxage);
        Page page = new Page();
        page.setPagecurrent(pagecode);
        page.setCount(count);

        //请求转发
        List<User> list = userService.userList(user,page,minage,maxage);
        request.setAttribute("list",list);
        request.setAttribute("username",username);
        request.setAttribute("page",page);
        request.setAttribute("minage",minage);
        request.setAttribute("maxage",maxage);
        request.getRequestDispatcher("/html/main/useradd.jsp").forward(request,response);
    }
}
