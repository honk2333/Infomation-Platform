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
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
<script type="text/javascript">
	function addCart() {
		//获得购买的商品的数量
		var buyNum = $("#buyNum").val(); //获得输入框输入的数量
		location.href = "${pageContext.request.contextPath}/product?method=addProductToCart&pid=${product.pid}&buyNum="
				+ 1;
	}
</script>

</script>
</head>

<body>
	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<!-- 			<div -->
			<!-- 				style="border: 1px solid #e4e4e4; width: 930px; margin-bottom: 10px; margin: 0 auto; padding: 10px; margin-bottom: 10px;"> -->
			<!-- 				<a href="./index.jsp">首页&nbsp;&nbsp;&gt;</a>  -->
			<!-- 				<a href="./失物招领.htm">蔬菜&nbsp;&nbsp;&gt;</a> -->
			<!-- 				<a>寻找手机</a> -->
			<!-- 			</div> -->

			<div style="margin: 0 auto; width: 950px;">
				<div class="col-md-6">
					<img style="opacity: 1; width: 400px; height: 350px;" title=""
						class="medium"
						src="${pageContext.request.contextPath }/${product.pimage}">
				</div>

				<div class="col-md-6">
					<div>
						<strong>${product.pname}</strong>
					</div>
					<div
						style="border-bottom: 1px dotted #dddddd; width: 350px; margin: 10px 0 10px 0;">
						<div>编号：${product.pid}</div>
					</div>

					<div style="margin: 10px 0 10px 0;">
						价值: <strong style="color: #ef0101;">￥：${product.shop_price}</strong>
						酬劳：￥${product.market_price}
					</div>

					<div style="margin: 10px 0 10px 0;">
						联系信息发布者:
						<a
							href="${pageContext.request.contextPath}/user?method=contactUser&uid=${product.uid}">获取信息发布者信息
<!-- 							 target="_blank" title="获取联系方式 " -->
<!-- 							style="background-color: #f07373;" -->
						</a>
					</div>

					<div
						style="padding: 10px; border: 1px solid #e7dbb1; width: 330px; margin: 15px 0 10px 0;; background-color: #fffee6;">
						
<!-- 						<div -->
<!-- 							style="border-bottom: 1px solid #faeac7; margin-top: 20px; padding-left: 10px;"> -->
<!-- 							购买数量: <input id="buyNum" name="buyNum" value="1" maxlength="4" -->
<!-- 								size="10" type="text"> -->
<!-- 						</div> -->

						<div style="margin: 20px 0 10px 0;; text-align: center;">
							<a href="javascript:void(0);" onclick="addCart()"> <input
								style="background: url('./images/product.gif') no-repeat scroll 0 -600px rgba(0, 0, 0, 0); height: 36px; width: 127px;"
								value="加入我的收藏" type="button">
							</a> &nbsp;收藏信息
						</div>
					</div>

					<!-- 返回列表页 -->
					<div>
						<a
							href="${pageContext.request.contextPath}/product?method=productList&cid=${cid }&currentPage=${currentPage}">返回列表页面</a>
					</div>


				</div>
			</div>
			<div class="clear"></div>
			<div style="width: 950px; margin: 0 auto;">
				<div
					style="background-color: #d3d3d3; width: 930px; padding: 10px 10px; margin: 10px 0 10px 0;">
					<strong>相关描述</strong>
				</div>

				<div>
					<img src="${pageContext.request.contextPath }/${product.pimage}">
				</div>
				<p>
					<font color="#FF0000">时间：&yen;${product.pdate }</font>
				</p>
				<p>
					<font color="#FF0000">价值：&yen;${product.shop_price }</font>
				</p>
				<p>
					<font color="#FF0000">酬劳：&yen;${product.market_price }</font>
				</p>
				<p>
					<font color="#FF0000">描述：&yen;${product.pdesc }</font>
				</p>

				<!-- 				<div -->
				<!-- 					style="background-color: #d3d3d3; width: 930px; padding: 10px 10px; margin: 10px 0 10px 0;"> -->
				<!-- 					<strong>商品参数</strong> -->
				<!-- 				</div> -->
				<!-- 				<div style="margin-top: 10px; width: 900px;"> -->
				<!-- 					<table class="table table-bordered"> -->
				<!-- 						<tbody> -->
				<!-- 							<tr class="active"> -->
				<!-- 								<th colspan="2">基本参数</th> -->
				<!-- 							</tr> -->
				<!-- 							<tr> -->
				<!-- 								<th width="10%">级别</th> -->
				<!-- 								<td width="30%">标准</td> -->
				<!-- 							</tr> -->
				<!-- 							<tr> -->
				<!-- 								<th width="10%">标重</th> -->
				<!-- 								<td>500</td> -->
				<!-- 							</tr> -->
				<!-- 							<tr> -->
				<!-- 								<th width="10%">浮动</th> -->
				<!-- 								<td>200</td> -->
				<!-- 							</tr> -->
				<!-- 						</tbody> -->
				<!-- 					</table> -->
				<!-- 				</div> -->

				<!-- 				<div style="background-color: #d3d3d3; width: 900px;"> -->
				<!-- 					<table class="table table-bordered"> -->
				<!-- 						<tbody> -->
				<!-- 							<tr class="active"> -->
				<!-- 								<th><strong>商品评论</strong></th> -->
				<!-- 							</tr> -->
				<!-- 							<tr class="warning"> -->
				<!-- 								<th>暂无商品评论信息 <a>[发表商品评论]</a></th> -->
				<!-- 							</tr> -->
				<!-- 						</tbody> -->
				<!-- 					</table> -->
				<!-- 				</div> -->

				<!-- 				<div style="background-color: #d3d3d3; width: 900px;"> -->
				<!-- 					<table class="table table-bordered"> -->
				<!-- 						<tbody> -->
				<!-- 							<tr class="active"> -->
				<!-- 								<th><strong>商品咨询</strong></th> -->
				<!-- 							</tr> -->
				<!-- 							<tr class="warning"> -->
				<!-- 								<th>暂无商品咨询信息 <a>[发表商品咨询]</a></th> -->
				<!-- 							</tr> -->
				<!-- 						</tbody> -->
				<!-- 					</table> -->
				<!-- 				</div> -->

			</div>

		</div>
	</div>


	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>