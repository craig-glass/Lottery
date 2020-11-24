import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


@WebServlet("/GetUserNumbers")
public class GetUserNumbers extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        EncryptionStorage es = (EncryptionStorage) session.getAttribute("es");


        System.out.println("Encryption Storage = " + es);
        ArrayList<byte[]> data = new ArrayList<>();

        System.out.println("Arraylist data 'getUserNumbers: " + data);
        try {
            data = es.splitData(es.bytesFileReader(
                    "C:\\Users\\cglas\\ComputerScience\\Stage_2\\" +
                            "Security\\Assignment\\CSC2031 Coursework\\" +
                            "LotteryWebApp\\EncryptedFiles\\" + session.getAttribute("filename") + ".txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("Filename = " + session.getAttribute("filename"));
        String[] decryptedData = new String[data.size()];
        System.out.println("Decrypted data array 'getUserNumbers': " + Arrays.toString(decryptedData));
        try{
            decryptedData = es.decryptData(data);
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println("Decrypted data array 'getUserNumbers': "
                + Arrays.toString(decryptedData));
        session.setAttribute("set", decryptedData);

        if(decryptedData.length == 0){
            request.setAttribute("warning", "No numbers added to draw!");
            session.setAttribute("winner", null);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/public/account.jsp");
            dispatcher.forward(request, response);
        }else{
            session.setAttribute("numberstring", "");
            session.setAttribute("winner", null);
            request.setAttribute("message", "");
            request.setAttribute("warning", null);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/public/account.jsp");
            dispatcher.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
