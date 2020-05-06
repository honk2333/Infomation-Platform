<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>我的收藏</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />
<style>
#cart-empty {
	
    height: 273px;
    padding-left: 770px;
    margin: 65px 0 130px;
    background: url("${pageContext.request.contextPath}/images/cart-empty.png") no-repeat 124px 0;
    background-position:center;
    color: #b0b0b0;
    overflow: hidden;
    _zoom: 1;
}



body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}
</style>
<script type="text/javascript">
	function delProFromCart(pid){
		if(confirm("您是否要删除该项？")){
			location.href="${pageContext.request.contextPath}/product?method=delProFromCart&pid="+pid;
		}
	}
	
	function clearCart(){
		if(confirm("您是否要清空购物车？")){
			location.href="${pageContext.request.contextPath}/product?method=clearCart";
		}
	}

</script>


</head>

<body>
	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>
	
		<c:if test="${empty cart}">
			<div>
				<img src="${pageContext.request.contextPath}/images/cart-empty.png">
				<a href="${pageContext.request.contextPath}/product?method=index">返回首页</a>
			</div>

		</c:if>
		<c:if test="${!empty cart}">
			<div class="container">
				<div class="row">

					<div style="margin:0 auto; margin-top:10px;width:950px;">
						<strong style="font-size:16px;margin:5px 0;">收藏详情</strong>
						<table class="table table-bordered">
							<tbody>
							<tr class="warning">
								<th>图片</th>
								<th>标题</th>
								<th>相关价格</th>
								<th>酬金</th>
<!-- 								<th>小计</th> -->
								<th>操作</th>
							</tr>
							<c:forEach items="${cart.cartItems}" var="cartItem">
								<tr class="active">
									<td width="60" width="40%">
										<input type="hidden" name="id" value="22">
										<img src="${pageContext.request.contextPath}/${cartItem.value.product.pimage}" width="70" height="60">
									</td>
									<td width="30%">
										<a target="${pageContext.request.contextPath}/product?method=findProductInfo&pid=${cartItem.value.product.pid}">${cartItem.value.product.pname}</a>
									</td>
									<td width="20%">
										￥${cartItem.value.product.shop_price}
									</td>
									<td width="10%">
											${cartItem.value.product.market_price}
									</td>
<!-- 									<td width="15%"> -->
<%-- 										<span class="subtotal">￥${cartItem.value.subtotal}</span> --%>
<!-- 									</td> -->
									<td>
										<a href="${pageContext.request.contextPath}/product?method=deleteCartItem&pid=${cartItem.key}" class="delete">删除</a>
									</td>
								</tr>
							</c:forEach>

							</tbody>
						</table>
					</div>
				</div>

				<div style="margin-right:130px;">
					<div style="text-align:right;">
						<em style="color:#ff6600;">
							&nbsp;&nbsp;
						</em> 赠送积分: <em style="color:#ff6600;">${cart.total}</em>&nbsp; 可获得的酬金总金额: <strong style="color:#ff6600;">￥${cart.total}元</strong>
					</div>
					<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
						<a href="${pageContext.request.contextPath}/product?method=clearCart" id="clear" class="clear">清空我的收藏</a>

<%--                         <a href="${pageContext.request.contextPath}/product?method=submitOrder"> --%>
<!-- 							<input  type="button" width="100" value="提交订单" name="submit" border="0" -->
<!-- 								 style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); -->
<!-- 								 height:35px;width:100px;color:white;"> -->
<!-- 						</a> -->
					</div>
				</div>

			</div>
		</c:if>
		
	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>