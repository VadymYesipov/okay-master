package ua.khpi.yesipov.project.servlets;

import org.apache.log4j.Logger;
import ua.khpi.yesipov.project.persistence.MySqlDAOFactory;
import ua.khpi.yesipov.project.persistence.dao.PersonDAO;
import ua.khpi.yesipov.project.persistence.dao.RoleDAO;
import ua.khpi.yesipov.project.persistence.domain.Person;
import ua.khpi.yesipov.project.persistence.domain.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class SignIn extends HttpServlet {

    private MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();
    private PersonDAO personDAO = mySqlDAOFactory.getPersonDAO();
    private static final Logger log = Logger.getLogger(SignIn.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Starting signing in");

        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Person person = personDAO.findPerson(login, password);

        if (person != null) {
            method(person, resp, session);
        }
        else {
            log.debug("Redirect to sign up");
            resp.sendRedirect("pages/signUp.jsp");
        }
    }

    private void method(Person person, HttpServletResponse resp, HttpSession session) throws IOException {
        switch (person.getRole().getId()) {
            case 1:
                session.setAttribute("person", person);
                log.debug("Redirect to admin main page");
                resp.sendRedirect("pages/admin_main_page.jsp");
                break;
            case 2:
                log.debug("Redirect to customer main page");
                resp.sendRedirect("pages/customer_main_page.jsp");
                break;
            case 3:
                log.debug("Redirect to manager main page");
                resp.sendRedirect("pages/manager_main_page.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }
}
