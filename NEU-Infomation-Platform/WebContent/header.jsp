<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- <!-- 登录 注册 我的收藏... --> -->
<!-- <div class="container-fluid"> -->
<!-- 	<div class="col-md-4"> -->
<%-- 	<a href="${pageContext.request.contextPath}/product?method=index"><img src="img/logo2.png --%>
<!-- 	" /></a>		 -->
<!-- 	</div> -->
<!-- 	<div class="col-md-5"> -->
<!-- 		<img src="img/header.png" /> -->
<!-- 	</div> -->
<!-- 	<div class="col-md-3" style="padding-top:20px"> -->
<!-- 		<ol class="list-inline"> -->
<!-- 			<li><a href="login.jsp">登录</a></li> -->
<!-- 			<li><a href="register.jsp">注册</a></li> -->
<!-- 			<li><a href="cart.jsp">我的收藏</a></li> -->
<!-- 			<li><a href="order_list.jsp">我的订单</a></li> -->
<!-- 		</ol> -->
<!-- 	</div> -->
<!-- </div> -->

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

			<li><a href="star.jsp">我的收藏</a></li>
			<c:if test="${!empty sessionScope.user}">
				<li><a href="cart.jsp">我发布的信息</a></li>
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
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}/product?method=index">首页</a>
				<c:if test="${!empty sessionScope.user}">
				<a class="navbar-brand" href="${pageContext.request.contextPath}/admin/product/add.jsp">发布信息</a>
				</c:if>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="categoryUl">
				
					<%-- <c:forEach items="${categoryList }" var="category">
						<li><a href="#">${category.cname }</a></li>
					</c:forEach> --%>
					
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
			//header.jsp加载完毕后 去服务器端获得所有的category数据
			$(function(){
				var content = "";
				$.post(
					"${pageContext.request.contextPath}/product?method=categoryList",
					function(data){
						//[{"cid":"xxx","cname":"xxxx"},{},{}]
						//动态创建<li><a href="#">${category.cname }</a></li>
						for(var i=0;i<data.length;i++){
							content+="<li><a href='${pageContext.request.contextPath}/product?method=productList&cid="+data[i].cid+"'>"+data[i].cname+"</a></li>";
						}
						
						//将拼接好的li放置到ul中
						$("#categoryUl").html(content);
					},
					"json"
				);
			});
		</script>
		
	</nav>
</div>