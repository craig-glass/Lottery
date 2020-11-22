import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/LogOut")
public class LogOut extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();


        session.removeAttribute("firstname");
        session.removeAttribute("lastname");
        session.removeAttribute("email");
        session.removeAttribute("username");
        session.removeAttribute("password");
        session.removeAttribute("set");
        session.removeAttribute("pass");
        session.removeAttribute("role");
        session.removeAttribute("telephone");
        session.removeAttribute("public_login");
        session.removeAttribute("filename");
        session.removeAttribute("winner");
        session.removeAttribute("numberstring");




        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
