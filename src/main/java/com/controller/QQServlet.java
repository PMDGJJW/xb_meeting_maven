package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.entity.User;
import com.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * @auth jian j w
 * @date 2020/3/21 14:10
 * @Description QQ登录
 */
@WebServlet("/qq/*")
public class QQServlet extends BaseServlet{

    /*
     * @description 点击qq登录，跳转到qq授权页面,获取code
     */
    protected void qqLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Properties prop = new Properties();
        prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
        //qq应用的AppID
        String appid = prop.getProperty("qq.AppID");
        //QQ授权成功后的回调地址
        String redirectUri = prop.getProperty("qq.redirect_uri");

        //固定格式
        //Step1：获取Authorization Code
        String url = "https://graph.qq.com/oauth2.0/authorize?response_type=code" +
                "&client_id=" + appid +
                "&redirect_uri=" + URLEncoder.encode(redirectUri) +
                "&state=1";

        // 重定向到QQ登录指定的地址进行QQ授权,授权成功后返回code
        response.sendRedirect(url);
    }

}
