
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<link rel="stylesheet" href="../../static/css/globalbase.css">
<body>
<%@ include file="/html/comment/head.jsp"%>
<div class="main">
    <%@ include file="/html/comment/menu.jsp"%>
    <div class="main_right">
        <form action="/html/dept/deptList" method="post">
            <input type="text" name="deptname" value="${deptname}" class="form-control" placeholder="部门名称" style="width: 150px;float: left">
            <input type="submit" class="btn btn-primary" >
        </form>
        <a href="/html/main/deptadd.jsp" class="btn btn-success">添加</a>
        <table class="table deptDetail">
            <tr>
                <td>编号</td>
                <td>部门名称</td>
                <td>部门人数</td>
                <td>操作</td>
            </tr>
            <c:forEach items="${deptlist}" varStatus="status" var="dept">
                <tr>
                    <td>${status.index+1}</td>
                    <td>${dept.name}</td>
                    <td>
                        当前有:
                        <span style="font-size: 20px ;color: red">${dept.count}</span>
                        人
                    </td>
                    <td>
                        <a href="/html/dept/deptWrite?id=${dept.id}" class="btn btn-info">编辑</a>
                        <a href="/html/dept/deptDelete?id=${dept.id}" class="btn btn-danger">删除</a>
                    </td>

                </tr>
            </c:forEach>
        </table>
        <a href="/html/dept/deptList?pagecurrent=1&deptname=${deptname}">首页</a>
        <a href="/html/dept/deptList?pagecurrent=${page.pagecurrent-1<=0?1:page.pagecurrent-1}&deptname=${deptname}">上一页</a>
        <a href="/html/dept/deptList?pagecurrent=${page.pagecurrent+1>=page.pagecount?page.pagecount:page.pagecurrent+1}&deptname=${deptname}">下一页</a>
        <a href="/html/dept/deptList?pagecurrent=${page.pagecount}&deptname=${deptname}">末页</a>
        <span>总页数:${page.pagecount}</span>
        <span>当前页:${page.pagecurrent}</span>
    </div>
</div>


</body>
</html>
