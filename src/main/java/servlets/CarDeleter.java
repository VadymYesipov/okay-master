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
import java.util.List;

public class CarDeleter extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();

        CarDAO carDAO = mySqlDAOFactory.getCarDAO();
        List<Car> cars = carDAO.selectAllCars();

        String parameter = req.getParameter("choiceOfCars");
        String[] parameters = parameter.split(" ");
        int id = Integer.parseInt(parameters[1]);

        for (Car car : cars) {
            if (car.getId() == id) {
                carDAO.deleteCar(car);
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
