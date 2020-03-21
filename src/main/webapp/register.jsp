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
		<title>注册</title>
		<style>
			body {
            background: url("static/img/bg.jpg") no-repeat fixed;
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
						<h3>新用户注册</h3>
						<p style="color:red" id="msg"></p>
						<p>请输入您的信息:</p>
					</div>
					<div class="form-top-right">
						<i class="fa fa-key"></i>
					</div>
				</div>
				<div class="form-bottom">
					<form role="form" action="/user/userRegister" method="post" class="login-form" id="mainForm">

						<!--上面的输入框尽可能不需要外边距，使用row解决-->
						<div class="row">

							<div class="form-group">
								<label class="sr-only" for="username">Username</label>
								<input type="text" name="username" placeholder="用户名" class="form-username form-control" id="username">
							</div>
							<div class="form-group">
								<label class="sr-only" for="password">Password</label>
								<input type="password" name="password" placeholder="密码" class="form-password form-control" id="password">
							</div>
							<div class="form-group">
								<label class="sr-only" for="email">Email</label>
								<input type="text" name="email" placeholder="邮箱" class="form-username form-control" id="email">
							</div>
							<div class="form-group">
								<label class="sr-only" for="rePassword">Password</label>
								<input type="password" name="rePassword" placeholder="确认密码" class="form-password form-control" id="rePassword">
							</div>
						</div>
						<!--上面的输入框尽可能不需要外边距，使用row包裹起来解决-->


						<button type="submit" class="btn">注册</button>

						<div class="row">
							<div style="padding: 10px 25px">
								<div style="display: inline-block;float: left" class="text-left"><a href="index.jsp">返回登录</a>
								</div>
								<!--<div style="display: inline-block;float: right" class="text-right"><a href="#">没有账号?</a></div>-->
							</div>
						</div>

					</form>
				</div>
			</div>
		</div>


		<!-- Javascript -->
		<script src="static/js/jquery-3.4.1.js"></script>
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
		<script src="static/js/jquery.backstretch.min.js"></script>

		<script>
            $("#email").blur(function () {
                var email = $("#email").val();
                $.ajax({
                    url:"/user/checkEmail",
                    data:{
                        email:email
                    },
                    method:"post",
                    dataType:"",
                    success:function (data) {
                       if (data==="500"){
                           alert("该邮箱已经注册过了")
					   }
                    }
                })
            })

            $("#username").blur(function () {
                var username = $("#username").val();
                $.ajax({
                    url:"/user/checkUsername",
                    data:{
                        username:username
                    },
                    method:"post",
                    dataType:"",
                    success:function (data) {
                        if (data==="500"){
                            alert("该用户名已经存在")
                        }
                    }
                })
            })
			$("#rePassword").blur(function () {
                var password = $("#password").val();
                var repassword = $(this).val();
                if (password!==repassword){
                    alert("密码不一致")
				}
            })
		</script>
	</body>
</html>
