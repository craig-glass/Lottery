<%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link href="static/style.css" rel="stylesheet" type="text/css">
</head>
<body>
   <h1>Error Page</h1>

   <% if(request.getAttribute("message") != null){
   %>
   <p style="color: red;"><%= request.getAttribute("message") %></p>
   <%
       }
   %>

   <a href="index.jsp">Back To Login</a>
<script type="text/javascript">

</script>
</body>
</html>
