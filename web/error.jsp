<%--
 * Displays error messages
 *
 * @author Craig Glass
 * @version 1.0
 * @since 2020-11-05
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link href="static/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<header>
    <h1>Error Page</h1>
</header>
<div class="container">


    <% if(request.getAttribute("message") != null){
    %>
    <p style="color: red;"><%= request.getAttribute("message") %></p>
    <%
        }
    %>

    <a href="index.jsp">Back To Login</a>
</div>

<script type="text/javascript">

</script>
</body>
</html>
