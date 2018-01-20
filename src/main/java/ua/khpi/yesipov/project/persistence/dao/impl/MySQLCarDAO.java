package ua.khpi.yesipov.project.persistence.dao.impl;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import ua.khpi.yesipov.project.persistence.dao.CarDAO;
import ua.khpi.yesipov.project.persistence.domain.Brand;
import ua.khpi.yesipov.project.persistence.domain.Car;
import ua.khpi.yesipov.project.persistence.domain.Quality;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLCarDAO implements CarDAO {

    public static final String DRIVER =
            "com.mysql.jdbc.Driver";
    public static final String DB_URL =
            "jdbc:mysql://localhost:3306/orders?useSSL=false";
    private MysqlDataSource mysqlDataSource;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public MySQLCarDAO(Connection connection) {
        this.connection = connection;
    }

    public MySQLCarDAO() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(DB_URL);
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("root");
        this.connection = mysqlDataSource.getConnection();
    }

    public int insertCar(Car car) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO orders_list.car (id, brand_id, model, quality_id, price, isOrdered) " +
                            "VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, car.getId());
            preparedStatement.setInt(2, car.getBrand().getId());
            preparedStatement.setString(3, car.getModel());
            preparedStatement.setInt(4, car.getQuality().getId());
            preparedStatement.setDouble(5, car.getPrice());
            preparedStatement.setInt(6, car.getIsOrdered());

            int result = preparedStatement.executeUpdate();
            preparedStatement.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean deleteCar(Car car) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM order_list.car car WHERE car.id=" + car.getId() + ";");
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Car findCar(int id) {
        return null;
    }

    public boolean updateDamaged(Car car) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE orders.car SET quality_id=4 WHERE id=" + car.getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCar(Car car) {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE orders.car SET isOrdered=" + car.getIsOrdered()
                    + " WHERE id=" + car.getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Car> selectCars() {
        List<Car> cars = new ArrayList<Car>();
        try {
            String sql = "WHERE car.isOrdered=0;";
            cars = select(sql, cars);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> selectAllCars() {
        return null;
    }

    public List<Car> selectCarsByBrand(String brand) {
        List<Car> cars = new ArrayList<Car>();
        try {
            String sql = "WHERE car.isOrdered=0 and brand.brand=\"" + brand + "\";";
            cars = select(sql, cars);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> selectCarsByQuality(String quality) {
        List<Car> cars = new ArrayList<Car>();
        try {
            String sql = "WHERE car.isOrdered=0 and quality.id<4 and quality.quality=\"" + quality + "\";";
            cars = select(sql, cars);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> selectSortedByModel() {
        List<Car> cars = new ArrayList<Car>();
        try {
            String sql = "WHERE car.isOrdered=0 ORDER BY model;";
            cars = select(sql, cars);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Car> selectSortedByPrice() {
        List<Car> cars = new ArrayList<Car>();
        try {
            String sql = "WHERE car.isOrdered=0 ORDER BY price DESC;";
            cars = select(sql, cars);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    private List<Car> select(String sql, List<Car> cars) throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT car.id, brand.id, brand.brand, car.model, quality.id, quality.quality, car.price, car.isOrdered FROM orders.car car\n" +
                "LEFT JOIN orders.brand brand on car.brand_id=brand.id\n" +
                "LEFT JOIN orders.quality quality on car.quality_id=quality.id\n" + sql);
        while (resultSet.next()) {
            Car car = new Car();
            car.setId(resultSet.getInt(1));

            Brand brand = new Brand();
            brand.setId(resultSet.getInt(2));
            brand.setName(resultSet.getString(3));
            car.setBrand(brand);

            car.setModel(resultSet.getString(4));

            Quality quality = new Quality();
            quality.setId(resultSet.getInt(5));
            quality.setName(resultSet.getString(6));
            car.setQuality(quality);

            car.setPrice(resultSet.getDouble(7));

            car.setIsOrdered(resultSet.getInt(8));

            cars.add(car);
        }
        return cars;
    }

    public List<Brand> selectAvailableBrands() {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT brand.brand FROM orders.car car\n" +
                    "LEFT JOIN orders.brand brand on car.brand_id=brand.id\n" +
                    "WHERE isOrdered=0;");
            List<Brand> brands = new ArrayList<Brand>();
            while (resultSet.next() ) {
                Brand brand = new Brand();
                brand.setName(resultSet.getString(1));
                if (isUniqueBrand(brands, brand)) {
                    brands.add(brand);
                }
            }
            return brands;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Quality> selectAvailableQualities() {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT quality.quality FROM orders.car car\n" +
                    "LEFT JOIN orders.quality quality on car.quality_id=quality.id\n" +
                    "WHERE isOrdered=0 and quality.id<4;");
            List<Quality> qualities = new ArrayList<Quality>();
            while (resultSet.next() ) {
                Quality quality = new Quality();
                quality.setName(resultSet.getString(1));
                if (isUniqueQuality(qualities, quality)) {
                    qualities.add(quality);
                }
            }
            return qualities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isUniqueQuality(List<Quality> qualities, Quality quality) {
        for (Quality temp : qualities) {
            if (temp.getName().equals(quality.getName())) {
                return false;
            }
        }
        return true;
    }

    private boolean isUniqueBrand(List<Brand> brands, Brand brand) {
        for (Brand temp : brands) {
            if (temp.getName().equals(brand.getName())) {
                return false;
            }
        }
        return true;
    }
}
