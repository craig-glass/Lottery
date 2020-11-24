<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Account</title>
    <link href="static/style.css" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/additional-methods.js"></script>
    <script src="static/jquery.js"></script>
</head>
<body>
<h1>User Account</h1>
<%
    if(request.getAttribute("message") != null){
%>
        <p style="color: darkgreen"><%= request.getAttribute("message") %></p>
<%
    }
%>
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

<form action="${pageContext.request.contextPath}/AddUserNumbers" method="post" name="choosenumbers">
    <label>Enter 6 numbers between 0 and 60:</label><br><br>
    <label for="numbers">No 1: </label>
    <input type="text" id="numbers" name="numbers"><br>
    <label for="numbers1">No 2: </label>
    <input type="text" id="numbers1" name="numbers1"><br>
    <label for="numbers2">No 3: </label>
    <input type="text" id="numbers2" name="numbers2"><br>
    <label for="numbers3">No 4: </label>
    <input type="text" id="numbers3" name="numbers3"><br>
    <label for="numbers4">No 5: </label>
    <input type="text" id="numbers4" name="numbers4"><br>
    <label for="numbers5">No 6: </label>
    <input type="text" id="numbers5" name="numbers5"><br><br>
    <input class="button" type="submit" value="Add To Draw">

</form>
<button class="button" onclick="generateNumbers()">Generate Numbers</button>
<% if(session.getAttribute("numberstring") != null){ %>
    <p style="color: darkgreen"><%= session.getAttribute("numberstring") %></p>
<% } %>

<form action="${pageContext.request.contextPath}/GetUserNumbers" method="post">
    <input class="button" type="submit" value="Get Draws">
</form>

<% String[] numSet = (String[]) session.getAttribute("set"); %>

<% if(request.getAttribute("warning") != null){ %>
    <p style="color: red"><%= request.getAttribute("warning") %></p>
<% } %>

<% if(numSet != null){ %>

<table style="color: darkblue">
    <% for(String nums : numSet){ %>
        <tr>
            <td>
                <%= nums %>
            </td>
        </tr>
    <%
        }
    %>
</table>
<%
    }
%>

<br>
<form action="${pageContext.request.contextPath}/CheckNumbers" method="post">
    <input class="button" type="submit" name="checkNumbers_btn" value="Check Numbers">
</form>
<%
    if(session.getAttribute("winner") != null){
%>
<p style="font-size: large; color: darkmagenta"><strong><%= session.getAttribute("winner") %></strong></p>
<%
    }
%>

<form action="${pageContext.request.contextPath}/LogOut">
    <input class="button" type="submit" value="Log Out">
</form><br>

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
