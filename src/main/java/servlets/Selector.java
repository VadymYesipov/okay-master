package servlets;

import ua.khpi.yesipov.project.persistence.MySqlDAOFactory;
import ua.khpi.yesipov.project.persistence.dao.BrandDAO;
import ua.khpi.yesipov.project.persistence.dao.CarDAO;
import ua.khpi.yesipov.project.persistence.dao.QualityDAO;
import ua.khpi.yesipov.project.persistence.domain.Brand;
import ua.khpi.yesipov.project.persistence.domain.Car;
import ua.khpi.yesipov.project.persistence.domain.Quality;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Selector extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();
        CarDAO carDAO = mySqlDAOFactory.getCarDAO();

        HttpSession session = req.getSession();
        List<String> strings = new ArrayList<String>();
        String parameter = req.getParameter("selectCarsBy");
        List<Car> cars = null;

        if (parameter.equals("default")) {
            cars = carDAO.selectCars();
            showCars(strings, cars);
        }
        if (parameter.equals("sorted model")) {
            cars = carDAO.selectSortedByModel();
            showCars(strings, cars);
        }
        if (parameter.equals("sorted price")) {
            cars = carDAO.selectSortedByPrice();
            showCars(strings, cars);
        }
        if (parameter.equals("brand")) {
            BrandDAO brandDAO = mySqlDAOFactory.getBrandDAO();
            List<Brand> brands = brandDAO.select();
            String[] parameters = req.getParameterValues("selectByBrand");
            for (int i = 0; i < parameters.length; i++) {
                for (Brand brand : brands) {
                    parameters[i] = parameters[i].equals("Range") ? parameters[i] + " Rover" : parameters[i];
                    if (parameters[i].equals(brand.getName())) {
                        System.out.println(brand.getName());
                        cars = carDAO.selectCarsByBrand(brand.getName());
                        showCars(strings, cars);
                        break;
                    }
                }
            }
            System.out.println(cars);
        }
        if (parameter.equals("quality")) {
            QualityDAO qualityDAO = mySqlDAOFactory.getQualityDAO();
            List<Quality> qualities = qualityDAO.select();
            String[] parameters = req.getParameterValues("selectByQuality");
            for (int i = 0; i < parameters.length; i++) {
                for (Quality quality : qualities) {
                    if (parameters[i].equals(quality.getName())) {
                        cars = carDAO.selectCarsByQuality(quality.getName());
                        showCars(strings, cars);
                        break;
                    }
                }
            }
        }
        session.setAttribute("cars", strings);
        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer);
    }

    private void showCars(List<String> strings, List<Car> cars) {
        for (Car car : cars) {
            StringBuilder result = new StringBuilder();
            result.append(car.getBrand().getName() + " " + car.getModel() +
                    ", quality: " + car.getQuality().getName() + " - " + car.getPrice() + "$");
            strings.add(result.toString());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        service(req, resp);
    }
}
