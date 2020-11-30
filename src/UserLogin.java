/**
 * Checks username and password for a match in the database,
 * and logs user in if match
 *
 * @author Craig Glass
 * @version 1.0
 * @since 2020-11-05
 */

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {

    private Connection conn;
    private int attempts = 0;
    public String filename;
    public String file;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "user";
        String PASS = "password";
        HttpSession session = request.getSession();


        // URL to connect to database
         String DB_URL = "jdbc:mysql://db:3306/lottery";


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

                    // generate hashed password
                    try {
                        pass = CreateAccount.GeneratePassword(pass, salt);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                    session.setAttribute("pass", pass);

                    // check match for username, password and role
                    String stmt1 = "SELECT * FROM userAccounts WHERE Username=? AND Pwd=? AND UserRole=?";
                    PreparedStatement ps = conn.prepareStatement(stmt1);


                    ps.setString(1,user);
                    ps.setString(2,pass);
                    ps.setString(3, role);
                    ResultSet rs1 = ps.executeQuery();


                    // if match then retrieve data from database
                    if(rs1.next()){
                        String dbfirstname = rs1.getString("Firstname");
                        String dblastname = rs1.getString("Lastname");
                        String dbemail = rs1.getString("Email");
                        String dbphone = rs1.getString("Phone");
                        String dbusername = rs1.getString("Username");
                        String dbpassword = rs1.getString("Pwd");
                        String dbrole = rs1.getString("UserRole");

                        // if match then set details as attributes
                        if(user.equals(dbusername) && pass.equals(dbpassword) &&
                                role.equals(dbrole)){
                            
                            session.setAttribute("firstname", dbfirstname);
                            session.setAttribute("lastname", dblastname);
                            session.setAttribute("email", dbemail);
                            session.setAttribute("telephone", dbphone);
                            session.setAttribute("username", dbusername);

                            // if role is admin log in to admin hom page
                            if(dbrole.equals("admin")){
                                RequestDispatcher dispatcher = request.getRequestDispatcher("" +
                                        "/admin/admin_home.jsp");
                                session.setAttribute("admin_login", dbusername);
                                session.setAttribute("role", "admin");
                                session.setAttribute("winningnumbers", "");
                                request.setAttribute("message", "Successfully logged in!");
                                dispatcher.forward(request, response);
                            }
                            // if role is public log in to accounts page
                            else if(dbrole.equals("public")){
                                RequestDispatcher dispatcher = request.getRequestDispatcher("" +
                                        "/public/account.jsp");
                                request.setAttribute("message", "Successfully logged in!");
                                session.setAttribute("numberstring", "");
                                session.setAttribute("role", "public");
                                session.setAttribute("public_login", dbusername);
                                session.setAttribute("password", dbpassword);
                                filename = (String) session.getAttribute("password");
                                file = filename.substring(0, 20);
                                session.setAttribute("filename", file);
                                dispatcher.forward(request, response);
                            }
                        }else{
                            // if log in fails 3 times reset attempts
                            if(attempts > 2){
                                attempts = 0;
                            }
                            // if log in fails increment attempts
                            attempts += 1;
                            System.out.println("Attempts = " + attempts);

                            // if log in fails 3 times the set cookie and go to error page
                            if(attempts == 3){
                                Cookie attempts = new Cookie("attempts", "3");
                                response.addCookie(attempts);
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                                request.setAttribute("message", "No attempts left!!!");
                                dispatcher.forward(request, response);

                            }

                            else{

                                // display error.jsp page with given message if log in fails
                                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                                request.setAttribute("message", "Please enter valid details! " + (3 - attempts) + " Attempts Left!");
                                dispatcher.forward(request, response);
                            }
                        }

                    }
                    else{

                        if(attempts > 2){
                            attempts = 0;
                        }
                        attempts += 1;
                        System.out.println("Attempts = " + attempts);
                        if(attempts == 3){
                            Cookie attempts = new Cookie("attempts", "3");
                            response.addCookie(attempts);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                            request.setAttribute("message", "No attempts left!!!");
                            dispatcher.forward(request, response);

                        }

                        else{
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                            request.setAttribute("message", "Please enter valid details! " + (3 - attempts) + " Attempts Left!");
                            dispatcher.forward(request, response);
                        }

                    }

                    // close connection
                    conn.close();
                } else{

                    if(attempts > 2){
                        attempts = 0;
                    }
                    attempts += 1;
                    System.out.println("Attempts = " + attempts);
                    if(attempts == 3){
                        Cookie attempts = new Cookie("attempts", "3");
                        response.addCookie(attempts);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                        request.setAttribute("message", "No attempts left!!!");
                        dispatcher.forward(request, response);
                    }

                    else{

                        Cookie unsuccessful = new Cookie("unsuccessful", "true");
                        response.addCookie(unsuccessful);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                        request.setAttribute("message", "Please enter valid details! " + (3 - attempts) + " Attempts Left!");
                        dispatcher.forward(request, response);
                    }


                }



            } catch (Exception se) {
                se.printStackTrace();
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                request.setAttribute("message", "Error connecting to database, please try again");
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
