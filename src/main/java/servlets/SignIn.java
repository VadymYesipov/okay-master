package servlets;

import ua.khpi.yesipov.project.persistence.MySqlDAOFactory;
import ua.khpi.yesipov.project.persistence.dao.PersonDAO;
import ua.khpi.yesipov.project.persistence.dao.RoleDAO;
import ua.khpi.yesipov.project.persistence.domain.Person;
import ua.khpi.yesipov.project.persistence.domain.Role;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class SignIn extends HttpServlet {

    private MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();
    private RoleDAO roleDAO = mySqlDAOFactory.getRoleDAO();
    private final List<Role> roles = roleDAO.selectRoles();
    private PersonDAO personDAO = mySqlDAOFactory.getPersonDAO();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Starting SignIn");
        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Person customer = personDAO.findCustomer(login, password);
        Person admin = personDAO.findAdmin(login, password);
        Person manager = personDAO.findManager(login, password);

        if (customer.getRole() != null || admin.getRole() != null || manager.getRole() != null) {

            if (customer.getRole() != null) {
                session.setAttribute("person", customer);
                resp.sendRedirect("pages/customer_main_page.jsp");
                //req.getRequestDispatcher("/userPage").forward(req, resp);
            } else if (admin.getRole() != null) {
                session.setAttribute("person", admin);
                resp.sendRedirect("pages/admin_main_page.jsp");
            } else if (manager.getRole() != null) {
                session.setAttribute("person", manager);
                resp.sendRedirect("pages/manager_main_page.jsp");
            }

        } else {
            resp.sendRedirect("pages/signUp.jsp");
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
