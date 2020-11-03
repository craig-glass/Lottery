import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;

@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {

    private Connection conn;
    private PreparedStatement stmt;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // MySql database connection info
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

        // get parameter data that was submitted in HTML form (use form attributes 'name')
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("telephone");
        String username = request.getParameter("username");
        String password = null;

        // add values to session
        session.setAttribute("firstname", firstname);
        session.setAttribute("lastname", lastname);
        session.setAttribute("email", email);
        session.setAttribute("telephone", phone);
        session.setAttribute("username", username);


        byte[] salt = getSalt();

        try {
            password = GeneratePassword(request.getParameter("password"),salt);
            session.setAttribute("password", password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }




        try{
            // create database connection and statement
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // Create sql query
            String query = "INSERT INTO userAccounts (Firstname, Lastname, Email, Phone, Username, Pwd, Salt)"
                    + " VALUES (?, ?, ?, ?, ?, ?,?)";


            // set values into SQL query statement
            stmt = conn.prepareStatement(query);
            stmt.setString(1,firstname);
            stmt.setString(2,lastname);
            stmt.setString(3,email);
            stmt.setString(4,phone);
            stmt.setString(5,username);
            stmt.setString(6,password);
            stmt.setBytes(7,salt);



            // execute query and close connection
            stmt.execute();
            conn.close();

            // display account.jsp page with given message if successful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
            request.setAttribute("message", firstname+", you have successfully created an account");
            request.setAttribute("numberstring", "");
            dispatcher.forward(request, response);

        } catch(Exception se){
            se.printStackTrace();
            // display error.jsp page with given message if unsuccessful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("message", firstname+", this username/password combination already exists. Please try again");
            dispatcher.forward(request, response);
        }
        finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }
            catch(SQLException se2){}
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }


    }

    // generate salt
    public static byte[] getSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    // generate hashed password
    public static String GeneratePassword(String password, byte[] salt) throws NoSuchAlgorithmException {


        String generatedPassword = null;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.reset();
            md.update(salt);

            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < hashedPassword.length; i++){
                sb.append(Integer.toString((hashedPassword[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
