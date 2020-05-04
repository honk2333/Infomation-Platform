<%--
  Created by IntelliJ IDEA.
  User: 王杰
  Date: 2018/6/1
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        response.sendRedirect(request.getContextPath()+"/product?method=index");
    %>

</body>
</html>
