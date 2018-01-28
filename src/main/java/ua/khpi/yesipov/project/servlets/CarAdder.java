package ua.khpi.yesipov.project.servlets;

import org.apache.log4j.Logger;
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

public class CarAdder extends HttpServlet {

    private static final Logger log = Logger.getLogger(SignIn.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("CarAdder is starting");

        MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();

        CarDAO carDAO = mySqlDAOFactory.getCarDAO();
        BrandDAO brandDAO = mySqlDAOFactory.getBrandDAO();
        QualityDAO qualityDAO = mySqlDAOFactory.getQualityDAO();

        List<Brand> brands = brandDAO.select();
        List<Quality> qualities = qualityDAO.select();

        HttpSession session = req.getSession();

        String brandParam = req.getParameter("selectBrands");
        String modelParam = req.getParameter("model");
        String qualityParam = req.getParameter("selectQualities");
        Double priceParam = 0D;
        try {
            priceParam = Double.valueOf(req.getParameter("price"));
        } catch (NumberFormatException e) {
            log.debug("Redirect to add car error");
            resp.sendRedirect("pages/addCarError.jsp");
            return;
        }

        Brand brand = null;
        for (Brand temp : brands) {
            if (brandParam.equals(temp.getName())) {
                brand = temp;
                break;
            }
        }

        Quality quality = null;
        for (Quality temp : qualities) {
            if (qualityParam.equals(temp.getName())) {
                quality = temp;
                break;
            }
        }

        Car car = new Car();
        car.setId(carDAO.selectCount() + 1);
        car.setBrand(brand);
        car.setModel(modelParam);
        car.setQuality(quality);
        car.setPrice(priceParam);
        car.setIsOrdered(0);

        carDAO.insertCar(car);

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
