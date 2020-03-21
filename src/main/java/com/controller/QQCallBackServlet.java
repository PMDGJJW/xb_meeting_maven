package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.constants.SysConstant;
import com.entity.User;
import com.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * @auth jian j w
 * @date 2020/3/21 14:45
 * @Description
 */
@WebServlet("/qq_login")
public class QQCallBackServlet extends HttpServlet {
    //注入UserService
    private UserService userService = new UserService();
        /*
     * @description 处理QQ回调
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取认证成功后的code
        String code = request.getParameter("code");

        Properties prop = new Properties();
        prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
        String appId = prop.getProperty("qq.AppID");
        String appKey = prop.getProperty("qq.AppKey");
        String redirectUri = prop.getProperty("qq.redirect_uri");

        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code" + "&client_id=" + appId +
                "&client_secret=" + appKey +
                "&code=" + code +
                "&redirect_uri=" + redirectUri;

        // 发送HttpClient请求获取access_token(临时票据)
        String access_token = userService.getAccessTokenForQQ(url);

        url = "https://graph.qq.com/oauth2.0/me?access_token=" + access_token;
        // 通过access_token获取openid(qq唯一标识符)
        String openid = userService.getQQOpenID(url);

        url = "https://graph.qq.com/user/get_user_info?access_token=" + access_token +
                "&oauth_consumer_key=" + appId +
                "&openid=" + openid;
        // 通过access_token和openid获取qq的用户信息（昵称，性别，头像...）
        JSONObject jsonObject = userService.getUserInfoForQQ(url);
        String gender =(jsonObject.getString("gender_type"));
        String realName=(jsonObject.getString("nickname"));
        String pic=(jsonObject.getString("figureurl_qq_2"));
        User user = userService.QQLoginByOpenid(openid);
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
            userRegister.setQqOpenid(openid);
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
