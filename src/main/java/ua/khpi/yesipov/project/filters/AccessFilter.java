package ua.khpi.yesipov.project.filters;

import org.apache.log4j.Logger;
import ua.khpi.yesipov.project.persistence.domain.Person;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessFilter implements Filter {

    private static final Logger log = Logger.getLogger(AccessFilter.class);

    private static final String SIGN_IN_PATH = "/signIn.jsp";
    private static final String ADMIN_MAIN_PAGE = "/admin_main_page.jsp";
    private static final String CUSTOMER_MAIN_PAGE = "/customer_main_page.jsp";
    private static final String MANAGER_MAIN_PAGE = "/manager_main_page.jsp";

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession();
        String url = request.getServletPath();

        Person person = (Person) session.getAttribute("person");

        log.debug(url);
        if ((ADMIN_MAIN_PAGE.equals(url) || CUSTOMER_MAIN_PAGE.equals(url) || MANAGER_MAIN_PAGE.equals(url))
                && person == null) {
            log.debug("person doesn't exist");
            session.setAttribute("error", "You haven't been at this shop. Please, sign up.");
            response.sendRedirect("pages/error.jsp");
            return;
        }
        if (person != null && !SIGN_IN_PATH.equals(url)) {
            log.debug("person exists");
            if (ADMIN_MAIN_PAGE.equals(url) && person.getRole().getId() != 1) {
                log.debug("can't load admin main page");
                session.setAttribute("error", "You don't have rights to sign in like an admin");
                response.sendRedirect("pages/error.jsp");
                return;
            }
            if (CUSTOMER_MAIN_PAGE.equals(url) && person.getRole().getId() != 2) {
                log.debug("can't load customer main page");
                session.setAttribute("error", "You don't have rights to sign in like a customer");
                response.sendRedirect("pages/error.jsp");
                return;
            }
            if (MANAGER_MAIN_PAGE.equals(url) && person.getRole().getId() != 3) {
                log.debug("can't load manager main page");
                session.setAttribute("error", "You don't have rights to sign in like a manager");
                response.sendRedirect("pages/error.jsp");
                return;
            }
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
