package ua.khpi.yesipov.project.persistence.dao.impl;

import ua.khpi.yesipov.project.persistence.dao.Creatable;
import ua.khpi.yesipov.project.persistence.dao.RoleDAO;
import ua.khpi.yesipov.project.persistence.domain.Role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLRoleDAO implements RoleDAO, Creatable {

    private static final String SELECT = "SELECT * FROM orders.role;";

    public int insertRole(Role role) {
        return 0;
    }

    public boolean deleteRole(Role role) {
        return false;
    }

    public Role findRole(int id) {
        return null;
    }

    public boolean updateRole(Role role) {
        return false;
    }

    public List<Role> selectRoles() {
        List<Role> roles = new ArrayList<Role>();
        try (Connection connection = createConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT);
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt(1));
                role.setRole(resultSet.getString(2));
                roles.add(role);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }
}
