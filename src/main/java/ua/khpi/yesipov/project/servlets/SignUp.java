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
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUp extends HttpServlet {

    private MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();
    private RoleDAO roleDAO = mySqlDAOFactory.getRoleDAO();
    private PersonDAO personDAO = mySqlDAOFactory.getPersonDAO();
    private final List<Role> roles = roleDAO.selectRoles();
    private List<Person> personList = new ArrayList<Person>();
    private static final Logger log = Logger.getLogger(SignIn.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Starting signing up");

        HttpSession session = req.getSession();
        req.setCharacterEncoding("UTF-8");

        Person person = new Person();

        person.setId(personDAO.selectCount() + 1);

        person.setRole(roles.get(1));

        String login = req.getParameter("login");
        person.setLogin(login);

        String password = req.getParameter("password");
        person.setPassword(password);

        person.setFirstName(req.getParameter("firstName"));
        person.setMiddleName(req.getParameter("middleName"));
        person.setLastName(req.getParameter("lastName"));
        person.setBirthday(Date.valueOf(req.getParameter("birthday")));

        person.setIsBlocked(0);

        Person oldPerson = personDAO.findPerson(login);

        if (oldPerson != null) {
            log.debug("Redirect ro sign up error");
            resp.sendRedirect("pages/signUpError.jsp");
        } else {
            log.debug("Redirect to sign in");

            Map<String, List> sessionMap = (Map<String, List>) req.getServletContext().getAttribute("sessionMap");
            if (sessionMap == null) {
                sessionMap = new HashMap<String, List>();
            }
            sessionMap.put(login, new ArrayList<String>());
            req.getServletContext().setAttribute("sessionMap", sessionMap);

            personDAO.insertPerson(person);
            personList.add(person);
            session.setAttribute("person", person);
            resp.sendRedirect("signIn.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }
}
