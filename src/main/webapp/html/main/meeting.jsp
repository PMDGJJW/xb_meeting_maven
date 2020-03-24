<%--
  Created by IntelliJ IDEA.
  User: PMDGJJW
  Date: 2020/3/21
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>会议系统</title>
</head>
<link rel="stylesheet" href="../../static/css/globalbase.css">
<body>
<%@ include file="/html/comment/head.jsp"%>
<div class="main">
    <%@ include file="/html/comment/menu.jsp"%>
        <div class="main_right">
        <form action="/html/meet/meetManager" method="post">
            <input type="text" name="meetTitle" placeholder=" 会议标题" value="${meet.title}">
            <select name="status" id="" >
                <option  value="-1"<c:if test="${status=='-1'}"> selected </c:if> >--请选择状态--</option>
                <option  value="0" <c:if test="${status=='0'}"> selected </c:if> >未开始</option>
                <option  value="1" <c:if test="${status=='1'}"> selected </c:if> >正在进行</option>
                <option  value="2" <c:if test="${status=='2'}"> selected </c:if>>已经结束</option>

            </select>
            <input type="submit" value="查询">
        </form>
        <a href="/html/meet/meetAdd" class="btn btn-danger">发布会议</a>
            <div>
                <table class="table">
                    <c:forEach var="meet" items="${meetlistall}">
                        <tr>
                            <ul>
                                <span style="float: right" class="btn btn-primary" >状态:
                                <c:choose>
                                    <c:when test="${meet.status==0}">未开始</c:when>
                                    <c:when test="${meet.status==1}">进行中</c:when>
                                    <c:when test="${meet.status==2}">已结束</c:when>
                                </c:choose>
                                </span>
                                <li><a href="/html/meet/meetJoin?id=${meet.id}">${meet.title}</a> </li>
                                <li><span>部门:</span>${meet.deptName}</li>
                                <li><span>开始时间:
                                    <fmt:parseDate var="staTime" value="  ${meet.startTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:parseDate>
                                    <fmt:formatDate value="${staTime}" var="Time" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                                    ${Time}
                                </span></li>
                                <li>${meet.content}</li>

                            </ul>
                        </tr>

                    </c:forEach>
                </table>
                <a href="/html/meet/meetManager?pagecurrent=1&title=${title}">首页</a>
                <a href="/html/meet/meetManager?pagecurrent=${page.pagecurrent-1<=0?1:page.pagecurrent-1}&title=${title}">上一页</a>
                <a href="/html/meet/meetManager?pagecurrent=${page.pagecurrent+1>=page.pagecount?page.pagecount:page.pagecurrent+1}&title=${title}">下一页</a>
                <a href="/html/meet/meetManager?pagecurrent=${page.pagecount}&title=${title}">末页</a>
                <span>总页数:${page.pagecount}</span>
                <span>当前页:${page.pagecurrent}</span>
            </div>
        </div>
    </div>
</body>
</html>
