import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "PathTraversalFilterPublic")
public class PathTraversalFilterPublic implements Filter {

    private ServletContext context;

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        System.out.println("Reached filter");
        if(session == null || session.getAttribute("role") == "admin" ||
        session.getAttribute("username") == null){
            System.out.println("Reached filter if block");
            this.context.log("Unauthorized access request");
            res.sendRedirect(req.getContextPath() + "/index.jsp");
        }else{
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
        this.context = config.getServletContext();
        this.context.log("PathTraversalFilter initialised");
    }

}
