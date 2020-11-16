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
        String DB_URL = "jdbc:mysql://localhost:33333/lottery";

        if(request.getParameter("checkNumbers_btn") != null){
            try{
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL, USER, PASS);

                String query = "SELECT * FROM winningNumbers";

                stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                String winningNumber;

                if(rs.next()){
                    winningNumber = rs.getString("WinningNumber");
                    System.out.println(winningNumber);

                    boolean noWin = true;
                    String[] numbers = (String[]) session.getAttribute("set");
                    for(String number : numbers){
                        System.out.println(number);
                        if(number.equals(winningNumber)){
                            noWin = false;
                            session.setAttribute("winner", "Winner!");
                            response.sendRedirect(request.getContextPath() + "/account.jsp");
                        }

                    }
                    if(noWin){
                        session.setAttribute("winner", "Sorry no winning numbers this time!");
                        response.sendRedirect(request.getContextPath() + "/account.jsp");
                    }

                }

                conn.close();
                stmt.close();





            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
