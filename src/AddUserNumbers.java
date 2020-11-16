import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.PreparedStatement;

@WebServlet("/AddUserNumbers")
public class AddUserNumbers extends HttpServlet {

    EncryptionStorage es = new EncryptionStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        // get parameter data from html form
        StringBuilder sb = new StringBuilder();
        sb.append("'").append(request.getParameter("numbers")).append(", ");
        sb.append(request.getParameter("numbers1")).append(", ");
        sb.append(request.getParameter("numbers2")).append(", ");
        sb.append(request.getParameter("numbers3")).append(", ");
        sb.append(request.getParameter("numbers4")).append(", ");
        sb.append(request.getParameter("numbers5")).append("'");


        try {
            es.bytesFileWriter("C:\\Users\\cglas\\ComputerScience\\" +
                    "Stage_2\\Security\\Assignment\\CSC2031 Coursework\\" +
                    "LotteryWebApp\\" + session.getAttribute("filename") + ".txt", es.encryptData(sb.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.setAttribute("es", es);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
        session.setAttribute("numberstring", sb);
        request.setAttribute("message", "Numbers added to draw!");
        dispatcher.forward(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
