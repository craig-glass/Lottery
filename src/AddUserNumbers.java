import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AddUserNumbers")
public class AddUserNumbers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // get parameter data from html form
        StringBuilder sb = new StringBuilder();
        sb.append("'" + request.getParameter("numbers") + ", ");
        sb.append(request.getParameter("numbers1") + ", ");
        sb.append(request.getParameter("numbers2") + ", ");
        sb.append(request.getParameter("numbers3") + ", ");
        sb.append(request.getParameter("numbers4") + ", ");
        sb.append(request.getParameter("numbers5") + "'");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
        request.setAttribute("numberstring", sb);
        request.setAttribute("message", "");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
