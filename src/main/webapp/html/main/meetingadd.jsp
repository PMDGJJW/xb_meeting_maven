<%--
  Created by IntelliJ IDEA.
  User: PMDGJJW
  Date: 2020/3/22
  Time: 10:05
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
       <h1>发布会议</h1>
        <form action="/html/meet/meetAddContent" method="post">
            <table>
                <tr>
                    <td>会议标题</td>
                    <td><input type="text" name="title" placeholder="会议标题"></td>
                </tr>
                <tr>
                    <td>
                        <span>部门:</span>
                        <select name="dept" id="dept">
                            <option value="-1">--请选择--</option>
                            <c:forEach items="${deptName}" var="dept">
                                <option value="${dept.id}">${dept.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td >
                        <span>抄送人</span>
                        <select name="users"  class="selectpicker"
                                <%--data-live-search="true"--%>
                                multiple name="makeUser" id="users"
                                required> >
                            <option value="-1">--请选择--</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>开始时间</td>
                    <td><input type="text" name="stareTime" placeholder="2020-1-1 12:12:12" id="stare"></td>
                </tr>
                <tr>
                    <td>结束时间</td>
                    <td><input type="text" name="endTime" placeholder="2020-1-1 12:12:12"></td>
                </tr>
                <tr>
                    <td>会议内容</td>
                    <td><input type="text" name="content" ></td>
                </tr>
                <tr>
                    <td>
                        <input type="submit"class="btn btn-success" value="发布会议">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
<script src="../../static/js/fSelect.js"></script>
<script type="text/javascript">
    $(function () {
        $("#dept").change(function () {
           var deptID = $(this).val();
            html="";
            $.ajax({
                url:"/html/meet/userFindByDeptID",
                data:{
                    deptID:deptID
                },
                method:"post",
                dataType:"json",
                success:function (data) {
                    for (var i = 0; i <data.length ; i++) {
                        html+='<option value="'+data[i].id+'">'+data[i].username+'</option>'
                    }
                    $("#users").html(html)
                    // 刷新页面UI
                    $('#users').selectpicker('refresh');
                }
            })

        })


    })
</script>

</html>
