package com.service;

import com.dao.DeptDao;
import com.entity.Dept;
import com.entity.Page;

import java.util.List;

/**
 * @auth jian j w
 * @date 2020/3/19 10:33
 * @Description
 */
public class DeptService {
        //注入DeptDAO
        private DeptDao deptDao = new DeptDao();
        //部门信息
        public List<Dept> deptList(Dept dept,Page page) {
                return deptDao.deptList(dept,page);
        }
        //部门分页
        public Integer deptCount(Dept dept){
                return deptDao.deptCount(dept);
        }
        //部门添加
        public Integer deptAdd(Dept dept){
                return deptDao.deptAdd(dept);
        }
        //部门名称检查
        public Dept deptCheck(String deptname){
                return deptDao.deptCheck(deptname);
        }
        //部门删除
        public Integer deptDelete(Integer id){
                return deptDao.deptDelete(id);
        }

        //部门编辑
        public List<Dept> deptWrite(Integer id){
                return deptDao.deptWrite(id);
        }

        //部门编辑提交
        public Integer deptRewrite(Dept dept){
                return deptDao.deptRewrite(dept);
        }

        //部门集合
        public List<Dept> deptListAll(){
                return deptDao.deptListAll();
        }

        /*
         * @Description 查询部门名称
         * @author jian j w
         * @date 2020/3/22
         */
        public List<Dept> deptNameList(){
            return deptDao.deptNameList();
        }
}
