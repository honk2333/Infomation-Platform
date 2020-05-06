<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>NEU信息平台首页</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
</head>

<body>
	<div class="container-fluid">

		<!-- 引入header.jsp -->
		<jsp:include page="/header.jsp"></jsp:include>

		<!-- 代取快递 -->
		<div class="container-fluid">
			<div class="col-md-12">
				<h2>代取快递&nbsp;&nbsp;</h2>
			</div>
			<!-- 				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;"> -->
			<!-- 					<img src="products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/> -->
			<!-- 				</div> -->
			<div class="col-md-10">
				<div class="col-md-6"
					style="text-align: center; height: 200px; padding: 0px;">
					<!-- 						<a href="product_info.htm"> -->
					<a
						href='${pageContext.request.contextPath}/product?method=productList&cid=1'>
						<img src="products/hao/middle01.jpg" width="516px" height="200px"
						style="display: inline-block;">
					</a>
				</div>

				<%-- 					<c:forEach items="${hotProductList }" var="hotPro"> --%>

				<!-- 						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;"> -->
				<!-- 							<a href="product_info.htm"> -->
				<%-- 								<img src="${pageContext.request.contextPath }/${hotPro.pimage }" width="130" height="130" style="display: inline-block;"> --%>
				<!-- 							</a> -->
				<%-- 							<p><a href="product_info.html" style='color:#666'>${hotPro.pname }</a></p> --%>
				<%-- 							<p><font color="#E4393C" style="font-size:16px">&yen;${hotPro.shop_price }</font></p> --%>
				<!-- 						</div> -->

				<%-- 					</c:forEach> --%>

			</div>
		</div>

		<!-- 拼车互助 -->
		<div class="container-fluid">
			<div class="col-md-12">
				<h2>拼车互助&nbsp;&nbsp;</h2>
			</div>
			<!-- 				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;"> -->
			<!-- 					<img src="products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/> -->
			<!-- 				</div> -->
			<div class="col-md-10">
				<div class="col-md-6"
					style="text-align: center; height: 200px; padding: 0px;">
					<a href='${pageContext.request.contextPath}/product?method=productList&cid=2'>
						<img src="products/hao/middle01.jpg" width="516px" height="200px"
						style="display: inline-block;">
					</a>
				</div>

				<%-- 					<c:forEach items="${hotProductList }" var="hotPro"> --%>

				<!-- 						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;"> -->
				<!-- 							<a href="product_info.htm"> -->
				<%-- 								<img src="${pageContext.request.contextPath }/${hotPro.pimage }" width="130" height="130" style="display: inline-block;"> --%>
				<!-- 							</a> -->
				<%-- 							<p><a href="product_info.html" style='color:#666'>${hotPro.pname }</a></p> --%>
				<%-- 							<p><font color="#E4393C" style="font-size:16px">&yen;${hotPro.shop_price }</font></p> --%>
				<!-- 						</div> -->

				<%-- 					</c:forEach> --%>

			</div>
		</div>

		<!-- 失物招领 -->
		<div class="container-fluid">
			<div class="col-md-12">
				<h2>失物招领&nbsp;&nbsp;</h2>
			</div>
			<!-- 				<div class="col-md-2" style="border:1px solid #E7E7E7;border-right:0;padding:0;"> -->
			<!-- 					<img src="products/hao/big01.jpg" width="205" height="404" style="display: inline-block;"/> -->
			<!-- 				</div> -->
			<div class="col-md-10">
				<div class="col-md-6"
					style="text-align: center; height: 200px; padding: 0px;">
					<a href='${pageContext.request.contextPath}/product?method=productList&cid=3'> <img
						src="products/hao/middle01.jpg" width="516px" height="200px"
						style="display: inline-block;">
					</a>
				</div>

				<%-- 					<c:forEach items="${newProductList }" var="newPro"> --%>

				<!-- 						<div class="col-md-2" style="text-align:center;height:200px;padding:10px 0px;"> -->
				<!-- 							<a href="product_info.htm"> -->
				<%-- 								<img src="${pageContext.request.contextPath }/${newPro.pimage }" width="130" height="130" style="display: inline-block;"> --%>
				<!-- 							</a> -->
				<%-- 							<p><a href="product_info.html" style='color:#666'>${newPro.pname }</a></p> --%>
				<%-- 							<p><font color="#E4393C" style="font-size:16px">&yen;${newPro.shop_price }</font></p> --%>
				<!-- 						</div> -->

				<%-- 					</c:forEach> --%>

			</div>
		</div>

		<!-- 			<!-- 轮播图 -->
		-->
		<!-- 			<div class="container-fluid"> -->
		<!-- 				<div id="carousel-example-generic" class="carousel slide" data-ride="carousel"> -->
		<!-- 					轮播图的中的小点 -->
		<!-- 					<ol class="carousel-indicators"> -->
		<!-- 						<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li> -->
		<!-- 						<li data-target="#carousel-example-generic" data-slide-to="1"></li> -->
		<!-- 						<li data-target="#carousel-example-generic" data-slide-to="2"></li> -->
		<!-- 					</ol> -->
		<!-- 					轮播图的轮播图片 -->
		<!-- 					<div class="carousel-inner" role="listbox"> -->
		<!-- 						<div class="item active"> -->
		<!-- 							<img src="img/1.jpg"> -->
		<!-- 							<div class="carousel-caption"> -->
		<!-- 								轮播图上的文字 -->
		<!-- 							</div> -->
		<!-- 						</div> -->
		<!-- 						<div class="item"> -->
		<!-- 							<img src="img/2.jpg"> -->
		<!-- 							<div class="carousel-caption"> -->
		<!-- 								轮播图上的文字 -->
		<!-- 							</div> -->
		<!-- 						</div> -->
		<!-- 						<div class="item"> -->
		<!-- 							<img src="img/3.jpg"> -->
		<!-- 							<div class="carousel-caption"> -->
		<!-- 								轮播图上的文字 -->
		<!-- 							</div> -->
		<!-- 						</div> -->
		<!-- 					</div> -->

		<!-- 					上一张 下一张按钮 -->
		<!-- 					<a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev"> -->
		<!-- 						<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> -->
		<!-- 						<span class="sr-only">Previous</span> -->
		<!-- 					</a> -->
		<!-- 					<a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next"> -->
		<!-- 						<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> -->
		<!-- 						<span class="sr-only">Next</span> -->
		<!-- 					</a> -->
		<!-- 				</div> -->
		<!-- 			</div> -->


		<!-- 引入footer.jsp -->
		<jsp:include page="/footer.jsp"></jsp:include>

	</div>
</body>

</html>