import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@WebFilter(filterName = "ServerFilter")
public class ServerFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        boolean invalid = false;
        Map params = req.getParameterMap();

        if (params != null){
            Iterator iter = params.keySet().iterator();
            while(iter.hasNext()){
                String key = (String) iter.next();
                String[] values = (String[]) params.get(key);

                for (String value : values) {
                    if (checkChars(value)) {
                        invalid = true;
                        break;
                    }

                }
                if(invalid){
                    break;
                }
            }

        }
        if(invalid){
            try{
                req.setAttribute("message", "Error! Suspicious characters are being used!");
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        else{
            chain.doFilter(req, resp);
        }
    }

    public static boolean checkChars(String value){
        boolean invalid = false;
        String[] badChars = {
                "<", ">", "!", "{", "}", "insert", "into", "where", "script",
                "delete", "input"
        };
        for (String badChar : badChars) {
            if (value.contains(badChar)) {
                invalid = true;
                break;
            }
        }
        return invalid;
    }

    public void init(FilterConfig config) throws ServletException {
        config.getServletContext().log("Filter Started");
    }

}
