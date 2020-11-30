/**
 * Store's encrypted draw to a file
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
import java.sql.PreparedStatement;

@WebServlet("/AddUserNumbers")
public class AddUserNumbers extends HttpServlet {

    EncryptionStorage es = new EncryptionStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        // get numbers from choose numbers form and build into string
        StringBuilder sb = new StringBuilder();
        sb.append(request.getParameter("numbers0")).append(", ");
        sb.append(request.getParameter("numbers1")).append(", ");
        sb.append(request.getParameter("numbers2")).append(", ");
        sb.append(request.getParameter("numbers3")).append(", ");
        sb.append(request.getParameter("numbers4")).append(", ");
        sb.append(request.getParameter("numbers5"));


        // encrypt and write string to file
        try {
            es.bytesFileWriter("C:\\Users\\cglas\\ComputerScience\\" +
                    "Stage_2\\Security\\Assignment\\CSC2031 Coursework\\" +
                    "LotteryWebApp\\EncryptedFiles\\" + session.getAttribute("filename") + ".txt", es.encryptData(sb.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.setAttribute("es", es);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/public/account.jsp");
        session.setAttribute("numberstring", sb + " - added to draw!");
        session.setAttribute("winner", null);
        dispatcher.forward(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
