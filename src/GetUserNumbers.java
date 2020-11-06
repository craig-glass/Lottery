import javax.crypto.Cipher;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.util.*;


@WebServlet("/GetUserNumbers")
public class GetUserNumbers extends HttpServlet {

    public static Set<String> strings = new HashSet<>();

    public static Set<String> getStrings(){
        return strings;
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        EncryptionStorage es = (EncryptionStorage) session.getAttribute("es");

        String data = es.decryptData(es.bytesFileReader(
                "C:\\Users\\cglas\\ComputerScience\\Stage_2\\" +
                        "Security\\Assignment\\CSC2031 Coursework\\" +
                        "LotteryWebApp\\" + session.getAttribute("filename") + ".txt"));


        strings.add(data);
        session.setAttribute("set", strings);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
