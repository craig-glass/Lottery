/**
 * Checks user's draws against winning number in database
 * and displays result
 *
 * @author Craig Glass
 * @version 1.0
 * @since 2020-11-05
 */

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/CheckNumbers")
public class CheckNumbers extends HttpServlet {
    private Connection conn;
    private PreparedStatement stmt;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // MySql database connection info
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "user";
        String PASS = "password";
        HttpSession session = request.getSession();
        String DB_URL = "jdbc:mysql://db:3306/lottery";


        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String query = "SELECT * FROM winningNumbers";

            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            String winningNumber;

            // check winning number in database against user's draws
            if(rs.next()){
                winningNumber = rs.getString("WinningNumber");
                System.out.println(winningNumber);

                boolean noWin = true;
                String[] numbers = (String[]) session.getAttribute("set");
                if(numbers != null){
                    for(String number : numbers){
                        System.out.println(number);
                        if(number.equals(winningNumber)){
                            noWin = false;
                            session.setAttribute("winner", "Winner! " + number + "!");


                            File dir = new File("C:\\Users\\cglas\\ComputerScience\\Stage_2\\" +
                                    "Security\\Assignment\\CSC2031 Coursework\\" +
                                    "LotteryWebApp\\EncryptedFiles");

                            if(dir.exists()){
                                for(File file : dir.listFiles()){
                                    if(!file.isDirectory()){
                                        file.delete();
                                    }

                                }
                            }

                            conn.close();
                            stmt.close();

                            RequestDispatcher dispatcher = request.getRequestDispatcher("/public/account.jsp");
                            dispatcher.forward(request, response);
                        }

                    }
                    if(noWin){
                        conn.close();
                        stmt.close();

                        session.setAttribute("winner", "Sorry no winning numbers this time!");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/public/account.jsp");
                        dispatcher.forward(request, response);
                    }
                }else{
                    conn.close();
                    stmt.close();

                    session.setAttribute("winner", "Please add numbers to draw!");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/public/account.jsp");
                    dispatcher.forward(request, response);
                }


            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
