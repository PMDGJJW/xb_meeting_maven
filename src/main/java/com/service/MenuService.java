package com.service;

import com.dao.MenuDao;
import com.entity.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @auth jian j w
 * @date 2020/3/19 21:58
 * @Description
 */
public class MenuService {

    //注入MenuDao
    private MenuDao menuDao = new MenuDao();

    //查询目录数据
    public Map<String, List<Menu>> menuListALL (){
        Map<String,List<Menu>> map = new HashMap<>();

        List <Menu> list = menuDao.menuListALL();

        //第一级目录
        List<Menu> firstMenu = new ArrayList<>();
        //第二级目录
        List<Menu> secondMenu = new ArrayList<>();

        firstMenu= list.stream().filter(n->{
            return n.getType()==0;
        }).collect(Collectors.toList());

        secondMenu= list.stream().filter(n->{
            return n.getType()==1;
        }).collect(Collectors.toList());

        map.put("firstMenu",firstMenu);
        map.put("secondMenu",secondMenu);

        return map;
    }

}
