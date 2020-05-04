<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
	width: 100%;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
</head>

<body>


	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>


	<div class="row" style="width: 1210px; margin: 0 auto;">
		<div class="col-md-12">
			<ol class="breadcrumb">
				<li><a href="#">首页</a></li>
			</ol>
		</div>
		<%--商品列表--%>
		<c:forEach items="${pageInfo.listPage}" var="product">
			<div class="col-md-2" style="height: 250px">
				<a href="${pageContext.request.contextPath}/product?method=findProductInfo&pid=${product.pid}">
					<img src="${pageContext.request.contextPath}/${product.pimage}"
						 width="170" height="170" style="display: inline-block;">
				</a>
				<p>
					<a href="${pageContext.request.contextPath}/product?method=findProductInfo&pid=${product.pid}" style='color: green'>${product.pname}</a>
				</p>
				<p>
					<font color="#FF0000">商城价：&yen;${product.shop_price}</font>
				</p>
			</div>
		</c:forEach>
	</div>

	<c:choose>
		<c:when test="${pageInfo.totalPage<5}">
			<c:set var="begin" value="1"></c:set>
			<c:set var="end" value="${pageInfo.totalPage}"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="begin" value="${pageInfo.currentPage-2}"></c:set>
			<c:set var="end" value="${pageInfo.currentPage+2}"></c:set>
			<c:if test="${begin<=0}">
				<c:set var="begin" value="1"></c:set>
				<c:set var="end" value="5"></c:set>
			</c:if>
			<c:if test="${end>pageInfo.totalPage}">
				<c:set var="begin" value="${pageInfo.totalPage-4}"></c:set>
				<c:set var="end" value="${pageInfo.totalPage}"></c:set>
			</c:if>
		</c:otherwise>
	</c:choose>
	<!--分页 -->
	<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
		<ul class="pagination" style="text-align: center; margin-top: 10px;">
			<%--回到第一页--%>
			<li>
				<a href="${pageContext.request.contextPath}/product?method=categoryListByCid&cid=${cid}" aria-label="Previous"><span
					aria-hidden="true">&laquo;</span>
				</a>
			</li>
			<c:forEach begin="${begin}" end="${end}" var="i">
				<%--判断是否为当前页--%>
				<c:if test="${i==pageInfo.currentPage}">
					<li><a href="javascript:void(0);">${i}</a></li>
				</c:if>

				<c:if test="${i!=pageInfo.currentPage}">
					<li><a href="${pageContext.request.contextPath}/product?method=categoryListByCid&cid=${cid}&currentPage=${i}">${i}</a></li>
				</c:if>
			</c:forEach>
			<%--回到最后一页--%>
			<li>
				<a href="${pageContext.request.contextPath}/product?method=categoryListByCid&cid=${cid}&currentPage=${pageInfo.totalPage}" aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
			     </a>
			</li>
		</ul>
	</div>
	<!-- 分页结束 -->

	<!--商品浏览记录-->
	<div
		style="width: 1210px; margin: 0 auto; padding: 0 9px; border: 1px solid #ddd; border-top: 2px solid #999; height: 246px;">

		<h4 style="width: 50%; float: left; font: 14px/30px 微软雅黑">浏览记录</h4>
		<div style="width: 50%; float: right; text-align: right;">
			<a href="">more</a>
		</div>
		<div style="clear: both;"></div>

		<div style="overflow: hidden;">

			<ul style="list-style: none;">
				<c:forEach items="${productList}" var="product">
					<li style="width: 150px; height: 216px; float: left; margin: 0 8px 0 0;
					    padding: 0 18px 15px; text-align: center;">

						<img  src="${product.pimage}" width="130px" height="130px" />
						<p>
							<font color="green">${product.pname}</font>
						</p>
					</li>
				</c:forEach>

			</ul>

		</div>
	</div>


	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>