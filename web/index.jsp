<%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Home</title>
  </head>
  <body>

  <h1>Home Page</h1>
  <h2>Registration Form</h2>
  <form action="CreateAccount" method="post">
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
      <input type="submit" value="Submit">
  </form>
  <br>
  <h2>Login Form</h2>
  <form action="UserLogin" method="post">
      <label for="username1">Username:</label><br>
      <input type="text" id="username1" name="username1"><br><br>
      <label for="password1">Password:</label><br>
      <input type="password" id="password1" name="password1">
      <input type="submit" value="login">
  </form>

  </body>
</html>