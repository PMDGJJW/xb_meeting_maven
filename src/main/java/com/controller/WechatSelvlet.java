package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.constants.SysConstant;
import com.entity.User;
import com.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * @auth jian j w
 * @date 2020/3/20 11:46
 * @Description
 */
@WebServlet("/weChat/*")
public class WechatSelvlet extends BaseServlet {

    //注入UserService
    private UserService userService = new UserService();

    protected void wxLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        Properties properties = new Properties();
        InputStream is = WechatSelvlet.class.getClassLoader().getResourceAsStream("config.properties");
        properties.load(is);

        String appid = properties.getProperty("wx.AppID");
        //微信授权成功后的回调地址
        String redirect_uri = properties.getProperty("wx.redirect_uri");

        //Step1：获取Authorization Code
        String url = "https://open.weixin.qq.com/connect/qrconnect?response_type=code"+
                "&appid=" + appid +
                "&redirect_uri=" + URLEncoder.encode(redirect_uri) +
                "&scope=snsapi_login";

        // 重定向到微信登录指定的地址进行微信登录授权,授权成功后返回code
        response.sendRedirect(url);
    }

    protected void wxLoginCallBack(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Properties properties = new Properties();
        InputStream is = WechatSelvlet.class.getClassLoader().getResourceAsStream("config.properties");
        properties.load(is);

        String appId = properties.getProperty("wx.AppID");
        String appSecret = properties.getProperty("wx.AppSecret");

        //获取微信授权后返回的code
        String code = request.getParameter("code");
        //固定格式
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId +
                "&secret=" + appSecret +
                "&code=" + code +
                "&grant_type=authorization_code";

        // 通过code获取access_token、openid等数据
        JSONObject info = userService.getJsonObject(url);
        System.out.println("info: " + info);

        url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + info.getString("access_token") +
                "&openid=" + info.getString("openid");
        //通过access_token和openid获取微信的用户信息（昵称，性别，头像...）
        JSONObject userInfo = userService.getJsonObject(url);
        String openid =(userInfo.getString("openid"));
        String gender =(userInfo.getString("sex"));
        String realName=(userInfo.getString("nickname"));
        String pic=(userInfo.getString("headimgurl"));

        User user =  userService.WeChatLoginByOpenid(openid);
        Integer flag=0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginTime = simpleDateFormat.format(new Date());
        User userRegister = new User();
       if (user==null){
           //获得文件名，这个新的文件名需要保存到数据库
           String fileName = UUID.randomUUID().toString() + ".jpg";
           userRegister.setGender(gender);
           userRegister.setRealName(realName);
           userRegister.setPic(fileName);
           userRegister.setWxOpenid(openid);
           userRegister.setLoginTime(loginTime);
           //将头像保存到服务器中
           userService.wxHeadImg(pic,fileName);

           // 随机用户名(11位随机字符串)
           userRegister.setUsername(UUID.randomUUID().toString().substring(36 - 15));
           flag = userService.userRegister(userRegister);

       }
       else {
           user.setLoginTime(loginTime);
           HttpSession session = request.getSession();
           session.setAttribute(SysConstant.SESSION_LOGIN_CHECK,user);
           response.sendRedirect("/html/comment/home.jsp");
           userService.loginTimeUpdate(user);
       }
       if (flag==1){
           HttpSession session = request.getSession();
           session.setAttribute(SysConstant.SESSION_LOGIN_CHECK,userRegister);
           response.sendRedirect("/html/comment/home.jsp");
           userService.loginTimeUpdate(userRegister);
       }
    }
}
