package com.controller;

import com.entity.Dept;
import com.entity.Page;
import com.service.DeptService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @auth jian j w
 * @date 2020/3/19 10:33
 * @Description
 */
@WebServlet("/html/dept/*")
public class DeptManageServlet extends BaseServlet {

    private DeptService deptService = new DeptService();
    //部门详情及分页查询
    protected void deptList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //部门查询
        Dept dept = new Dept();
        String deptname = request.getParameter("deptname");
        if (deptname==null||deptname==""){
            deptname="";
        }
        dept.setName(deptname);

        //部门分页
        String pagecurrent = request.getParameter("pagecurrent");
        if (pagecurrent==null||pagecurrent==""){
            pagecurrent="1";
        }
        Integer cuttenrt = Integer.valueOf(pagecurrent);
        Page page = new Page();
        page.setPagesize(5);
        page.setPagecurrent(cuttenrt);
        Integer pagecount = deptService.deptCount(dept);
        page.setCount(pagecount);

        List<Dept> list = deptService.deptList(dept,page);
        request.setAttribute("deptlist",list);
        request.setAttribute("deptname",deptname);
        //设置分页
        request.setAttribute("page",page);
        request.getRequestDispatcher("/html/main/deptlist.jsp").forward(request,response);
    }
    //部门添加
    protected void deptAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String deptname = request.getParameter("deptname");
        Dept dept = new Dept();

        if (deptname==null||deptname==""){
            response.sendRedirect("/html/main/deptadd.jsp");
        }
        dept.setName(deptname);
        Integer checkflag = deptService.deptAdd(dept);
        if (checkflag==1){
            response.sendRedirect("/html/dept/deptList");
        }else {
            response.sendRedirect("/html/main/deptadd.jsp");
        }
    }
    //部门名称检查
    protected void deptCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String deptname = request.getParameter("deptname");
        if (deptname==null||deptname==""){
            return;
        }
        System.out.println(deptname);
        Dept result = deptService.deptCheck(deptname);
        PrintWriter out = response.getWriter();
        if (result==null){
            out.write("200");
        }
        else {
            out.write("500");
        }
    }

    //部门删除
    protected void deptDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String id = request.getParameter("id");
        if (id==null||id==""){
            response.sendRedirect("/html/main/deptlist.jsp");
            return;
        }
        Integer flag = deptService.deptDelete(Integer.valueOf(id));

        if (flag==1){
            response.sendRedirect("/html/main/deptlist.jsp");
        }else {
            response.sendRedirect("/html/main/deptlist.jsp");
        }
    }

    //部门编辑
    protected void deptWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String id = request.getParameter("id");
        if (id==null||id==""){
            response.sendRedirect("/html/main/deptlist.jsp");
            return;
        }
        List<Dept> list = deptService.deptWrite(Integer.valueOf(id));
        request.setAttribute("list",list);
        System.out.println(list);
        request.getRequestDispatcher("/html/main/deptwrite.jsp").forward(request,response);
    }
    //部门编辑提交
    protected void deptRewrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String id = request.getParameter("id");
        String deptname = request.getParameter("deptname");

        if (id==null||id==""){
            response.sendRedirect("/html/main/deptlist.jsp");
            return;
        }
        if (deptname==null||deptname==""){
            response.sendRedirect("/html/main/deptlist.jsp");
            return;
        }
        Dept dept = new Dept();
        dept.setName(deptname);
        dept.setId(Integer.valueOf(id));
        Integer flag = deptService.deptRewrite(dept);

        if (flag==1){
            response.sendRedirect("/html/main/deptlist.jsp");
        }else {
            response.sendRedirect("/html/main/deptlist.jsp");
        }

    }

    //部门集合
    protected List<Dept> deptListAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        return deptService.deptListAll();
    }
}
