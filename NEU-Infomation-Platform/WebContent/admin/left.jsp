<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>菜单</title>
<link href="${pageContext.request.contextPath}/css/left.css" rel="stylesheet" type="text/css"/>
<link rel="StyleSheet" href="${pageContext.request.contextPath}/css/dtree.css" type="text/css" />
</head>
<body>
<table width="100" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="12"></td>
  </tr>
</table>
<table width="100%" border="0">
  <tr>
    <td>
<div class="dtree">

	<a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dtree.js"></script>
	<script type="text/javascript">
	
	 d = new dTree('d');
     d.add('01',-1,'系统菜单树');
     d.add('0102','01','功能模块管理','','','mainFrame');
     d.add('010201','0102','功能模块管理','${pageContext.request.contextPath}/admin?method=findCategoryList','','mainFrame');
     d.add('0104','01','信息管理');
     d.add('010401','0104','信息管理','${pageContext.request.contextPath}/admin?method=findProductList','','mainFrame');
     d.add('0105','01','发布管理');
     d.add('010501','0105','发布管理','${pageContext.request.contextPath}/admin?method=findOrderList','','mainFrame');
	document.write(d);
		
	</script>
</div>	</td>
  </tr>
</table>
</body>
</html>
