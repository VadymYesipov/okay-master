package ua.khpi.yesipov.project.servlets;

import org.apache.log4j.Logger;
import ua.khpi.yesipov.project.persistence.dao.PersonDAO;
import ua.khpi.yesipov.project.persistence.dao.impl.MySQLPersonDAO;
import ua.khpi.yesipov.project.persistence.domain.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BlockUnblock extends HttpServlet {

    private static final Logger log = Logger.getLogger(SignIn.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("BlockUnblock is starting");

        PersonDAO personDAO = new MySQLPersonDAO();
        List<Person> persons = personDAO.selectPersons();

        String parameter = req.getParameter("choiceOfCustomers");
        String[] parameters = parameter.split(" ");
        int id = Integer.valueOf(parameters[1]);

        for (Person person : persons) {
            if (person.getId() == id) {
                person.setIsBlocked(person.getIsBlocked() == 1 ? 0 : 1);
                System.out.println(person);
                personDAO.updatePerson(person);
            }
        }

        String referer = req.getHeader("Referer");
        log.debug("Redirect to " + referer);
        resp.sendRedirect(referer);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }
}
