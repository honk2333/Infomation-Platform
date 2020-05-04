<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<c:if test="${!empty sessionScope.user}">
				<li><a href="${pageContext.request.contextPath}/product?method=findOrderList">欢迎您，${sessionScope.user.username}</a></li>
			</c:if>
			<c:if test="${empty sessionScope.user}">
				<li><a href="login.jsp">登录</a></li>
				<li><a href="register.jsp">注册</a></li>
			</c:if>

			<li><a href="cart.jsp">购物车</a></li>
			<c:if test="${!empty sessionScope.user}">
				<li><a href="${pageContext.request.contextPath}/product?method=findOrderList">我的订单</a></li>
				<li><a href="${pageContext.request.contextPath}/user?method=logout">安全退出</a></li>
			</c:if>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
						data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=index">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="ul1">
					<%--<c:forEach items="${categoryList}" var="category">--%>
						<%--<li class="active">--%>
							<%--<a href="#">${category.cname}</a>--%>
						<%--</li>--%>
					<%--</c:forEach>--%>

				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>

		<script type="text/javascript">
			$(function () {
			    var content="";
                //ajax异步刷新列表分类，也可以放到session中
				$.post(
                    <%--[{},{},{},{}] --%>
					"${pageContext.request.contextPath}/product?method=categoryList",
					function(date){
					    for(var i=0;i<date.length;i++){
					        content+="<li class=\"active\"><a href=\"product?method=categoryListByCid&cid="+date[i].cid+"\">"+date[i].cname+"</a></li>";
						}
						$("#ul1").html(content);
					},
					"json"
				);
            });
		</script>
	</nav>
</div>