package ua.khpi.yesipov.project.servlets;

import org.apache.log4j.Logger;
import ua.khpi.yesipov.project.persistence.MySqlDAOFactory;
import ua.khpi.yesipov.project.persistence.dao.CarDAO;
import ua.khpi.yesipov.project.persistence.dao.DriverDAO;
import ua.khpi.yesipov.project.persistence.domain.Car;
import ua.khpi.yesipov.project.persistence.domain.Driver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriceGetter extends HttpServlet {

    private final int HOUR_IN_MILLISECONDS = 86400000;
    private static final Logger log = Logger.getLogger(SignIn.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("PriceGetter is starting");

        MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();
        CarDAO carDAO = mySqlDAOFactory.getCarDAO();
        DriverDAO driverDAO = mySqlDAOFactory.getDriverDAO();

        HttpSession session = req.getSession();
        List<String> strings = new ArrayList<String>();
        String parameter = req.getParameter("choiceOfCars");
        List<Car> cars = (List<Car>) session.getAttribute("availableCars");

        String param = req.getParameter("selectYesNo");
        List<Driver> drivers = driverDAO.selectDrivers();
        double tariff = 0;
        if (param.equals("yes")) {
            log.debug("Customer chose yes");
            String driverName = req.getParameter("selectDrivers");
            for (Driver driver : drivers) {
                if (driverName.equals(driver.getName() + " " + driver.getSurname())) {
                    driver.setIsBusy(1);
                    session.setAttribute("driver", driver);
                    tariff = 100;
                }
            }
        } else {
            log.debug("Customer chose no");
            Driver driver = new Driver();
            driver.setId(1);
            driver.setName(null);
            driver.setSurname(null);
            driver.setIsBusy(0);
            session.setAttribute("driver", driver);
        }

        showCars(strings, cars);

        System.out.println(parameter);
        for (String temp : strings) {
            System.out.println(temp);
            if (parameter.equals(temp)) {
                String[] temps = temp.split(" ");
                double price = carDAO.selectPrice(temps[0]);
                double days = getDays(req, session);

                log.debug("price = " + price);
                System.out.println("days = " + days);
                System.out.println("tariff = " + tariff);

                session.setAttribute("priceCar", price * days + tariff * days);
                session.setAttribute("car", returnCar(cars, parameter));
            }
        }
        String referer = req.getHeader("Referer");
        log.debug("Redirect to " + referer);
        resp.sendRedirect(referer);
    }

    private double getDays(HttpServletRequest request, HttpSession session) {
        String since = request.getParameter("since");
        String till = request.getParameter("till");
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        Date sinceDate = null;
        Date tillDate = null;
        try {
            sinceDate = format.parse(since);
            tillDate = format.parse(till);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        session.setAttribute("sinceTime", sinceDate.getTime());
        session.setAttribute("tillTime", tillDate.getTime());
        return ((tillDate.getTime() - sinceDate.getTime()) / HOUR_IN_MILLISECONDS) + 1;
    }

    private void showCars(List<String> strings, List<Car> cars) {
        for (Car car : cars) {
            StringBuilder result = new StringBuilder();
            result.append(car.getModel() + " " + car.getBrand().getName() +
                    ", quality: " + car.getQuality().getName() + " - " + car.getPrice() + "$");
            strings.add(result.toString());
        }
    }

    private Car returnCar(List<Car> availableCars, String parameter) {
        for (Car car : availableCars) {
            String formerResult = car.getModel() + " " + car.getBrand().getName() +
                    ", quality: " + car.getQuality().getName() + " - " + car.getPrice() + "$";
            if (formerResult.equals(parameter)) {
                return car;
            }
        }
        return null;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service(request, response);
    }
}
