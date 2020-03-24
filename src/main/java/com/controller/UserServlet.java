package com.controller;

import com.alibaba.fastjson.JSON;
import com.constants.SysConstant;
import com.entity.Dept;
import com.entity.User;
import com.service.DeptService;
import com.service.UserService;
import com.utils.MdUtil;
import org.apache.commons.beanutils.BeanUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/3/17 18:20
 * @Description 用户
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //注入UserService
    private UserService userService = new UserService();

    //注入DeptServlet
    private DeptService deptService = new DeptService();

    /**
     * @Description 登录
     * @author jian j w
     * @date 2020/3/17
     * @param request
     * @param response
     * @return void
     */
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取表单域提交的值
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String piccode = request.getParameter("picCode");
        String remember = request.getParameter("remember");
        password=MdUtil.md5(password);
        remember=remember==null?"0":"1";
        //创建User对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        //通过session获取验证码
        HttpSession session = request.getSession();
        Object piccodestr = session.getAttribute(SysConstant.SESSION_PIC_CODE);

        //忽略大小写
        String piccodestr_ToLower = (piccodestr.toString()).toLowerCase();
        piccode = piccode.toLowerCase();

        try {
            //验证验证码是否正确
            if (!(piccodestr_ToLower.equals(piccode))) {
                response.sendRedirect("/index.jsp");
                return;
            }

            User result = userService.userlogin(user);
            //创建session
            HttpSession session_login = request.getSession();
            session_login.setAttribute(SysConstant.SESSION_LOGIN_CHECK,result);

            if ("1"==remember){
                String cookieStr = JSON.toJSONString(result);
                cookieStr = URLEncoder.encode(cookieStr,"utf-8");
                Cookie cookie = new Cookie(SysConstant.COOKIE_LOGIN_USER,cookieStr);
                cookie.setMaxAge(7*24*60*60);
                //任何请求都能获取cookie
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            if (!(result == null)) {
                    response.sendRedirect("/html/comment/home.jsp");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/index.jsp");
            return;
        }

    }


    /**
     * @Description 用户名验证
     * @author jian j w
     * @date 2020/3/17
     * @param request
     * @param response
     * @return void
     */
    protected void checkUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username = request.getParameter("username");
        //是否存在重复
        User result = userService.checkUsername(username);
        //状态码写回 200没有重复，500重复
        PrintWriter out = response.getWriter();
        if (result==null){
            out.write("200");
        }
        else {
            out.write("500");
        }
    }

    /**
     * @Description 用户邮箱验证
     * @author jian j w
     * @date 2020/3/17
     * @param request
     * @param response
     * @return void
     */
    protected void checkEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String email = request.getParameter("email");
        //是否存在重复
        User result = userService.checkEmail(email);
        //状态码写回 200没有重复，500重复
        PrintWriter out = response.getWriter();
        if (result==null){
            out.write("200");
        }
        else {
            out.write("500");
        }
    }

    /**
     * @Description 用户注册
     * @author jian j w
     * @date 2020/3/17
     * @param request
     * @param response
     * @return void
     */
    protected void userRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();

        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //密码MD5加密
        String password = request.getParameter("password");
        password=MdUtil.md5(password);
        user.setPassword(password);

        //获取是否注册成功标识码
        Integer registeflag = userService.userRegister(user);
        if (registeflag==1){
            response.sendRedirect("/index.jsp");
        }else {
            response.sendRedirect("/register");
        }

    }

    /**
     * @Description 用户详情
     * @author jian j w
     * @date 2020/3/17
     * @param request
     * @param response
     * @return void
     */
    protected void userDetail (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //当前用户id
        String id = request.getParameter("id");
        User user = userService.userDetail(Integer.valueOf(id));
        //用户数据
        List<Dept> depts = deptService.deptListAll();
        request.setAttribute("user",user);
        request.setAttribute("depts",depts);
        request.getRequestDispatcher("/html/main/userdetail.jsp").forward(request,response);
    }

    //用户信息修改

    protected void userDetailUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String id = request.getParameter("id");
        String realName = request.getParameter("realName");
        String deptId = request.getParameter("dept");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");
        String isSecret = request.getParameter("isSecret");

        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setRealName(realName);
        user.setDeptId(Integer.valueOf(deptId));
        user.setAge(Integer.valueOf(age));
        user.setGender(gender);
        if (isSecret==null||isSecret==""){
            isSecret="1";
        }
        user.setIsSecret(isSecret);

        Integer flag = userService.updateUserDetail(user);

        if (flag==1){
            response.sendRedirect("/html/comment/home.jsp");
        }
        else {
            response.sendRedirect("/html/main/userdetail.jsp");
        }
    }
    /**
     * @Description 用户登出
     * @author jian j w
     * @date 2020/3/21
     * @param request
     * @param response
     * @return void
     */
    protected void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Cookie [] cookie = request.getCookies();
        //设置cookie的有效期
        for (int i = 0; i <cookie.length ; i++) {
            if (SysConstant.COOKIE_LOGIN_USER.equals(cookie[i].getName())) {
                cookie[i].setPath("/");
                cookie[i].setMaxAge(0);
                response.addCookie(cookie[i]);
            }
        }
        //使session失效
        HttpSession session = request.getSession();
        session.removeAttribute(SysConstant.SESSION_LOGIN_CHECK);
        response.sendRedirect("/index.jsp");
    }

    /**
     * @Description 检验是否为用户注册邮箱
     * @author jian j w
     * @date 2020/3/21
     * @param request
     * @param response
     * @return void
     */
    protected void usernameEmailCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        User user = new User();
        if (username==null||username==""){
            return;
        }
        if (email==null||email==""){
            return;
        }
        user.setUsername(username);
        user.setEmail(email);
        //检测该邮箱是否为用户的注册邮箱 ，状态码：200不是，500是
        String flag = userService.usernameEmailCheck(user);
        PrintWriter out = response.getWriter();
        if (flag=="200"){
            out.write("200");
        }else {
            out.write("500");
        }
    }
    /**
     * @Description  验证码创建
     * @author jian j w
     * @date 2020/3/21
     * @param request
     * @param response
     * @return void
     */
    protected void sendEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String email = request.getParameter("email");
        //获取验证码
        String checktoken = userService.sendEmail(email);

        PrintWriter out = response.getWriter();
        if (checktoken.length()==6){
            //设置验证码session 有效期180s
            HttpSession session = request.getSession();
            session.setAttribute(SysConstant.SEND_EMAIL_TOKEN,checktoken);
            session.setMaxInactiveInterval(60*3);
//            request.getRequestDispatcher("/forget.jsp").forward(request,response);
            out.write("200");
        }
        else {
            out.write("500");
        }
    }
    /**
     * @Description 用户输入验证码后校验是否正确
     * @author jian j w
     * @date 2020/3/21
     * @param request
     * @param response
     * @return void
     */
    protected void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //获取验证码session
        HttpSession session = request.getSession();
        Object obg =  session.getAttribute(SysConstant.SEND_EMAIL_TOKEN);
        String code = obg.toString();
        PrintWriter writer = response.getWriter();
        //是否验证码失效了
        if (code==null){
            writer.write("500");
            return;
        }
        //获取用户输入的验证码
        String checkCode = request.getParameter("code");
        //前端返回状态码 200正确,500错误
        if (!code.equals(checkCode)){
            writer.write("500");
        }else {
            writer.write("200");
        }
    }
    /**
     * @Description  修改密码
     * @author jian j w
     * @date 2020/3/21
     * @param request
     * @param response
     * @return void
     */
    protected void passwordForget(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username = request.getParameter("username");
        String rewritePassword = request.getParameter("password");
        User user = new User();
        user.setUsername(username);
        rewritePassword = MdUtil.md5(rewritePassword);
        user.setPassword(rewritePassword);
        Integer flag = userService.passWordForget(user);
        if (flag==1){
            response.sendRedirect("/index.jsp");
        }else if (flag==0){
            response.sendRedirect("/forget.jsp");
        }
    }
}

