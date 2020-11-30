/**
 * Generates a random draw and updates the winning numbers in
 * the database
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;

@WebServlet("/GenerateWinningNumbers")
public class GenerateWinningNumbers extends HttpServlet {

    private Connection conn;
    private PreparedStatement stmt;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // MySql database connection info
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "user";
        String PASS = "password";
        String DB_URL = "jdbc:mysql://db:3306/lottery";

        HttpSession session = request.getSession();
        Random rand = new Random();

        // generates 6 random numbers
        String winningNumbers = rand.nextInt(61) + ", " +
                                rand.nextInt(61) + ", " +
                                rand.nextInt(61) + ", " +
                                rand.nextInt(61) + ", " +
                                rand.nextInt(61) + ", " +
                                rand.nextInt(61);

        try{
            //create database connection
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // create sql query
            String query = "UPDATE winningNumbers SET Winningnumber="
                    + " (?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, winningNumbers);

            // execute query and close connection
            stmt.executeUpdate();
            conn.close();

            // set winning numbers and set it a message
            session.setAttribute("winningnumbers", winningNumbers);
            request.setAttribute("message", "Winning Number Created!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_home.jsp");
            dispatcher.forward(request, response);
        }
        catch(Exception se){
            se.printStackTrace();

            // redirect to error page
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            session.setAttribute("message", "Database error!");
            dispatcher.forward(request, response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
