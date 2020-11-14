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
    public String filename;
    public String file;

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


        System.out.println(session.getAttribute("password"));
        filename = (String) session.getAttribute("password");
        System.out.println(filename);
        file = filename.substring(0, 20);
        System.out.println(file);
        session.setAttribute("filename", file);

        try {
            es.bytesFileWriter("C:\\Users\\cglas\\ComputerScience\\" +
                    "Stage_2\\Security\\Assignment\\CSC2031 Coursework\\" +
                    "LotteryWebApp\\" + file + ".txt", es.encryptData(sb.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        session.setAttribute("es", es);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
        session.setAttribute("numberstring", sb);
        session.setAttribute("message", "");
        dispatcher.forward(request, response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
