<%--
  Created by IntelliJ IDEA.
  User: cglas
  Date: 11/5/2020
  Time: 5:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Admin Home Page</title>
    <link href="${pageContext.request.contextPath}/static/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <h1>Admin Home Page</h1>

    <% if(request.getAttribute("message") != null){ %>
        <p class="green"><%= request.getAttribute("message") %></p>
    <% } %>

    <table>
        <tr>
            <td>Name: </td>
            <td><%= session.getAttribute("firstname") + " " + session.getAttribute("lastname") %></td>
        </tr>
        <tr>
            <td>Email: </td>
            <td><%= session.getAttribute("email") %></td>
        </tr>
        <tr>
            <td>Phone: </td>
            <td><%= session.getAttribute("telephone") %></td>
        </tr>
        <tr>
            <td>User Name: </td>
            <td><%= session.getAttribute("username") %></td>
        </tr>
    </table>
    <br>

    <form action="${pageContext.request.contextPath}/DataTable">
        <input class="button" type="submit" value="Get Data">
    </form>
    <form action="${pageContext.request.contextPath}/GenerateWinningNumbers">
        <input class="button" type="submit" value="Generate Winning Numbers">
    </form>
    <p><%= session.getAttribute("winningnumbers") %></p>
<a href="${pageContext.request.contextPath}/index.jsp">Back To Login Page</a>
</body>
</html>
