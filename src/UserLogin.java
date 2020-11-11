import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;


@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {

    private Connection conn;
    private int attempts = 0;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "user";
        String PASS = "password";
        HttpSession session = request.getSession();


        // URLs to connect to database depending on your development approach
        // (NOTE: please change to option 1 when submitting)

        // 1. use this when running everything in Docker using docker-compose
        // String DB_URL = "jdbc:mysql://db:3306/lottery";

        // 2. use this when running tomcat server locally on your machine and mysql database server in Docker
        String DB_URL = "jdbc:mysql://localhost:33333/lottery";

        // 3. use this when running tomcat and mysql database servers on your machine
        //String DB_URL = "jdbc:mysql://localhost:3306/lottery";




            String user = request.getParameter("username1");
            String pass = request.getParameter("password1");
            String role = request.getParameter("role_form");

            try {
                // create database connection and statement
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);



                // check username exists
                String stmtUser = "SELECT * FROM userAccounts WHERE Username=?";
                PreparedStatement stUser = conn.prepareStatement(stmtUser);
                stUser.setString(1,user);
                ResultSet rsUser = stUser.executeQuery();

                if(rsUser.next()){
                    // get salt from database


                    String stmt = "SELECT Salt FROM userAccounts WHERE Username=?";
                    PreparedStatement st = conn.prepareStatement(stmt);


                    st.setString(1,user);
                    ResultSet rs = st.executeQuery();


                    byte[] salt = null;
                    if(rs.next()){
                        salt = rs.getBytes("Salt");
                    }

                    try {
                        pass = CreateAccount.GeneratePassword(pass, salt);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    session.setAttribute("pass", pass);

                    String stmt1 = "SELECT * FROM userAccounts WHERE Username=? AND Pwd=? AND UserRole=?";
                    PreparedStatement ps = conn.prepareStatement(stmt1);


                    ps.setString(1,user);
                    ps.setString(2,pass);
                    ps.setString(3, role);
                    ResultSet rs1 = ps.executeQuery();


                    if(rs1.next()){
                        String dbfirstname = rs1.getString("Firstname");
                        String dblastname = rs1.getString("Lastname");
                        String dbemail = rs1.getString("Email");
                        String dbphone = rs1.getString("Phone");
                        String dbusername = rs1.getString("Username");
                        String dbpassword = rs1.getString("Pwd");
                        String dbrole = rs1.getString("UserRole");

                        if(user.equals(dbusername) && pass.equals(dbpassword) &&
                                role.equals(dbrole)){
                            session.setAttribute("firstname", dbfirstname);
                            session.setAttribute("lastname", dblastname);
                            session.setAttribute("email", dbemail);
                            session.setAttribute("telephone", dbphone);
                            session.setAttribute("username", dbusername);

                            if(dbrole.equals("admin")){
                                RequestDispatcher dispatcher = request.getRequestDispatcher("" +
                                        "/admin/admin_home.jsp");
                                session.setAttribute("admin_login", dbusername);
                                dispatcher.forward(request, response);
                            }else if(dbrole.equals("public")){
                                RequestDispatcher dispatcher = request.getRequestDispatcher("" +
                                        "/account.jsp");
                                session.setAttribute("message", "Successfully logged in!");
                                session.setAttribute("numberstring", "");
                                session.setAttribute("public_login", dbusername);
                                dispatcher.forward(request, response);
                            }
                        }

                    }
                    else{

                        // display error.jsp page with given message if successful
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                        session.setAttribute("message", "Please enter valid details!");
                        dispatcher.forward(request, response);

                    }

                    // close connection
                    conn.close();
                } else{

                    attempts += 1;
                    if(attempts == 3){
                        session.setAttribute("attempts", attempts);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                        dispatcher.forward(request, response);
                    }else{
                        // display error.jsp page with given message if successful
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                        session.setAttribute("message", "Please enter valid details!");
                        dispatcher.forward(request, response);
                    }


                }



            } catch (Exception se) {
                se.printStackTrace();
                // display error.jsp page with given message if successful
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                request.setAttribute("message", "Database Error, Please try again");
                dispatcher.forward(request, response);
            } finally {
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }



    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
