package com.controller;

import com.alibaba.fastjson.JSON;
import com.entity.Menu;
import com.service.MenuService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * @auth jian j w
 * @date 2020/3/19 21:58
 * @Description
 */
@WebServlet("/Menu/*")
public class MenuServlet extends BaseServlet {

    //注入MenuService
    private MenuService menuService = new MenuService();
    protected void menuListALL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Map<String, List<Menu>> map = menuService.menuListALL();
        String mapJSONStr = JSON.toJSONString(map);

        PrintWriter out = response.getWriter();
        out.write(mapJSONStr);

    }
}
