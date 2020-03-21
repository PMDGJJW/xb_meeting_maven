<%--
  Created by IntelliJ IDEA.
  User: PMDGJJW
  Date: 2020/3/18
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
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
        <div style="float: left">
            <form action="/html/manage/userList" method="post">
                <input type="text" name="username" value="${username}" class="form-control" placeholder="用户名" style="width: 150px;float: left">
                <input type="submit" class="btn btn-primary" value="查询">
            </form>
        </div>
        <div style="float: left">
            <form action="/xml/upLoadXml" method="post" enctype="multipart/form-data">
                <input type="file" name="userExcel">
                <input type="submit" class="btn btn-primary" value="上传">
            </form>
                <a href="/xml/dowLoadXml?username=${username}" class="btn btn-info">导出为Exl</a>
                <a href="/xml/xmlTemplate" class="btn btn-success">下载上传模板</a>
        </div>
        <table class="table deptDetail">
            <tr>
                <td>编号</td>
                <td>用户名</td>
                <td>真实姓名</td>
                <td>年龄</td>
                <td>性别</td>
                <td>创建时间</td>
                <td>创建人</td>
                <td>操作</td>
            </tr>
            <c:forEach items="${list}" varStatus="status" var="user">
                <tr>
                    <td>${status.index+1}</td>
                    <td>${user.username}</td>
                    <td>${user.realName}</td>
                    <td>${user.age}</td>
                    <td>
                       <c:choose>
                           <c:when test="${user.gender=='1'}">男</c:when>
                           <c:when test="${user.gender=='0'}">女</c:when>
                       </c:choose>
                    </td>
                    <td>
                        <fmt:parseDate var="createTime" value="${user.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:parseDate>
                        <fmt:formatDate value="${createTime}" pattern="yyyy年MM月dd日 HH:mm:ss" var="cTime"></fmt:formatDate>
                        ${cTime}
                    </td>
                    <td>${user.createBy}</td>
                </tr>
            </c:forEach>
        </table>
        <a href="/html/manage/userList?pagecurrent=1&username=${username}">首页</a>
        <a href="/html/manage/userList?pagecurrent=${page.pagecurrent-1<=0?1:page.pagecurrent-1}&username=${username}">上一页</a>
        <a href="/html/manage/userList?pagecurrent=${page.pagecurrent+1>=page.pagecount?page.pagecount:page.pagecurrent+1}&username=${username}">下一页</a>
        <a href="/html/manage/userList?pagecurrent=${page.pagecount}&username=${username}">末页</a>
        <span>总页数:${page.pagecount}</span>
        <span>当前页:${page.pagecurrent}</span>
    </div>
</div>


</body>
</html>
