package ua.khpi.yesipov.project.persistence.dao.impl;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import ua.khpi.yesipov.project.persistence.dao.Creatable;
import ua.khpi.yesipov.project.persistence.dao.DriverDAO;
import ua.khpi.yesipov.project.persistence.domain.Driver;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLDriverDAO implements DriverDAO, Creatable {


    private static final String SELECT = "SELECT * FROM orders.driver WHERE id>1 and isBusy=0;";

    public int insertDriver(Driver driver) {
        return 0;
    }

    public boolean deleteDriver(Driver driver) {
        return false;
    }

    public Driver findDriver(int id) {
        return null;
    }

    public int updateDriver(Driver driver) {
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            int i = statement.executeUpdate("UPDATE orders.driver SET isBusy=" + driver.getIsBusy() + " WHERE id>1 and id=" + driver.getId());

            statement.close();
            connection.close();

            return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Driver> selectDrivers() {
        List<Driver> drivers = new ArrayList<Driver>();
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT);
            while (resultSet.next()) {
                Driver driver = new Driver();
                driver.setId(resultSet.getInt(1));
                driver.setName(resultSet.getString(2));
                driver.setSurname(resultSet.getString(3));
                driver.setIsBusy(resultSet.getInt(4));

                drivers.add(driver);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }
}
