import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {

    private Connection conn;

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
        String pass = null;



        try {
            // create database connection and statement
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


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
                pass = CreateAccount.GeneratePassword(request.getParameter("password1"), salt);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            String stmt1 = "SELECT * FROM userAccounts WHERE Username=? AND Pwd=?";
            PreparedStatement ps = conn.prepareStatement(stmt1);


            ps.setString(1,user);
            ps.setString(2,pass);
            ResultSet rs1 = ps.executeQuery();

            if(rs1.next()){
                String firstname = rs1.getString("Firstname");
                String lastname = rs1.getString("Lastname");
                String email = rs1.getString("Email");
                String phone = rs1.getString("Phone");
                String username = rs1.getString("Username");

                session.setAttribute("firstname", firstname);
                session.setAttribute("lastname", lastname);
                session.setAttribute("email", email);
                session.setAttribute("telephone", phone);
                session.setAttribute("username", username);

                // display output.jsp page with given content above if successful
                RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
                session.setAttribute("message", "Successfully logged in!");
                session.setAttribute("numberstring", "");
                dispatcher.forward(request, response);
            }
            else{
                // display error.jsp page with given message if successful
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                session.setAttribute("message", "Please enter valid details!");
                dispatcher.forward(request, response);
            }

            // close connection
            conn.close();


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
