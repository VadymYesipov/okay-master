package ua.khpi.yesipov.project.persistence.dao.impl;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import ua.khpi.yesipov.project.persistence.dao.BrandDAO;
import ua.khpi.yesipov.project.persistence.dao.Creatable;
import ua.khpi.yesipov.project.persistence.domain.Brand;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLBrandDAO implements BrandDAO, Creatable{

    private static final String sql = "SELECT * FROM orders.brand;";

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
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

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
