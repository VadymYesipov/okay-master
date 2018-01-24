package servlets;

import ua.khpi.yesipov.project.persistence.MySqlDAOFactory;
import ua.khpi.yesipov.project.persistence.dao.CarDAO;
import ua.khpi.yesipov.project.persistence.dao.DriverDAO;
import ua.khpi.yesipov.project.persistence.dao.OrderDAO;
import ua.khpi.yesipov.project.persistence.domain.Car;
import ua.khpi.yesipov.project.persistence.domain.Driver;
import ua.khpi.yesipov.project.persistence.domain.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Punisher extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();

        CarDAO carDAO = mySqlDAOFactory.getCarDAO();
        DriverDAO driverDAO = mySqlDAOFactory.getDriverDAO();
        OrderDAO orderDAO = mySqlDAOFactory.getOrderDAO();

        List<Order> orders = orderDAO.selectFutureOrders();

        String parameter = req.getParameter("returnedCars");
        String[] parameters = parameter.split(" ");

        String idParam = parameters[0].substring(0, parameters[0].length() - 1);
        int id = Integer.parseInt(idParam);

        HttpSession session = req.getSession();
        Map<String, List<String>> map = (Map<String, List<String>>) session.getServletContext().getAttribute("sessionMap");

        String login = parameters[2].substring(0, parameters[2].length() - 1);
        System.out.println(login);
        String number = req.getParameter("number");
        Order temp = null;
        if (number.equals("")) {
            for (Order order : orders) {
                if (order.getId() == id) {
                    temp = order;
                    Car car = new Car();
                    car.setId(order.getCar().getId());
                    car.setIsOrdered(0);
                    carDAO.updateCar(car);
                }
            }
        } else {
            for (Order order : orders) {
                if (order.getId() == id) {
                    temp = order;
                    Car car = new Car();
                    car.setId(order.getCar().getId());
                    carDAO.updateDamaged(car);
                }
            }
            List<String> list = map.get(login);
            String reasonParam = parameter.replaceFirst("[\\d]: ", "");
            list.add("You were fined on: " + number + "$ because you broke the car: " + reasonParam);
            map.put(login, list);

            session.getServletContext().setAttribute("sessionMap", map);
        }
        
        Driver driver = new Driver();
        driver.setId(temp.getDriver().getId());
        driver.setIsBusy(0);
        driverDAO.updateDriver(driver);

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
