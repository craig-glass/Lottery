/**
 * Creates a user account, stores details in the database,
 * and sets session attributes
 *
 * @author Craig Glass
 * @version 1.0
 * @since 2020-11-05
 */

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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


        // URL to connect to database
        String DB_URL = "jdbc:mysql://db:3306/lottery";


        // check register button has been pressed
        if(request.getParameter("register_btn") != null){
            // get parameter data that was submitted in HTML form (use form attributes 'name')
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String email = request.getParameter("email");
            String phone = request.getParameter("telephone");
            String username = request.getParameter("username");
            String role = request.getParameter("role_form");
            String password = null;

            // add values to session
            session.setAttribute("firstname", firstname);
            session.setAttribute("lastname", lastname);
            session.setAttribute("email", email);
            session.setAttribute("telephone", phone);
            session.setAttribute("username", username);
            session.setAttribute("role", role);

            byte[] salt = getSalt();

            // create hashed password
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
                String query = "INSERT INTO userAccounts (Firstname, Lastname, Email, Phone, Username, Pwd, UserRole, Salt)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


                // set values into SQL query statement
                stmt = conn.prepareStatement(query);
                stmt.setString(1,firstname);
                stmt.setString(2,lastname);
                stmt.setString(3,email);
                stmt.setString(4,phone);
                stmt.setString(5,username);
                stmt.setString(6,password);
                stmt.setString(7, role);
                stmt.setBytes(8,salt);




                // execute query and close connection
                stmt.execute();
                conn.close();

                // display account.jsp page with given message if successful
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                request.setAttribute("message", firstname+", you have successfully created an account");
                session.setAttribute("numberstring", "");
                dispatcher.forward(request, response);

            } catch(Exception se){
                se.printStackTrace();
                // display error.jsp page with given message if unsuccessful
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                request.setAttribute("message", "Error connecting to database, please try again");
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


    }

    /**
     * Create salt
     * @return byte[] 'salt'
     */
    public static byte[] getSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    /**
     *
     * @param password user password
     * @param salt generated salt
     * @return String 'generatedPassword' (hashed password)
     * @throws NoSuchAlgorithmException
     */
    public static String GeneratePassword(String password, byte[] salt) throws NoSuchAlgorithmException {


        String generatedPassword = null;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
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
