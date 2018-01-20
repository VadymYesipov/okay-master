package ua.khpi.yesipov.project.persistence.dao.impl;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import ua.khpi.yesipov.project.persistence.dao.BrandDAO;
import ua.khpi.yesipov.project.persistence.domain.Brand;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLBrandDAO implements BrandDAO {

    public static final String DRIVER =
            "com.mysql.jdbc.Driver";
    public static final String DB_URL =
            "jdbc:mysql://localhost:3306/orders?useSSL=false";
    private MysqlDataSource mysqlDataSource;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public MySQLBrandDAO(Connection connection) {
        this.connection = connection;
    }

    public MySQLBrandDAO() throws SQLException {
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

    public int insertBrand(Brand brand) {
        return 0;
    }

    public boolean deleteBrand(Brand brand) {
        return false;
    }

    public Brand findBrand(int id) {
        return null;
    }

    public boolean updateBrands(Brand brand) {
        return false;
    }

    public List<Brand> select() {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM orders.brand;");

            List<Brand> brands = new ArrayList<Brand>();
            while (resultSet.next()) {
                Brand brand = new Brand();
                brand.setId(resultSet.getInt(1));
                brand.setName(resultSet.getString(2));

                brands.add(brand);
            }

            resultSet.close();
            statement.close();

            return brands;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
