package ua.khpi.yesipov.project.persistence.dao.impl;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import ua.khpi.yesipov.project.persistence.dao.QualityDAO;
import ua.khpi.yesipov.project.persistence.domain.Brand;
import ua.khpi.yesipov.project.persistence.domain.Quality;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLQualityDAO implements QualityDAO {

    public static final String DRIVER =
            "com.mysql.jdbc.Driver";
    public static final String DB_URL =
            "jdbc:mysql://localhost:3306/orders?useSSL=false";
    private MysqlDataSource mysqlDataSource;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public MySQLQualityDAO(Connection connection) {
        this.connection = connection;
    }

    public MySQLQualityDAO() throws SQLException {
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

    public int insertQuality(Quality quality) {
        return 0;
    }

    public boolean deleteQuality(Quality quality) {
        return false;
    }

    public Quality findQuality(int id) {
        return null;
    }

    public boolean updateQuality(Quality quality) {
        return false;
    }

    public List<Quality> select() {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM orders.quality;");

            List<Quality> qualities = new ArrayList<Quality>();
            while (resultSet.next()) {
                Quality quality = new Quality();
                quality.setId(resultSet.getInt(1));
                quality.setName(resultSet.getString(2));

                qualities.add(quality);
            }

            resultSet.close();
            statement.close();

            return qualities;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
