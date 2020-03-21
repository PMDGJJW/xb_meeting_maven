<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="static/css/style.css">
    <link rel="stylesheet" href="static/css/form-elements.css">
    <link rel="stylesheet" href="static/css/font-awesome.min.css">
    <link rel="stylesheet" href="static/css/layer.css">
    <title>重置密码</title>
    <style>
        body {
            background: url("/static/img/bg.jpg") no-repeat fixed;
            background-size: cover;
            overflow: hidden;
        }

    </style>
</head>
<body>

<div class="container myBox">
    <div class="col-xs-8 col-xs-offset-4 col-sm-6 col-sm-offset-3 form-box">
        <div class="form-top">
            <div class="form-top-left">
                <h3>重置密码</h3>
                <p style="color:red" id="msg"></p>
                <p>请输入您的邮箱:</p>
            </div>
            <div class="form-top-right">
                <i class="fa fa-key"></i>
            </div>
        </div>
        <div class="form-bottom">
            <form role="form" action="/user/passwordForget" class="login-form" id="mainForm" method="post">

                <!--上面的输入框尽可能不需要外边距，使用row解决-->
                <div class="row">
                    <div style="margin-bottom: 15px" class="form-inline">

                        <label class="sr-only" for="email">用户名</label>
                        <input type="text" name="username" placeholder="用户名" class="form-username"
                               id="username">
                        <br>
                        <label class="sr-only" for="email">Email</label>
                        <input type="text" name="email" placeholder="注册邮箱" class="form-username"
                               id="email" value="zilie487147@sohu.com">
                        <input type="button" class="btn btn-primary" value="发送验证码" id="sendEmailBtn">
                    </div>
                    <div class="form-group">
                        <label class="sr-only" for="form-password">验证码</label>
                        <input type="text" id="checkCode" placeholder="验证码" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label class="sr-only" for="form-password">Password</label>
                        <input type="password" name="password" placeholder="新密码" class="form-password form-control"
                               id="form-password">
                    </div>
                </div>
                <button type="submit" class="btn">重置</button>

                <div class="row">
                    <div style="padding: 10px 25px">
                        <div style="display: inline-block;float: left" class="text-left"><a href="/index.jsp">返回登录</a>
                        </div>
                        <!--<div style="display: inline-block;float: right" class="text-right"><a href="#">没有账号?</a></div>-->
                    </div>
                </div>

                <%-- 标识表单是否可以提交 0:不可以 1:可以 --%>
                <input type="hidden" id="flag_check" value="1">
                <input type="hidden" id="flag_email" value="1">
            </form>
        </div>
    </div>
</div>


<!-- Javascript -->
<script src="./static/js/jquery-3.4.1.js"></script>
<script src="./static/bootstrap/js/bootstrap.min.js"></script>
<script src="./static/js/jquery.backstretch.min.js"></script>

<script>
    $(function () {
        $("#form-password").attr("disabled",true)
        $("#email").blur(function () {
            var username = $("#username").val();
            var email = $("#email").val();
            $.ajax({
                url:"/user/usernameEmailCheck",
                data:{
                    username:username,
                    email:email
                },
                method:"post",
                dataType:"",
                success:function (data) {
                    if (data==="500"){
                        alert("该邮箱不是账号的注册邮箱")
                    }
                }
            })
        })
        $("#sendEmailBtn").click(function () {
            var email = $("#email").val();
            $.ajax({
                url: "/user/sendEmail",
                data: {
                    email:email
                },
                method: "",
                success:function (data) {
                    if (data==200){
                        alert("验证码发送成功,请注意签收");
                    }
                    else {
                        alert("验证码发送失败")
                    }
                }
            })
        })
        $("#checkCode").blur(function () {
            var code = $("#checkCode").val();
            $.ajax({
                url:"/user/checkCode",
                data:{
                    code:code
                },
                method:"post",
                success:function (data) {
                    if (data==="500"){
                        alert("验证码不正确")
                    }
                    else {
                        $("#form-password").attr("disabled",false)
                    }

                }
            })
        })
    })
</script>

</body>
</html>
