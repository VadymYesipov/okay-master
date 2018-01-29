package ua.khpi.yesipov.project.servlets;

import org.apache.log4j.Logger;
import ua.khpi.yesipov.project.persistence.dao.CarDAO;
import ua.khpi.yesipov.project.persistence.dao.DriverDAO;
import ua.khpi.yesipov.project.persistence.dao.OrderDAO;
import ua.khpi.yesipov.project.persistence.dao.impl.MySQLCarDAO;
import ua.khpi.yesipov.project.persistence.dao.impl.MySQLDriverDAO;
import ua.khpi.yesipov.project.persistence.dao.impl.MySQLOrderDAO;
import ua.khpi.yesipov.project.persistence.domain.Car;
import ua.khpi.yesipov.project.persistence.domain.Driver;
import ua.khpi.yesipov.project.persistence.domain.Order;
import ua.khpi.yesipov.project.persistence.domain.Person;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class Rent extends HttpServlet {

    private static final Logger log = Logger.getLogger(SignIn.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Rent is starting");

        CarDAO carDAO = new MySQLCarDAO();
        OrderDAO orderDAO = new MySQLOrderDAO();
        DriverDAO driverDAO = new MySQLDriverDAO();
        HttpSession session = req.getSession();

        Order order = new Order();

        order.setId(orderDAO.selectCount() + 1);

        Car car = (Car) session.getAttribute("car");
        if (car == null) {
            session.setAttribute("error", "Something is wrong");
            resp.sendRedirect("pages/error.jsp");
            return;
        }
        car.setIsOrdered(1);
        carDAO.updateCar(car);
        order.setCar(car);

        order.setPerson((Person) session.getAttribute("person"));

        order.setSince(new Date((Long) session.getAttribute("sinceTime")));
        order.setTill(new Date((Long) session.getAttribute("tillTime")));

        Driver driver = (Driver) session.getAttribute("driver");
        if (driver.getSurname() != null) {
            driverDAO.updateDriver(driver);
        }
        order.setDriver(driver);

        order.setPrice((Double) session.getAttribute("priceCar"));
        System.out.println(session.getAttribute("priceCar"));

        orderDAO.insertOrder(order);

        List<Car> availableCars = carDAO.selectCars(0);
        session.setAttribute("availableCars", availableCars);

        removeAttributes(session);

        String referer = req.getHeader("Referer");
        log.debug("Redirect to " + referer);
        resp.sendRedirect(referer);
    }

    private void removeAttributes(HttpSession session) {
        session.removeAttribute("car");
        session.removeAttribute("driver");
        session.removeAttribute("priceCar");
        session.removeAttribute("sinceTime");
        session.removeAttribute("tillTime");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }
}
