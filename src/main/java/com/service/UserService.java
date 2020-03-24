package com.service;

import com.alibaba.fastjson.JSONObject;
import com.constants.SysConstant;
import com.dao.UserDao;
import com.entity.Page;
import com.entity.User;
import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * @auth jian j w
 * @date 2020/3/17 18:15
 * @Description
 */
public class UserService {

    //注入UserDao
    private UserDao userDao = new UserDao();

    //用户登录
    public User userlogin(User user){
        return userDao.userlogin(user);
    }

    /*
     * @Description 用户姓名集合
     * @author jian j w
     * @date 2020/3/22
     */
    public List<User> userNameList(Integer deptID){
        return userDao.userNameList(deptID);
    }

    //用户登录修改登录时间
    public void loginTimeUpdate(User user){
        //获取当前时间作为登录时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginTime = simpleDateFormat.format(new Date());
        user.setLoginTime(loginTime);
        userDao.loginTimeUpdate(user);
    }
    //微信回调数据
    public JSONObject getJsonObject(String url) {
        try {
            // 创建一个http Client请求
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            // 获取响应数据(json)
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, Charset.forName("UTF8"));
                return JSONObject.parseObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //用户首次微信登录处理
    public User WeChatLoginByOpenid(String wx_openid  ){
        return userDao.WeChatLoginByOpenid(wx_openid);
    }

    //验证用户名
    public User checkUsername(String username){
        return userDao.checkUsername(username);
    }

    //验证用户邮箱
    public User checkEmail(String email){
        return userDao.checkEmail(email);
    }

    //用户注册
    public Integer userRegister (User user){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creattime = simpleDateFormat.format(new Date());
        user.setCreateTime(creattime);
        user.setRegisterTime(creattime);
        user.setCreateBy("1");
        user.setDelFlag(SysConstant.USER_DEL_CODE);
        return userDao.userRegister(user);
    }

    //用户管理
    public List<User> userList(User user,Page page,String minage ,String maxage){
       return userDao.userList(user,page,minage,maxage);
    }

    //分页总记录数
    public Integer count(User user,String minage,String maxage){
        return  userDao.count(user,minage,maxage);
    }

    //用户头像
    public User userDetail (Integer id){
        return userDao.userDetail(id);
    }

    //更新用户头像
    public void uploadHeadImg(String username,String fileName){
        userDao.uploadHeadImg(username,fileName);
    }

    //用户信息更新
    public Integer updateUserDetail(User user){
        return userDao.updateUserDetail(user);
    }

    //微信或QQ登录头像处理保存到服务器
    public void wxHeadImg(String pic,String imgstr){

        try {
            // 从网络上获取图片--URL对象用来封装路径
            URL url = new URL(pic);
            // 打开路径链接---得到HttpURLConnection对象
            HttpURLConnection httpURLConnection = (HttpURLConnection) url
                    .openConnection();
            // 通过HTTP协议请求网络图片---设置请求方式：get/post
            httpURLConnection.setRequestMethod("GET");
            // 设置连接超时
            httpURLConnection.setConnectTimeout(5000);
            // ----通过输入流获取图片数据
            BufferedInputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            //图片保存到服务器路径
            String imgBase = SysConstant.SYS_USER_IMGBASE;
            FileOutputStream fos = new FileOutputStream(imgBase+imgstr);
            int len = 0;
            byte [] b = new byte[1024];
            while (true){
                len=inputStream.read(b);
                if (len==-1){
                    break;
                }
                fos.write(b,0,len);
            }
            fos.flush();
            fos.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * @Description 检验是否为用户注册邮箱
     * @author jian j w
     * @date 2020/3/21
     */
    public String usernameEmailCheck(User user){
        Integer flag = userDao.usernameEmailCheck(user);
        if (flag==1){
            return "200";
        }
        else {
            return "500";
        }
    }

    /*
     * @Description 验证码发送
     * @author jian j w
     * @date 2020/3/21
     */
    public String sendEmail(String email){
        //第一步：设置发件人邮箱地址，第二步：开启smtp服务
        try {
            //设置发件人
            String from = "2639488092@qq.com";
            //设置收件人
            String to = email;
            //设置邮件发送的服务器，这里为QQ邮件服务器
            String host = "smtp.qq.com";
            //获取系统属性
            Properties properties = System.getProperties();
            //SSL加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);

            //设置系统属性
            properties.setProperty("mail.smtp.host", host);
            properties.put("mail.smtp.auth", "true");

            //获取发送邮件会话、获取第三方登录授权码
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    //第三方登录授权码
                    return new PasswordAuthentication(from, "rnbsxuynzcpcdibc");
                }
            });
            Message message = new MimeMessage(session);
            //防止邮件被当然垃圾邮件处理，披上Outlook的马甲
            message.addHeader("X-Mailer", "Microsoft Outlook Express 6.00.2900.2869");
            //设置发件人
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            //邮件主题
            message.setSubject("用户密码更改验证码");
            BodyPart bodyPart = new MimeBodyPart();
            //消息体（正文）
            Random random = new Random();
            String checkStr = "";
            while (checkStr.length()<6){
                checkStr+=String.valueOf(random.nextInt(9));
            }
            bodyPart.setText("设是本次密码更改操作的验证码:"+checkStr+"该验证码180秒内有效" );
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            message.setContent(multipart);

            //发送邮件
            Transport.send(message);
            return checkStr;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            return "";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "";
        }
}

    /*
     * @Description 用户密码更改
     * @author jian j w
     * @date 2020/3/21
     */
    public Integer passWordForget(User user){
        return userDao.passWordForget(user);
    }

    /*
     * @Description 通过url获得QQAccessToken
     * @author jian j w
     * @date 2020/3/21
     */
    public String getAccessTokenForQQ(String url) {
        String token = null;
        try {
            // 创建一次HttpClient请求
            CloseableHttpClient client = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                if (result.indexOf("access_token") >= 0) {
                    String[] array = result.split("&");
                    for (String str : array) {
                        if (str.indexOf("access_token") >= 0) {
                            token = str.substring(str.indexOf("=") + 1);
                            break;
                        }
                    }
                }
            }
            httpGet.releaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /*
     * @Description 通过创建请求获得QQOpenID
     * @author jian j w
     * @date 2020/3/21
     */
    public String getQQOpenID(String url) throws IOException {
        JSONObject jsonObject = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            int startIndex = result.indexOf("(");
            int endIndex = result.lastIndexOf(")");
            String json = result.substring(startIndex + 1, endIndex);
            jsonObject = JSONObject.parseObject(json);
        }
        httpGet.releaseConnection();
        if (jsonObject != null) {
            return jsonObject.getString("openid");
        } else {
            return null;
        }
    }

    /*
     * @Description 通过QQOpenID发起请求获得QQO用户详情
     * @author jian j w
     * @date 2020/3/21
     */
    public JSONObject getUserInfoForQQ(String url) throws IOException {
        JSONObject jsonObject = null;
        CloseableHttpClient client = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String result = EntityUtils.toString(entity, "UTF-8");
            jsonObject = JSONObject.parseObject(result);
        }
        httpGet.releaseConnection();
        return jsonObject;
    }

    /*
     * @Description 用户首次微信登录处理
     * @author jian j w
     * @date 2020/3/21
     */
    public User QQLoginByOpenid(String qq_openid  ){
        return userDao.QQLoginByOpenid(qq_openid);
    }
}
