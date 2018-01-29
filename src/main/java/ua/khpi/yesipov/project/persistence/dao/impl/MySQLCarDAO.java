package ua.khpi.yesipov.project.persistence.dao.impl;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import ua.khpi.yesipov.project.persistence.dao.CarDAO;
import ua.khpi.yesipov.project.persistence.dao.Creatable;
import ua.khpi.yesipov.project.persistence.domain.Brand;
import ua.khpi.yesipov.project.persistence.domain.Car;
import ua.khpi.yesipov.project.persistence.domain.Quality;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLCarDAO implements CarDAO, Creatable {

    private static final String UPDATE_DAMAGED = "UPDATE orders.car SET quality_id=4, isOrdered=0 WHERE id=";
    private static final String DELETE_CAR = "DELETE FROM orders.car WHERE id=";
    private static final String INSERT_CAR = "INSERT INTO orders.car (id, brand_id, model, quality_id, price, isOrdered) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT = "SELECT car.id, brand.id, brand.brand, car.model, quality.id, quality.quality, car.price, car.isOrdered " +
            "FROM orders.car car\n" +
            "LEFT JOIN orders.brand brand on car.brand_id=brand.id\n" +
            "LEFT JOIN orders.quality quality on car.quality_id=quality.id\n";
    private static final String SELECT_AVAILABLE_BRANDS = "SELECT brand.brand FROM orders.car car\n" +
            "LEFT JOIN orders.brand brand on car.brand_id=brand.id\n" +
            "WHERE isOrdered=0;";
    private static final String SELECT_AVAILABLE_QUALITIES = "SELECT quality.quality FROM orders.car car\n" +
            "LEFT JOIN orders.quality quality on car.quality_id=quality.id\n" +
            "WHERE isOrdered=0 and quality.id<4;";
    private static final String SELECT_COUNT = "SELECT MAX(id) as total FROM orders.car;";

    public int insertCar(Car car) {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_CAR);
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
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate( DELETE_CAR + car.getId() + ";");
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
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(UPDATE_DAMAGED + car.getId());
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCar(Car car) {
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE orders.car SET isOrdered=" + car.getIsOrdered()
                    + " WHERE id=" + car.getId());
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Car car) {
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE orders.car " +
                    "SET brand_id=" + car.getBrand().getId() + ", model=\"" + car.getModel() +
                    "\", quality_id=" + car.getQuality().getId() + ", price=" + car.getPrice() +
                    " WHERE id=" + car.getId() + ";");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Car> selectCars(int id) {
        List<Car> cars = new ArrayList<Car>();
        String sql = "WHERE car.isOrdered=" + id + ";";
        cars = select(sql, cars);
        return cars;
    }

    public List<Car> selectAllCars() {
        List<Car> cars = new ArrayList<Car>();
        String sql = ";";
        cars = select(sql, cars);
        return cars;
    }

    public List<Car> selectCarsByBrand(String brand) {
        List<Car> cars = new ArrayList<Car>();
        String sql = "WHERE car.isOrdered=0 and brand.brand=\"" + brand + "\";";
        cars = select(sql, cars);
        return cars;
    }

    public List<Car> selectCarsByQuality(String quality) {
        List<Car> cars = new ArrayList<Car>();
        String sql = "WHERE car.isOrdered=0 and quality.id<4 and quality.quality=\"" + quality + "\";";
        cars = select(sql, cars);
        return cars;
    }

    public List<Car> selectSortedByModel() {
        List<Car> cars = new ArrayList<Car>();
        String sql = "WHERE car.isOrdered=0 ORDER BY model;";
        cars = select(sql, cars);
        return cars;
    }

    public List<Car> selectSortedByPrice() {
        List<Car> cars = new ArrayList<>();
        String sql = "WHERE car.isOrdered=0 ORDER BY price DESC;";
        cars = select(sql, cars);
        return cars;
    }

    private List<Car> select(String sql, List<Car> cars) {
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT + sql);
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

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public List<Brand> selectAvailableBrands() {
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_AVAILABLE_BRANDS);
            List<Brand> brands = new ArrayList<Brand>();
            while (resultSet.next() ) {
                Brand brand = new Brand();
                brand.setName(resultSet.getString(1));
                if (isUniqueBrand(brands, brand)) {
                    brands.add(brand);
                }
            }

            resultSet.close();
            statement.close();

            return brands;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Quality> selectAvailableQualities() {
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_AVAILABLE_QUALITIES);
            List<Quality> qualities = new ArrayList<Quality>();
            while (resultSet.next() ) {
                Quality quality = new Quality();
                quality.setName(resultSet.getString(1));
                if (isUniqueQuality(qualities, quality)) {
                    qualities.add(quality);
                }
            }

            resultSet.close();
            statement.close();

            return qualities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double selectPrice(String model) {
        double price = 0;
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT car.price FROM orders.car car WHERE car.model=\"" + model + "\";");
            while (resultSet.next()) {
                price = resultSet.getDouble(1);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    public Integer selectCount() {
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_COUNT);

            int total = 0;
            while (resultSet.next()) {
                total = resultSet.getInt("total");
            }

            resultSet.close();
            statement.close();

            return total;

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
