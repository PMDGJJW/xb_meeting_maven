package com.controller;

import com.entity.User;
import com.service.ExcelService;
import com.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/3/20 21:02
 * @Description
 */
@WebServlet("/xml/*")
public class ExcelServlet extends BaseServlet {
    //注入excelService
    private ExcelService excelService = new ExcelService();
    //注入
    private UserService userService = new UserService();
    /**
     * @Description
     * @author jian j w
     * @date 2020/3/21
     * @param request
     * @param response
     * @return void
     */

    protected void dowLoadXml(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String username=request.getParameter("username");
        username=username==null?"":username;

        Workbook wb = excelService.dowLoadXml(username);
        String fileName = "用户列表Excel.xls";
        //必要头
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
    }
    /**
     * @Description 下载模板
     * @author jian j w
     * @date 2020/3/21
     * @param request
     * @param response
     * @return void
     */

    protected void xmlTemplate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Workbook wb = excelService.xmlTemplate();
        String fileName = "用户信息提交上传模板Excel.xls";
        //必要头
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
        response.setContentType("application/ynd.ms-excel;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        wb.write(out);
        out.flush();
        out.close();
    }
    /**
     * @Description 读取文件数据
     * @author jian j w
     * @date 2020/3/21
     * @param request
     * @param response
     * @return void
     */

    protected void upLoadXml(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //核心Api
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        //判断enctype是否是muitipart/form-data类型
        if (!ServletFileUpload.isMultipartContent(request)) {
            response.getWriter().println("表单的enctype属性不是multipart/form-data类型");
        }
        //设置单个文件上传大小 2M
        fileUpload.setFileSizeMax(2 * 1024 * 1024);
        //设置总上传文件大小(有时候一次性上传多个文件，需要有一个上限,此处为10M)
        fileUpload.setSizeMax(10 * 1024 * 1024);

        List<User> list =new ArrayList<>();
        //解析请求
        try {
            List<FileItem> parseRequest = fileUpload.parseRequest(request);
            //获取数据
            for (FileItem fileItem : parseRequest) {
                //判断数据类型是不是普通的form表单字段
                if (!fileItem.isFormField()) {
                    //获取文件名称
                    String fileName = fileItem.getName();
                    //截取后缀
                    String[] arr = fileName.split("\\.");
                    String suffix = arr[arr.length - 1];
                    //根据上传的excel文件构造workbook实例-注意区分xls与xlsx版本对应的实例
                    InputStream fileStream = fileItem.getInputStream();
                    Workbook wb = excelService.getWorkbook(fileStream, suffix);
                    //读取上传上来的excel的数据到List<User>中
                     list = excelService.getExcelData(wb);
                } else {
                    //普通字段
                    //字段名
                    String fieldName = fileItem.getFieldName();
                    //字段值
                    String fieldValue = fileItem.getString();
                    System.out.println(fieldName + ":" + fieldValue);
                }
            }

            Integer flag = 0;
            for (int i = 0; i <list.size(); i++) {
                User user = list.get(i);
               flag = userService.userRegister(user);
            }
            if (flag==1){
                response.sendRedirect("/html/main/useradd.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
