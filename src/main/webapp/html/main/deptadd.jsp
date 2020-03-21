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
        <form action="/html/dept/deptAdd" method="post" >
            <input type="text" name="deptname" id="deptname"  class="form-control" placeholder="部门名称" style="width: 150px;float: left">
            <input type="submit" class="btn btn-primary" >
        </form>
    </div>
</div>
</body>
<script>
    $(function () {
        $("#deptname").blur(function () {
            var deptname = $("#deptname").val();
            $.ajax({
                url:"/html/dept/deptCheck",
                data:{
                    deptname:deptname
                },
                method:"post",
                dataType:"",
                success:function (data) {
                    if (data==="500"){
                        alert("该部门已经存在了")
                    }
                }
            })
        })
    })
</script>
</html>
