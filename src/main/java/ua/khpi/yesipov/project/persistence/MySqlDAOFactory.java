package ua.khpi.yesipov.project.persistence;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import ua.khpi.yesipov.project.persistence.dao.*;
import ua.khpi.yesipov.project.persistence.dao.impl.*;

import java.sql.Connection;
import java.sql.SQLException;

public class MySqlDAOFactory implements DAOFactory {

    public static final String DRIVER =
            "com.mysql.jdbc.Driver";
    public static final String DB_URL =
            "jdbc:mysql://localhost:3306/orders?useSSL=false";

    private MysqlDataSource mysqlDataSource;

    public MySqlDAOFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(DB_URL);
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("root");
    }

    // метод для создания соединений к MySQL
    public Connection createConnection() {
        try {
            return mysqlDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BrandDAO getBrandDAO() {
        return new MySQLBrandDAO(createConnection());
    }

    public CarDAO getCarDAO() {
        return new MySQLCarDAO(createConnection());
    }

    public DriverDAO getDriverDAO() {
        return new MySQLDriverDAO(createConnection());
    }

    public OrderDAO getOrderDAO() {
        return new MySQLOrderDAO(createConnection());
    }

    public QualityDAO getQualityDAO() {
        return new MySQLQualityDAO(createConnection());
    }

    public RoleDAO getRoleDAO() {
        return new MySQLRoleDAO(createConnection());
    }

    public PersonDAO getPersonDAO() {
        return new MySQLPersonDAO(createConnection());
    }
}