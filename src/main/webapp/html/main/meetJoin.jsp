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
            <c:if test="${needjoin == '0'}">
                <a href="#" style="float: right" class="btn btn-danger">无需参加会议</a>
            </c:if>
            <c:if test="${needjoin == '1'}">
                <input type="button" class="btn btn-primary " value="参加会议" id="join">
                <%--<a href="/html/meet/meetDetil?id=${Detil.id}&userid=${sessionScope.get("session_login_check").id}" style="float: right" class="btn btn-primary" id="join">参加会议</a>--%>
            </c:if>
        </div>
        <div>

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
        var meetStatus =  ${meetStatus}
        $("#join").click(function () {
            if (meetStatus==0){
                alert("该会议还没开始哦")
            }
            else if (meetStatus==1){
                window.location.href="/html/meet/meetDetil?id=${Detil.id}&userid=${sessionScope.get("session_login_check").id}"
            }
            else if (meetStatus==2){
                alert("该会议已经结束了")
            }
        })
    })
</script>
</html>
