<%--
  Created by IntelliJ IDEA.
  User: PMDGJJW
  Date: 2020/3/18
  Time: 12:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>头部1</title>
</head>
<body>
<div class="top">
    <div>
        <%--<img src="/img/userImage?pic=${loginUser.pic}" alt="" id="img" style="width: 70px;height: 70px">--%>
        <img src="/img/userImage/?pic=${sessionScope.get("session_login_check").pic}" alt="" id="img" style="width: 70px;height: 70px">
            <div style="float: right">
                <a href="/user/logOut" class="btn btn-danger">登出</a>
            </div>
    </div>
    <input type="hidden" value="${sessionScope.get("session_login_check").id}" id="userDetail">
    欢迎：${sessionScope.get("session_login_check").username}

</div>
</body>
<script>
    $(function () {
        var userDetailId = $("#userDetail").val()
        $("#img").click(function () {
            window.location.href="/user/userDetail?id="+userDetailId
        })
    })
</script>
</html>
