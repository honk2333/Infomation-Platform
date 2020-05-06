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
					<h2>小组简介</h2>
					<hr/>
					<div>
						<p>
							<font color="red">“东北大学软件工程项目-学生互助平台”</font>
						</p>
						
						<p>
							主要功能包括代取快递、拼车互助、失物认领，以及可在软件上通用的交流平台系统。
						</p>
					
						<p>
							使用范围为东北大学浑南校区。
						</p>

						<p>小组成员：</p>
						<p>计算机1704 20174634王洪科（组长）</p>
						<p>计算机1704 20174687赵丹迪</p>
						<p>计算机1704 20174956刘炯驿</p>
						<p>计算机1705 20174685安卓君</p>
						<p>计算机1704 20174680万欣航</p>
						<p>计算机1704 20174581王婷婷</p>	
						<p>计算机1704 20174707董铎</p>
						
					</div>
				</div>
			</div>
		</div>
		
		<!-- 引入footer.jsp -->
		<jsp:include page="/footer.jsp"></jsp:include>

	</body>

</html>