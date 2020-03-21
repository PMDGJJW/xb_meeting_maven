<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="../static/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="../static/css/style.css">
		<link rel="stylesheet" href="../static/css/form-elements.css">
		<link rel="stylesheet" href="../static/css/font-awesome.min.css">
		<link rel="stylesheet" href="../static/css/layer.css">

		<%-- QQ登录 --%>
		<script type="text/javascript" charset="utf-8" src="http://connect.qq.com/qc_jssdk.js" data-appid="101837854"
		 data-redirecturi="http://xb.dfbz.com/qq_login"></script>
		<title>登录</title>
		<style>
			body {
            background: url("../static/img/bg.jpg") no-repeat fixed;
            background-size: cover;
            overflow: hidden;
        }

    </style>
	</head>
	<body>
		<div id="wx"></div>
		<div class="container myBox">
			<div class="col-xs-8 col-xs-offset-4 col-sm-6 col-sm-offset-3 form-box">
				<div class="form-top">
					<div class="form-top-left">
						<h3>小标会议平台</h3>
						<p style="color:red">${error}</p>
						<p>请输入您的信息:</p>
					</div>
					<div class="form-top-right">
						<i class="fa fa-key"></i>
					</div>
				</div>
				<div class="form-bottom">
					<form role="form" action="/user/login" method="post" class="login-form">

						<!--上面的输入框尽可能不需要外边距，使用row解决-->
						<div class="row">
							<div class="form-group">
								<label class="sr-only" for="form-username">Username</label>
								<input type="text" name="username" placeholder="用户名" class="form-username form-control" id="form-username">
							</div>
							<div class="form-group">
								<label class="sr-only" for="form-password">Password</label>
								<input type="password" name="password" placeholder="密码" class="form-password form-control" id="form-password">
							</div>
							<div class="form-group">
								<input type="text" name="checkCode" id="checkCode" placeholder="请输入验证码" style="width: 180px;" />
								<a onclick="document.getElementById('code').src='/checkCode.jsp?v='+Math.random()">
									<img src="/checkCode.jsp" title="看不清点击刷新" id="code" />
								</a>
							</div>
						</div>

						<div class="checkbox">
							<label>
								<input type="checkbox" name="remember" value="1"> 记住我
							</label>
						</div>
						<button type="submit" class="btn">登录</button>

						<div class="row">
							<div style="padding: 10px 25px">
								<div style="display: inline-block;float: left" class="text-left"><a href="forget.jsp">忘记密码?</a>
								</div>
								<div style="display: inline-block;float: right" class="text-right"><a href="/register.jsp">没有账号?</a></div>
							</div>
						</div>

					</form>
					<button id="qqLogin">qq登录</button>
					<button id="wxLogin">微信登录</button>
					<hr>

				</div>
			</div>
		</div>


		<!-- Javascript -->
		<script src="/js/jquery-1.11.1.min.js"></script>
		<script src="/bootstrap/js/bootstrap.min.js"></script>
		<script src="/js/jquery.backstretch.min.js"></script>
		<script src="/js/scripts.js"></script>
		<script src="/js/layer.js"></script>
		<script>
			$(function() {

				$("#qqLogin").click(function() {
					location.href = "/user/qq_login";
				})

				$("#wxLogin").click(function() {
					location.href = "/user/wx_login";
				})


			})
		</script>
	</body>
</html>
