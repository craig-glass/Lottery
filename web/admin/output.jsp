<%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Output</title>
    <link href="static/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<h1>User Details</h1>

<div>
    <%= request.getAttribute("data") %>
</div>
<br>
<div>
    <a href="${pageContext.request.contextPath}/admin/admin_home.jsp">Go Back</a>
</div>

</body>
</html>
