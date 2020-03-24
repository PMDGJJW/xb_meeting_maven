package com.filter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.constants.SysConstant;
import com.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * @auth jian j w
 * @date 2020/3/17 19:03
 * @Description 过滤器
 */
@WebFilter("/*")
public class BaseFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //解决中文乱码
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        String uri = request.getRequestURI();

        if (uri.endsWith("/index.jsp") ){
            Cookie [] cookie = request.getCookies();
            if (cookie!=null){
            for (int i = 0; i <cookie.length ; i++) {
                if (cookie[i].getName().equals(SysConstant.COOKIE_LOGIN_USER)){
                    String cookieUser = URLDecoder.decode(cookie[i].getValue(),"utf-8") ;
                    User loginUser = JSON.parseObject(cookieUser, new TypeReference<User>() {
                    });
                    HttpSession session1 = request.getSession();
                    session1.setAttribute(SysConstant.SESSION_LOGIN_CHECK,loginUser);
//                    request.setAttribute("loginUser",loginUser);
                    request.getRequestDispatcher("/html/comment/home.jsp").forward(request,response);
//                    response.sendRedirect("/html/comment/home.jsp");
                }
            }
            //放行
            filterChain.doFilter(request,response);
            return;
            }
        }
        //不需要拦截的，直接放行
        else if (uri.endsWith("/register.jsp") ||
                uri.endsWith("register") ||
                uri.endsWith("login") ||
                uri.endsWith("checkUsername") ||
                uri.endsWith("checkEmail") ||
                uri.endsWith("getPic")||
                uri.endsWith("wxLogin")||
                uri.endsWith("wxLoginCallBack")||
                uri.endsWith("/forget.jsp")||
                uri.endsWith("usernameEmailCheck")||
                uri.endsWith("sendEmail")||
                uri.endsWith("checkCode")||
                uri.endsWith("passwordForget")||
                uri.endsWith("qqLogin")||
                uri.endsWith("qqLoginCallBack")||
                uri.endsWith("/qq_login"))
                {
            //放行
            filterChain.doFilter(request, response);
            return;
        }

        Object obj = session.getAttribute(SysConstant.SESSION_LOGIN_CHECK);
        if (obj == null) {
            //session中没有登录信息（非法登录）
            response.sendRedirect("/index.jsp");
            return;
        }else {
            request.setAttribute("loginUser",(User)obj);
            //放行
            filterChain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
