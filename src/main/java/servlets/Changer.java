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

public class Changer extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();

        CarDAO carDAO = mySqlDAOFactory.getCarDAO();
        BrandDAO brandDAO = mySqlDAOFactory.getBrandDAO();
        QualityDAO qualityDAO = mySqlDAOFactory.getQualityDAO();
        List<Car> cars = carDAO.selectAllCars();

        List<Brand> brands = brandDAO.select();
        List<Quality> qualities = qualityDAO.select();

        HttpSession session = req.getSession();

        String carParam = req.getParameter("choiceOfCars");
        String[] parameters = carParam.split(" ");
        int id = Integer.parseInt(parameters[1]);

        String brandParam = req.getParameter("selectBrands");
        String modelParam = req.getParameter("modelChange");
        String qualityParam = req.getParameter("selectQualities");
        String price = req.getParameter("priceChange");

        if (price.equals("") && modelParam.equals("") && brandParam.equals("") && qualityParam.equals("")) {
            resp.sendRedirect("pages/editError.jsp");
            return;
        }

        Double priceParam = 0D;

        Brand brand = brandParam.equals("") ?
                doBrandForEach(brands, parameters[3]) : doBrandForEach(brands, brandParam);

        Quality quality = qualityParam.equals("") ?
                doQualityForEach(qualities, parameters[6].substring(0, parameters[6].length() - 1)) :
                doQualityForEach(qualities, qualityParam);

        modelParam = modelParam.equals("") ?
                parameters[4].substring(0, parameters[4].length() - 1) :
                modelParam;

        price = price.equals("") ? parameters[9].substring(0, parameters[9].length() - 1) : price;

        try {
            priceParam = Double.valueOf(price);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            resp.sendRedirect("pages/addCarError.jsp");
            return;
        }

        Car car = new Car();
        car.setId(id);
        car.setBrand(brand);
        car.setModel(modelParam);
        car.setQuality(quality);
        car.setPrice(priceParam);

        carDAO.update(car);

        String referer = req.getHeader("Referer");
        resp.sendRedirect(referer);
    }

    private Brand doBrandForEach(List<Brand> brands, String brandParam) {
        for (Brand temp : brands) {
            if (brandParam.equals(temp.getName())) {
                return temp;
            }
        }
        return null;
    }

    private Quality doQualityForEach(List<Quality> qualities, String qualityParam) {
        for (Quality temp : qualities) {
            if (qualityParam.equals(temp.getName())) {
                return temp;
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
