<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>小组信息</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
</head>

<body>
	<div class="container-fluid">

		<!-- 引入header.jsp -->
		<jsp:include page="/header.jsp"></jsp:include>

		<div class="container-fluid">
			<div class="main_con">
				<h2>信息发布者相关信息</h2>
				<hr />
				<div>
					<p>
						<font color="#FF0000">姓名：${user.username }</font>
						<font color="#FF0000">姓别：${user.sex }</font>
					</p>
					<p>
						<font color="#FF0000">手机号码：${user.telephone }</font> <font
							color="#FF0000">电子邮箱：${user.email }</font>
					</p>


				</div>
			</div>
		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>