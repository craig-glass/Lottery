<%--
  Created by IntelliJ IDEA.
  User: cglas
  Date: 11/5/2020
  Time: 5:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Home Page</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/DataTable">
        <input type="submit" value="Get Data">
    </form>
    <form action="${pageContext.request.contextPath}/GenerateWinningNumbers">
        <input type="submit" value="Generate Winning Numbers">
    </form>
    <p><%= session.getAttribute("winningnumbers") %></p>
</body>
</html>
