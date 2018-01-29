package ua.khpi.yesipov.project.filters;

import org.apache.log4j.Logger;
import ua.khpi.yesipov.project.persistence.MySqlDAOFactory;
import ua.khpi.yesipov.project.persistence.dao.PersonDAO;
import ua.khpi.yesipov.project.persistence.domain.Person;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ManagerFilter implements Filter {

    private static final Logger log = Logger.getLogger(AdminFilter.class);

    public void init(FilterConfig filterConfig) {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        HttpSession session = req.getSession();

        Person manager = (Person) session.getAttribute("person");
        if (manager == null || manager.getRole().getId() != 3) {
            log.debug("person doesn't exist");
            ((HttpServletResponse) response).sendRedirect("addCarError.jsp");
            return;
        }

        log.debug("person exists");
        chain.doFilter(req, response);
    }

    public void destroy() {

    }
}
