<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Account</title>
    <link href="${pageContext.request.contextPath}/static/style.css" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.1/jquery.validate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.19.2/additional-methods.js"></script>
    <script src="static/jquery.js"></script>
</head>
<body>
<header>
    <h1>User Account</h1>
    <a href="${pageContext.request.contextPath}/LogOut">Log Out</a>
</header>
<div class="container">
    <div class="flexContent">
        <div id="userDetails">
            <table class="tableStyle rowStyle">
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
        </div>
        <div class="message">
            <%
                if(request.getAttribute("message") != null){
            %>
            <p class="green"><%= request.getAttribute("message") %></p>
            <%
                }
            %>
        </div>
    </div>



    <br>
    <div id="chooseNumbers">
        <form action="${pageContext.request.contextPath}/AddUserNumbers" method="post" name="choosenumbers">
            <label>Enter 6 numbers between 0 and 60:</label><br><br>
            <label for="numbers0">No 1: </label>
            <input type="text" id="numbers0" name="numbers0"><br>
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
            <button class="button" type="button" onclick="generateNumbers()">Generate Numbers</button>
            <% if(session.getAttribute("numberstring") != null){ %>
            <p class="green"><%= session.getAttribute("numberstring") %></p>
            <% } %>
        </form>
    </div>
    <div class="flexContentCheckNums">
        <div id="getDraws">
            <form action="${pageContext.request.contextPath}/GetUserNumbers" method="post">
                <input class="button" type="submit" value="Get Draws">
            </form>
            <% String[] numSet = (String[]) session.getAttribute("set"); %>

            <% if(request.getAttribute("warning") != null){ %>
            <p style="color: red"><%= request.getAttribute("warning") %></p>
            <% } %>

            <% if(numSet != null){ %>

            <table style="color: blue">
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

        </div>
        <div id="checkNumLogout">
            <div id="checkNumbers">
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
            </div>
        </div>

    </div>

</div>



<script>
    function generateNumbers(){
        let array = new Uint8Array(6);
        window.crypto.getRandomValues(array);
        for (let i = 0; i < array.length; i++){
            document.getElementById("numbers" + i)
                .value = (Math.floor((array[i]/ 255) * 60)).toString();
        }
    }
</script>
</body>
</html>
