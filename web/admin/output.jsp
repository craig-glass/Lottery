<%--
 * User's data displayed in a table
 *
 * @author Craig Glass
 * @version 1.0
 * @since 2020-11-05
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Output</title>
    <link href="${pageContext.request.contextPath}/static/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <h1>User Details</h1>
    <a href="${pageContext.request.contextPath}/LogOut">Log Out</a>
</header>
<div class="container">

    <div>
        <%= request.getAttribute("data") %>
    </div>

    <div id="dataTableBackLink">
        <a href="${pageContext.request.contextPath}/admin/admin_home.jsp">Go Back</a>
    </div>

</div>

</body>
</html>
