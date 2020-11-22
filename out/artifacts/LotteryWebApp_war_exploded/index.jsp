<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Home</title>
    <link href="static/style.css" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/additional-methods.js"></script>
    <script src="jquery.js"></script>
  </head>
  <body>
<%
    String attempts = null;
    boolean unsuccessful = false;
    Cookie[] cookies = request.getCookies();
    if(cookies != null){
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("attempts")){
                attempts = cookie.getValue();
            }
            if(cookie.getName().equals("unsuccessful")){
                unsuccessful = true;
            }
            if(unsuccessful){
                if(cookie.getName().equals("JSESSIONID")){
                    cookie.setValue(null);
                }
            }
        }
    }

%>
  <h1>Home Page</h1>

  <% if(request.getAttribute("message") != null){
  %>
        <p><%= request.getAttribute("message") %></p>
  <%
      }
  %>

<div id="container">
    <div id="registration">
        <h2>Registration Form</h2>
        <form action="CreateAccount" method="post" class="error" name="createaccount">
            <table>
                <tr>
                    <td>
                        <label for="firstname">First name:</label>
                    </td>
                    <td>
                        <input type="text" id="firstname" name="firstname">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="lastname">Last name:</label>
                    </td>
                    <td>
                        <input type="text" id="lastname" name="lastname">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="email">Email:</label>
                    </td>
                    <td>
                        <input type="email" id="email" name="email">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="telephone">Phone Number:</label>
                    </td>
                    <td>
                        <input type="text" id="telephone" name="telephone">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="username">Username:</label>
                    </td>
                    <td>
                        <input type="text" id="username" name="username">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="password">Password:</label>
                    </td>
                    <td>
                        <input type="password" id="password" name="password">
                    </td>
                </tr>
                <tr>
                    <td>
                        <select name="role_form">
                            <option value="" selected="selected"> - Select Role - </option>
                            <option value="admin">Admin</option>
                            <option value="public">Public</option>
                        </select>
                    </td>
                    <td>
                        <input class="button" type="submit" name="register_btn" value="Submit">
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div id="login">
        <h2>Login Form</h2>
        <form action="UserLogin" method="post" name="login" onsubmit="check_form()">
            <table>
                <tr>
                    <td>
                        <label for="username1">Username:</label>
                    </td>
                    <td>
                        <input type="text" id="username1" name="username1">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label for="password1">Password:</label>
                    </td>
                    <td>
                        <input type="password" id="password1" name="password1">
                    </td>
                </tr>
                <tr>
                    <td>
                        <select name="role_form">
                            <option value="" selected="selected"> - Select Role - </option>
                            <option value="admin">Admin</option>
                            <option value="public">Public</option>
                        </select>
                    </td>
                    <td>
                        <input class="button" type="submit" name="login_btn" value="login">
                    </td>
                </tr>
            </table>

        </form>
    </div>
</div>




  <script type="text/javascript">
      if('<%= attempts %>' === "3"){
          document.getElementById("username1").disabled=true;
          document.getElementById("password1").disabled=true;
          alert("Login disabled!");
      }
  </script>
  </body>
</html>
