package servlets;

import ua.khpi.yesipov.project.persistence.MySqlDAOFactory;
import ua.khpi.yesipov.project.persistence.dao.CarDAO;
import ua.khpi.yesipov.project.persistence.dao.PersonDAO;
import ua.khpi.yesipov.project.persistence.domain.Car;
import ua.khpi.yesipov.project.persistence.domain.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BlockUnblock extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();

        PersonDAO personDAO = mySqlDAOFactory.getPersonDAO();
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
        resp.sendRedirect(referer);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }
}
