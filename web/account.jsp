<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.reflect.Array" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %><%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>
    <link href="style.css" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/additional-methods.js"></script>
    <script src="jquery.js"></script>
</head>
<body>
<h1>User Account</h1>

<p><%= session.getAttribute("message") %></p>
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

<form action="AddUserNumbers" method="post" name="choosenumbers">
    <label for="numbers">Enter 6 numbers between 0 and 60:</label><br><br>
    <input type="text" id="numbers" name="numbers"><br>
    <input type="text" id="numbers1" name="numbers1"><br>
    <input type="text" id="numbers2" name="numbers2"><br>
    <input type="text" id="numbers3" name="numbers3"><br>
    <input type="text" id="numbers4" name="numbers4"><br>
    <input type="text" id="numbers5" name="numbers5"><br>
    <input type="submit" value="Confirm">

</form>
<button onclick="generateNumbers()">Generate Numbers</button>
    <p><%= session.getAttribute("numberstring") %></p>



<form action="GetUserNumbers" method="post">
    <input type="submit" value="Get Draws">
</form>


<% Set<String> numSet = (HashSet<String>) session.getAttribute("set");
if(numSet != null){
    for(String num : numSet){
%>
<tr>
    <td>
        <%= num %>
    </td>
</tr></br>
<%
    }
%>
<%
}
%>


<a href="index.jsp">Home Page</a>

<script>
    function generateNumbers(){
        document.getElementById("numbers")
            .value = (Math.floor(Math.random() * 60)).toString();
        document.getElementById("numbers1")
            .value = (Math.floor(Math.random() * 60)).toString();
        document.getElementById("numbers2")
            .value = (Math.floor(Math.random() * 60)).toString();
        document.getElementById("numbers3")
            .value = (Math.floor(Math.random() * 60)).toString();
        document.getElementById("numbers4")
            .value = (Math.floor(Math.random() * 60)).toString();
        document.getElementById("numbers5")
            .value = (Math.floor(Math.random() * 60)).toString();
    }
</script>
</body>
</html>
