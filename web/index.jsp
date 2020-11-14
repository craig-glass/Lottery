<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Home</title>
    <link href="style.css" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/additional-methods.js"></script>
    <script src="jquery.js"></script>
  </head>
  <body>
<%
    String attempts = null;
    Cookie[] cookies = request.getCookies();
    if(cookies != null){
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("attempts")){
                attempts = cookie.getValue();
            }
        }
    }
%>
  <h1>Home Page</h1>

  <% if(session.getAttribute("message") != null){
  %>
        <p><%= session.getAttribute("message") %></p>
  <%
      }
  %>

  <h2>Registration Form</h2>
  <form action="CreateAccount" method="post" class="error" name="createaccount">
      <label for="firstname">First name:</label><br>
      <input type="text" id="firstname" name="firstname"><br>
      <label for="lastname">Last name:</label><br>
      <input type="text" id="lastname" name="lastname"><br>
      <label for="email">Email:</label><br>
      <input type="email" id="email" name="email"><br>
      <label for="telephone">Phone Number:</label><br>
      <input type="text" id="telephone" name="telephone"><br>
      <label for="username">Username:</label><br>
      <input type="text" id="username" name="username"><br><br>
      <label for="password">Password:</label><br>
      <input type="password" id="password" name="password">

      <br>
      <select name="role_form">
          <option value="" selected="selected"> - Select Role - </option>
          <option value="admin">Admin</option>
          <option value="public">Public</option>
      </select>
      <input type="submit" name="register_btn" value="Submit">
  </form>
  <br>
  <h2>Login Form</h2>
  <form action="UserLogin" method="post" name="login" onsubmit="check_form()">
      <label for="username1">Username:</label><br>
      <input type="text" id="username1" name="username1"><br><br>
      <label for="password1">Password:</label><br>
      <input type="password" id="password1" name="password1">

      <br>
      <select name="role_form">
          <option value="" selected="selected"> - Select Role - </option>
          <option value="admin">Admin</option>
          <option value="public">Public</option>
      </select>

      <input type="submit" name="login_btn" value="login">
  </form>


  <script type="text/javascript">
      if('<%= attempts %>' === "3"){
          document.getElementById("username1").disabled=true;
          document.getElementById("password1").disabled=true;
          alert("Login disabled!");
      }
  </script>
  </body>
</html>
