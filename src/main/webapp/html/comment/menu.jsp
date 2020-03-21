<%--
  Created by IntelliJ IDEA.
  User: PMDGJJW
  Date: 2020/3/18
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>home</title>
</head>
<body>
<div class="main_left">
    <%--系统管理--%>
    <%--<ul>--%>
        <%--<li><a href="/html/manage/userList">用户管理</a></li>--%>
        <%--<li><a href="/html/dept/deptList">部门管理</a></li>--%>
    <%--</ul>--%>
    <%--测试--%>
    <%--<ul>--%>
        <%--<li><a href="">测试1</a></li>--%>
        <%--<li><a href="">测试2</a></li>--%>
    <%--</ul>--%>
    <%--测试--%>
    <%--<ul>--%>
        <%--<li><a href="">测试1</a></li>--%>
        <%--<li><a href="">测试2</a></li>--%>
    <%--</ul>--%>

</div>
</body>
<script>
    $(function () {
        $.ajax({
            url:"/Menu/menuListALL",
            data:{},
            method:"post",
            dataType:"json"
        }).then(function (data) {
            var html="";
            for (var i = 0; i <data.firstMenu.length ; i++) {
                html+=data.firstMenu[i].name
                html+='<ul>'
                for (var j = 0; j <data.secondMenu.length ; j++) {
                    if (data.firstMenu[i].id==data.secondMenu[j].pId){
                        html+= '<li>' +
                            '      <a href="'+data.secondMenu[j].url+'">'+data.secondMenu[j].name+'</a>  ' +
                            ' </li> '
                    }
                }
                html+='</ul>'
            }

            $(".main_left").append(html)
        })
    })
</script>
</html>
