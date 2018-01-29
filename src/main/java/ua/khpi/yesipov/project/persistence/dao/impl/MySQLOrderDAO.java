package ua.khpi.yesipov.project.persistence.dao.impl;

import ua.khpi.yesipov.project.persistence.dao.Creatable;
import ua.khpi.yesipov.project.persistence.dao.OrderDAO;
import ua.khpi.yesipov.project.persistence.domain.*;
import ua.khpi.yesipov.project.persistence.domain.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLOrderDAO implements OrderDAO, Creatable {

    private static final String INSERT_ORDER = "INSERT INTO orders.order_list (id, car_id, person_id, since, till, driver_id, price) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_ORDER = "DELETE FROM orders.order_list WHERE id=?;";
    private static final String SELECT_ALL_ORDERS = "SELECT order_list.id, car.id, brand.brand, car.model, quality.quality, person.first_name, person.middle_name, person.last_name, " +
            "person.birthday, person.login, person.password, driver.id, driver.name, driver.surname, since, till, order_list.price\n" +
            "FROM orders.order_list order_list\n" +
            "LEFT JOIN orders.car car on order_list.car_id=car.id\n" +
            "LEFT JOIN orders.person person on order_list.person_id=person.id\n" +
            "LEFT JOIN orders.brand brand on car.brand_id=brand.id\n" +
            "LEFT JOIN orders.quality quality on car.quality_id=quality.id\n" +
            "LEFT JOIN orders.driver driver on order_list.driver_id=driver.id ";
    private static final String SELECT = "SELECT order_list.id, brand.brand, car.model, quality.quality, person.first_name, person.middle_name, person.last_name, " +
            "person.birthday, driver.name, driver.surname, since, till, order_list.price\n" +
            "FROM orders.order_list order_list\n" +
            "LEFT JOIN orders.car car on order_list.car_id=car.id\n" +
            "LEFT JOIN orders.person person on order_list.person_id=person.id\n" +
            "LEFT JOIN orders.brand brand on car.brand_id=brand.id\n" +
            "LEFT JOIN orders.quality quality on car.quality_id=quality.id\n" +
            "LEFT JOIN orders.driver driver on order_list.driver_id=driver.id ";
    private static final String SELECT_COUNT = "SELECT MAX(*) as total FROM orders.order_list;";

    public int insertOrder(Order order) {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER);
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setInt(2, order.getCar().getId());
            preparedStatement.setInt(3, order.getPerson().getId());
            preparedStatement.setDate(4, order.getSince());
            preparedStatement.setDate(5, order.getTill());
            preparedStatement.setInt(6, order.getDriver().getId());
            preparedStatement.setDouble(7, order.getPrice());

            int result = preparedStatement.executeUpdate();
            preparedStatement.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return 0;
    }

    public boolean deleteOrder(Order order) {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER);
            preparedStatement.setInt(1, order.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Order findOrder(int id) {
        return null;
    }

    public boolean updateOrder(Order order) {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders.order_list SET price=" + order.getPrice()
                    + " WHERE id=" + order.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //manager or admin
    private List<Order> selectAllOrders(String sql) {
        List<Order> orders = new ArrayList<Order>();
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS + sql +";");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt(1));

                Car car = new Car();
                car.setId(resultSet.getInt(2));
                Brand brand = new Brand();
                brand.setName(resultSet.getString(3));
                car.setBrand(brand);
                car.setModel(resultSet.getString(4));
                Quality quality = new Quality();
                quality.setName(resultSet.getString(5));
                car.setQuality(quality);
                order.setCar(car);

                Person person = new Person();
                person.setFirstName(resultSet.getString(6));
                person.setMiddleName(resultSet.getString(7));
                person.setLastName(resultSet.getString(8));
                person.setBirthday(resultSet.getDate(9));
                person.setLogin(resultSet.getString(10));
                person.setPassword(resultSet.getString(11));
                order.setPerson(person);

                Driver driver = new Driver();
                driver.setIsBusy(1);
                driver.setId(resultSet.getInt(12));
                driver.setName(resultSet.getString(13));
                driver.setSurname(resultSet.getString(14));
                order.setDriver(driver);

                order.setSince(resultSet.getDate(15));
                order.setTill(resultSet.getDate(16));

                order.setPrice(resultSet.getDouble(17));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    //customer
    public List<Order> selectOrders(int id) {
        List<Order> orders = new ArrayList<Order>();
        String sql = "WHERE car.isOrdered=1 and order_list.person_id=" + id + ";";
        orders = select(sql, orders);
        return orders;
    }

    //manager
    public List<Order> selectPastOrders() {
        List<Order> orders = new ArrayList<Order>();
        String sql = "WHERE order_list.till < CURRENT_DATE;";
        orders = selectAllOrders(sql);
        return orders;
    }

    public List<Order> selectFutureOrders() {
        List<Order> orders = new ArrayList<Order>();
        String sql = "WHERE order_list.since > CURRENT_DATE;";
        orders = selectAllOrders(sql);
        return orders;
    }

    private List<Order> select(String sql, List<Order> orders) {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT + sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt(1));

                Car car = new Car();
                Brand brand = new Brand();
                brand.setName(resultSet.getString(2));
                car.setBrand(brand);
                car.setModel(resultSet.getString(3));
                Quality quality = new Quality();
                quality.setName(resultSet.getString(4));
                car.setQuality(quality);
                order.setCar(car);

                Person person = new Person();
                person.setFirstName(resultSet.getString(5));
                person.setMiddleName(resultSet.getString(6));
                person.setLastName(resultSet.getString(7));
                person.setBirthday(resultSet.getDate(8));
                order.setPerson(person);

                Driver driver = new Driver();
                driver.setIsBusy(1);
                driver.setName(resultSet.getString(9));
                driver.setSurname(resultSet.getString(10));
                order.setDriver(driver);

                order.setSince(resultSet.getDate(11));
                order.setTill(resultSet.getDate(12));

                order.setPrice(resultSet.getDouble(13));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public int selectCount() {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT);
            ResultSet resultSet = preparedStatement.executeQuery();

            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }

            resultSet.close();
            preparedStatement.close();
            //connection.close();

            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
