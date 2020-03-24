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
 * @Description 部门管理
 */
@WebServlet("/html/dept/*")
public class DeptManageServlet extends BaseServlet {
    //注入DeptService 部门控制层
    private DeptService deptService = new DeptService();

    /**
     * @Description 部门详情及分页查询
     * @author jian j w
     * @date 2020/3/19
     * @param request
     * @param response
     * @return void
     */
    protected void deptList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //按照部门名称查询
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

        //当前页
        Integer cuttenrt = Integer.valueOf(pagecurrent);

        Page page = new Page();
        page.setPagesize(5);
        page.setPagecurrent(cuttenrt);

        Integer pagecount = deptService.deptCount(dept);
        page.setCount(pagecount);

        //部门集合
        List<Dept> list = deptService.deptList(dept,page);
        request.setAttribute("deptlist",list);
        request.setAttribute("deptname",deptname);

        //设置分页
        request.setAttribute("page",page);
        request.getRequestDispatcher("/html/main/deptlist.jsp").forward(request,response);
    }

    /**
     * @Description 部门添加
     * @author jian j w
     * @date 2020/3/19
     * @param request
     * @param response
     * @return void
     */
    protected void deptAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //部门名称
        String deptname = request.getParameter("deptname");
        Dept dept = new Dept();

        if (deptname==null||deptname==""){
            response.sendRedirect("/html/main/deptadd.jsp");
        }
        dept.setName(deptname);

        //添加成功标识
        Integer checkflag = deptService.deptAdd(dept);
        if (checkflag==1){
            response.sendRedirect("/html/dept/deptList");
        }else {
            response.sendRedirect("/html/main/deptadd.jsp");
        }

    }

    /**
     * @Description 部门名称是否重复检查
     * @author jian j w
     * @date 2020/3/19
     * @param request
     * @param response
     * @return void
     */
    protected void deptCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String deptname = request.getParameter("deptname");
        if (deptname==null||deptname==""){
            return;
        }
        //查询结果
        Dept result = deptService.deptCheck(deptname);
        PrintWriter out = response.getWriter();
        //返回状态码 200成功，500失败
        if (result==null){
            out.write("200");
        }
        else {
            out.write("500");
        }
    }

    /**
     * @Description 部门删除
     * @author jian j w
     * @date 2020/3/19
     * @param request
     * @param response
     * @return void
     */
    protected void deptDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String id = request.getParameter("id");
        if (id==null||id==""){
            response.sendRedirect("/html/main/deptlist.jsp");
            return;
        }
        //是否删除成功结果 1是，0否
        Integer flag = deptService.deptDelete(Integer.valueOf(id));

        if (flag==1){
            response.sendRedirect("/html/dept/deptList");
            return;
        }else {
            response.sendRedirect("/html/main/deptlist.jsp");
            return;
        }
    }

    /**
     * @Description 部门编辑
     * @author jian j w
     * @date 2020/3/19
     * @param request
     * @param response
     * @return void
     */
    protected void deptWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String id = request.getParameter("id");
        if (id==null||id==""){
            response.sendRedirect("/html/main/deptlist.jsp");
            return;
        }
        //部门数据回显
        List<Dept> list = deptService.deptWrite(Integer.valueOf(id));
        request.setAttribute("list",list);
        request.getRequestDispatcher("/html/main/deptwrite.jsp").forward(request,response);
    }

    /**
     * @Description 部门编辑提交
     * @author jian j w
     * @date 2020/3/19
     * @param request
     * @param response
     * @return void
     */
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

        //修改成功标识码
        Integer flag = deptService.deptRewrite(dept);
        if (flag==1){
            response.sendRedirect("/html/dept/deptList");
        }else {
            response.sendRedirect("/html/main/deptlist.jsp");
        }

    }

    /**
     * @Description 部门集合
     * @author jian j w
     * @date 2020/3/19
     * @param request
     * @param response
     * @return java.util.List<com.entity.Dept>
     */
    protected List<Dept> deptListAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        return deptService.deptListAll();
    }
}
