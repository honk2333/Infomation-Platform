<%@ page language="java" pageEncoding="UTF-8"%>
<HTML>
<HEAD>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath}/css/Style1.css"
	type="text/css" rel="stylesheet">

<%--要加上${pageContext.request.contextPath，ajax不能用 }--%>
<script
	src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$
				.ajax({
					url : "${pageContext.request.contextPath}/product?method=categoryList",
					type : "get",
					success : function(date) {
<%--// {method:"findAllCategoryByAjax"},--%>
	
<%--//<option value=""></option>--%>
	
<%--//[{},{},{}]--%>
	var content = "";
						for (var i = 0; i < date.length; i++) {
							content += "<option value='"+date[i].cid+"'>"
									+ date[i].cname + "</option>";
						}
						$("#selectCategory").html(content);
					},
					dataType : "json"
				});
	});
</script>
</HEAD>

<body>
	<!--  -->
	<form id="Form1" name="Form1"
		action="${pageContext.request.contextPath}/adminaddproduct"
		method="post" enctype="multipart/form-data">
		&nbsp;
		<table cellSpacing="1" cellPadding="5" width="100%" align="center"
			bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
			<tr>
				<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
					height="26"><strong>添加商品</strong></td>
			</tr>

			<tr>
				<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
					用户编号：</td>
				<td class="ta_01" bgColor="#ffffff"><input type="text"
					name="uid" value="${sessionScope.user.getUid()}"  id="uid" class="bg" /></td>
			</tr>

			<tr>
				<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
					名称：</td>
				<td class="ta_01" bgColor="#ffffff"><input type="text"
					name="pname" value="" id="pname" class="bg" /></td>

				<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
					是否紧急：</td>
				<td class="ta_01" bgColor="#ffffff"><select name="is_hot">
						<option value="1">是</option>
						<option value="0">否</option>
				</select></td>
			</tr>
			<tr>
				<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
					酬金：</td>
				<td class="ta_01" bgColor="#ffffff"><input type="text"
					name="market_price" value="" id="market_price" class="bg" /></td>
				<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
					涉及物品价格：</td>
				<td class="ta_01" bgColor="#ffffff"><input type="text"
					name="shop_price" value="" id="shop_price" class="bg" /></td>
			</tr>
			<tr>
				<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
					图片：</td>
				<td class="ta_01" bgColor="#ffffff" colspan="3"><input
					type="file" name="upload" /></td>
			</tr>
			<tr>
				<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
					所属分类：</td>
				<td class="ta_01" bgColor="#ffffff" colspan="3"><select
					id="selectCategory" name="cid">

				</select></td>
			</tr>
			<tr>
				<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
					描述：</td>
				<td class="ta_01" bgColor="#ffffff" colspan="3"><textarea
						name="pdesc" rows="5" cols="30"></textarea></td>
			</tr>

			<tr>
				<td class="ta_01" style="WIDTH: 100%" align="center"
					bgColor="#f5fafe" colSpan="4">
					<button  type="submit" id="submitButton" value="确定"
						class="button_ok">&#30830;&#23450;</button> 
						<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
					<button type="reset" value="重置" class="button_cancel">&#37325;&#32622;</button>

					<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT> <INPUT
					class="button_ok" type="button" onclick="history.go(-1)" value="返回" />
					<span id="Label1"></span>
				</td>
			</tr>
		</table>
	</form>
</body>
</HTML>