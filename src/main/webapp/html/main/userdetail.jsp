
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
            <img src="/img/userImage?pic=${loginUser.pic}" alt="" id="userdetailimg" style="width: 70px;height: 70px">
            <input type="file" id="picFile" style="display: none">
        </div>
        <form action="/user/userDetailUpdate" method="post">
            <input type="text" value="${user.id}" style="display: none" id="detailid" name="id">
           真实姓名: <input type="text" name="realName"  value="${user.realName}" ><br><br>
           所属部门: <select name="dept" id="dept" >
                        <option value="-1">--请选择--</option>
                        <c:forEach var="dept" items="${depts}">
                            <option  value="${dept.id}"  <c:if test="${dept.id==user.deptId}">
                                selected
                            </c:if>>${dept.name}

                            </option>
                        </c:forEach>

                    </select><br><br>

            年龄: <input type="text" name="age" value="${user.age}"><br><br>
            性别: <input type="radio" name="gender" value="1" <c:if test="${user.gender==1}" > checked </c:if>  >男
            <input type="radio" name="sex" value="0" <c:if test="${user.gender==0}" > checked </c:if>  >女
            <br><br>
            注册时间: ${user.registerTime}<br><br>
            上次时间: ${user.loginTime}<br><br>
            是否私密: <input type="checkbox" name="isSecret" value="0" <c:if test="${user.isSecret==0}">checked </c:if> ><br><br>
            <input type="submit" class="btn btn-success " value="保存">
        </form>
    </div>
</div>
</body>
<script>
    $(function () {
        $("#userdetailimg").click(function () {
            $("#picFile").click()
        })
        $("#picFile").change(function () {
            // 构造文件上传form
            var formData = new FormData();
            formData.append("iconFile", document.getElementById("picFile").files[0]);
            $.ajax({

                url: "/img/uploadHeadImg",
                processData: false,      //默认为true,对请求传递的参数(formData)不做编码处理
                contentType: false,       //不对请求头做处理
                data: formData,
                type: "post",
                dataType: "json",
                async: true,
                success: function (res) {
                    if (res.status == 200) {
                        // 上传成功后置空
                        $("#picFile").val("");
                        // 替换成后台传递过来的新的图片url
                        var pic = res.picUrl;
                        $("#userdetailimg").attr("src", "/img/userImage?pic=" + pic);
                        $("#img").attr("src", "/img/userImage?pic=" + pic);
                    } else {
                        alert("修改头像失败！");
                    }
                }

            })
        })
    })
</script>
</html>
