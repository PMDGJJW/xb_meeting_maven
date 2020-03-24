<%--
  Created by IntelliJ IDEA.
  User: PMDGJJW
  Date: 2020/3/22
  Time: 20:34
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
        <div>
            <h1 style="text-align: center">${Detil.title}</h1>
            <%--<c:if test="${joinOnce == 0}"><a href="/html/meet/meetDetil?id=${Detil.id}" style="float: right" class="btn btn-primary">参加会议</a></c:if>--%>
            <c:if test="${joinOnce == 1}"><a href="#" style="float: right" class="btn btn-danger" id="cansole">取消会议</a></c:if>
        <div>
            <ul>
                <li> 部门: ${Detil.deptName}</li>
                <li> 应到:${meetDetil.makeSize}人</li>
                <li>
                    实到: ${needSize}人</li>
                <li> 缺席:${meetDetil.missSize}人</li>

            </ul>
            <ul>
                <li>
                    <textarea name="" id="" cols="160" rows="10">
                        ${Detil.content}
                    </textarea>
                </li>
            </ul>
            <ul style="float: right">
                <fmt:parseDate var="staTime" value="${Detil.startTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:parseDate>
                <fmt:formatDate value="${staTime}" var="time" pattern="yyyy年MM月dd日 HH:mm:ss"></fmt:formatDate>
                <li >开始时间 :${time}</li>
            </ul>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        $("#cansole").click(function () {
            alert("会议已经开始了，不能取消了哦")
        })
    })
</script>
</html>
