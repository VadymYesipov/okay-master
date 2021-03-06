package ua.khpi.yesipov.project.servlets;

import org.apache.log4j.Logger;
import ua.khpi.yesipov.project.persistence.dao.PersonDAO;
import ua.khpi.yesipov.project.persistence.dao.RoleDAO;
import ua.khpi.yesipov.project.persistence.dao.impl.MySQLPersonDAO;
import ua.khpi.yesipov.project.persistence.dao.impl.MySQLRoleDAO;
import ua.khpi.yesipov.project.persistence.domain.Person;
import ua.khpi.yesipov.project.persistence.domain.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Register extends HttpServlet {

    private static final Logger log = Logger.getLogger(SignIn.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Register is starting");

        PersonDAO personDAO = new MySQLPersonDAO();
        RoleDAO roleDAO = new MySQLRoleDAO();
        HttpSession session = req.getSession();

        List<Role> roles = roleDAO.selectRoles();

        String firstName = req.getParameter("firstNameField");
        String middleName = req.getParameter("middleField");
        String lastName = req.getParameter("lastNameField");
        String login = req.getParameter("loginField");
        String password = req.getParameter("passwordField");

        Person oldPerson = personDAO.findPerson(login);

        if (oldPerson != null) {
            log.debug("Redirect to register manager error");
            session.setAttribute("error", "Somebody has gotten registered by this login");
            resp.sendRedirect("pages/error.jsp");
            return;
        } else {
            Person person = new Person();

            person.setId(personDAO.selectCount() + 1);

            person.setRole(roles.get(2));

            person.setFirstName(firstName);
            person.setMiddleName(middleName);
            person.setLastName(lastName);

            person.setBirthday(new java.sql.Date(getDays(req)));

            person.setLogin(login);
            person.setPassword(password);

            person.setIsBlocked(0);

            personDAO.insertPerson(person);
        }

        String referer = req.getHeader("Referer");
        log.debug("Redirect to " + referer);
        resp.sendRedirect(referer);
    }

    private long getDays(HttpServletRequest request) {
        String since = request.getParameter("birthdayField");
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date sinceDate = null;
        try {
            sinceDate = format.parse(since);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sinceDate.getTime();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }
}
