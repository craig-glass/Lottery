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


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        EncryptionStorage es = (EncryptionStorage) session.getAttribute("es");

        ArrayList<byte[]> data = new ArrayList<>();

        System.out.println("Arraylist data 'getUserNumbers: " + data);
        try {
            data = es.splitData(es.bytesFileReader(
                    "C:\\Users\\cglas\\ComputerScience\\Stage_2\\" +
                            "Security\\Assignment\\CSC2031 Coursework\\" +
                            "LotteryWebApp\\" + session.getAttribute("filename") + ".txt"));
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

        RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
