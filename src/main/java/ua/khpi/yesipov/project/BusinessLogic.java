package ua.khpi.yesipov.project;

import ua.khpi.yesipov.project.persistence.MySqlDAOFactory;
import ua.khpi.yesipov.project.persistence.dao.*;
import ua.khpi.yesipov.project.persistence.domain.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class BusinessLogic {

    private MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory();

    private CarDAO carDAO = mySqlDAOFactory.getCarDAO();

    private RoleDAO roleDAO = mySqlDAOFactory.getRoleDAO();

    private OrderDAO orderDAO = mySqlDAOFactory.getOrderDAO();

    private PersonDAO personDAO = mySqlDAOFactory.getPersonDAO();

    private DriverDAO driverDAO = mySqlDAOFactory.getDriverDAO();

    private static List<Car> cars;

    private static List<Order> orders;

    private static List<Role> roles;

    private static List<Driver> drivers;

    public BusinessLogic() {
        roles = roleDAO.selectRoles();
        drivers = driverDAO.selectDrivers();

        System.out.println("Do you have a login?");
        Scanner scanner = new Scanner(System.in);
        if (scanner.next().equalsIgnoreCase("no")) {
            register(scanner);
        } else {
            System.out.println("Type in your login");
            String login = scanner.next();
            System.out.println("Type in your password");
            String password = scanner.next();
            person = personDAO.findCustomer(login, password);
            if (person.getFirstName() != null) {
                System.out.println("Welcome");
                System.out.println("Your previous orders:");
                try {
                    showOrders();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Goodbye");
                System.exit(0);
            }
        }
        cars = carDAO.selectCars();//carDAO.selectSortedByModel();carDAO.selectSortedByPrice();carDAO.selectCarsByBrand("Audi");carDAO.selectCarsByQuality("good");
        System.out.println("Which car do you want to rent");
        for (Car car : cars) {
            System.out.println(car);
        }
    }

    public void makeOrder(Scanner scanner) {
        OrderDAO orderDAO = mySqlDAOFactory.getOrderDAO();
        Order order = new Order();

        order.setId(orderDAO.selectCount() + 1);

        int i = scanner.nextInt() - 1;
        cars.get(i).setIsOrdered(1);
        carDAO.updateCar(cars.get(i));
        order.setCar(cars.get(i));

        order.setPerson(person);

        long since = System.currentTimeMillis();
        order.setSince(new Date(since));
        System.out.println("How long do you want to rent the car? (hours)");
        long till = since + 3600000 * scanner.nextInt();
        order.setTill(new Date(till));

        double price = ((till - since) / 3600000) * cars.get(i).getPrice();
        order.setPrice(price);

        System.out.println("Do you want to have a driver");
        Driver driver = new Driver();
        if (scanner.next().equalsIgnoreCase("yes")) {
            System.out.println("Which driver do you want to rent?");
            for (int j = 0; j < drivers.size(); j++) {
                System.out.println(drivers.get(j));
            }
            driver = drivers.get(scanner.nextInt() - 2);
            driver.setIsBusy(1);
            driverDAO.updateDriver(driver);
        } else {
            driver.setId(1);
        }
        order.setDriver(driver);

        orderDAO.insertOrder(order);
    }

    public void showOrders() throws SQLException {
        orders = orderDAO.selectOrders(person.getId());

        for (Order order : orders) {
            System.out.println(order);
        }
    }

    private Person person;

    private void register(Scanner scanner) {
        person = new Person();
        person.setId(personDAO.selectCount() + 1);

        person.setBirthday(new Date(System.currentTimeMillis()));

        person.setRole(roles.get(1));

        System.out.println("What's your first name?");
        person.setFirstName(scanner.next());
        System.out.println("What's your middle name?");
        person.setMiddleName(scanner.next());
        System.out.println("What's your last name?");
        person.setLastName(scanner.next());
        System.out.println("What's your login?");
        String login = scanner.next();
        person.setLogin(login);
        System.out.println("What's your password?");
        String password = scanner.next();
        person.setPassword(password);

        person.setIsBlocked(0);

        Person copy = personDAO.findCustomer(login, password);
        if (copy.getFirstName() != null) {
            System.out.println("Goodbye");
            System.exit(0);
        }

        personDAO.insertPerson(person);
    }
}
