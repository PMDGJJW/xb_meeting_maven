package com.controller;

import com.alibaba.fastjson.JSON;
import com.constants.SysConstant;
import com.entity.User;
import com.service.UserService;
import com.utils.ImgCodeUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @auth jian j w
 * @date 2020/3/17 19:46
 * @Description
 */
@WebServlet("/img/*")
public class GetPicCodeServlet extends BaseServlet {
    //注入UserService
    private UserService userService = new UserService();

    //登录验证码
    protected void getPic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码生成工具类
        ImgCodeUtil imgCodeUtil = new ImgCodeUtil();

        //获取图片
        BufferedImage bufferedImage = imgCodeUtil.getImage();

        //获取验证码文字
        String imagestr = imgCodeUtil.getText();

        //创建session
        HttpSession session = request.getSession();

        //将验证码文字存入session
        session.setAttribute(SysConstant.SESSION_PIC_CODE,imagestr);

        // 禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(bufferedImage,"jpeg",sos);
        sos.flush();
        sos.close();
    }

    //获得用户头像
    protected void userImage (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String imageBase = SysConstant.SYS_USER_IMGBASE;
        String picStr = request.getParameter("pic");
        String pic = imageBase+picStr;
        File file = new File(pic);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        OutputStream os = response.getOutputStream();
        int len = 0;
        byte [] b = new byte[1024];
        while (true){
            len= bis.read(b);
            if (len==-1){
                break;
            }
            os.write(b,0,len);
        }
        os.flush();
        os.close();
        bis.close();
    }

    //上传头像到服务器
    protected void uploadHeadImg (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //为解析类提供配置信息 创建文件上传工厂类
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //创建解析类的实例 传入工厂类获取文件上传对象
        ServletFileUpload sfu = new ServletFileUpload(factory);
        //设置文件最大解析大小(单位：字节)
        sfu.setFileSizeMax(1024 * 1024 * 2);
        //每个表单域中数据会封装到一个对应的FileItem对象上
        PrintWriter out = response.getWriter();
        try {
            List<FileItem> items = sfu.parseRequest(request);
            String fileName = "";

            //区分表单域
            for (int i = 0; i < items.size(); i++) {
                FileItem item = items.get(i);
                //isFormField为true，表示这不是文件上传表单域
                if (!item.isFormField()) {
                    String name = item.getName();
                    // 获取文件名后缀
                    String suffix = name.substring(name.lastIndexOf("."));

                    //获得文件名，这个新的文件名需要保存到数据库
                    fileName = UUID.randomUUID().toString() + suffix;
                    String path = SysConstant.SYS_USER_IMGBASE + fileName;
                    System.out.println(path);
                    File file = new File(path);
                    if (!file.exists()) {
                        //将文件写出到指定磁盘（即保存图片的服务器）
                        item.write(file);
                    }
                }
            }
            HttpSession session = request.getSession();
            User loginUser = (User)session.getAttribute(SysConstant.SESSION_LOGIN_CHECK);
            // 修改数据库中的pic地址
            userService.uploadHeadImg( loginUser.getUsername(),fileName);

            //修改session中的loginUser的pic
            loginUser.setPic(fileName);
            session.setAttribute(SysConstant.SESSION_LOGIN_CHECK, loginUser);

            //把文件名返回给前端
            Map dataMap = new HashMap();
            dataMap.put("status", 200);
            dataMap.put("picUrl", fileName);
            out.write(JSON.toJSONString(dataMap));

        } catch (Exception e) {
            Map dataMap = new HashMap();
            dataMap.put("status", 500);
            dataMap.put("message", "图片上传失败");
            out.write(JSON.toJSONString(dataMap));
            e.printStackTrace();
        }
    }


}
