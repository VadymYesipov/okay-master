package ua.khpi.yesipov.project.persistence.dao.impl;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import ua.khpi.yesipov.project.persistence.dao.Creatable;
import ua.khpi.yesipov.project.persistence.dao.QualityDAO;
import ua.khpi.yesipov.project.persistence.domain.Brand;
import ua.khpi.yesipov.project.persistence.domain.Quality;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLQualityDAO implements QualityDAO, Creatable {

    private static final String SELECT = "SELECT * FROM orders.quality;";

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
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT);

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
