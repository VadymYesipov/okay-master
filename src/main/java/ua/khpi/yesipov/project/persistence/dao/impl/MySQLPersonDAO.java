package ua.khpi.yesipov.project.persistence.dao.impl;

import ua.khpi.yesipov.project.persistence.dao.PersonDAO;
import ua.khpi.yesipov.project.persistence.domain.Person;
import ua.khpi.yesipov.project.persistence.domain.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySQLPersonDAO implements PersonDAO {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public MySQLPersonDAO(Connection connection) {
        this.connection = connection;
    }

    public int insertPerson(Person person) {
        try {
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO person (id, role_id, first_name, middle_name, last_name, birthday, login, password, isBlocked) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, person.getId());
            preparedStatement.setInt(2, person.getRole().getId());
            preparedStatement.setString(3, person.getFirstName());
            preparedStatement.setString(4, person.getMiddleName());
            preparedStatement.setString(5, person.getLastName());
            preparedStatement.setDate(6, person.getBirthday());
            preparedStatement.setString(7, person.getLogin());
            preparedStatement.setString(8, person.getPassword());
            preparedStatement.setInt(9, person.getIsBlocked());

            int updated = preparedStatement.executeUpdate();
            preparedStatement.close();

            return updated;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean deletePerson(Person person) {
        return false;
    }

    public Person findCustomer(String login, String password) {
        return findPerson(login, password, 2);
    }

    public Person findAdmin(String login, String password) {
        return findPerson(login, password, 1);
    }

    public Person findManager(String login, String password) {
        return findPerson(login, password, 3);
    }

    private Person findPerson(String login, String password, int role_id) {
        try {
            preparedStatement = connection.prepareStatement("SELECT person.id, role.id, role.role, first_name, middle_name, last_name, birthday, login, password " +
                    "FROM orders.person person  " +
                    "LEFT JOIN orders.role role on person.role_id=role.id " +
                    "WHERE login=? and password=? and isBlocked=0 and role_id=" + role_id);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();

            Person person = new Person();
            if (resultSet.next()) {
                person.setId(resultSet.getInt(1));

                Role role = new Role();
                role.setId(resultSet.getInt(2));
                role.setRole(resultSet.getString(3));
                person.setRole(role);

                person.setFirstName(resultSet.getString(4));
                person.setMiddleName(resultSet.getString(5));
                person.setLastName(resultSet.getString(6));
                person.setBirthday(resultSet.getDate(7));

                person.setLogin(resultSet.getString(8));
                person.setPassword(resultSet.getString(9));
            }

            resultSet.close();
            preparedStatement.close();
            //connection.close();

            return person;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePerson(Person person) {
        return false;
    }

    public List<Person> selectPerson() {
        return null;
    }

    public int selectCount() {
        try {
            preparedStatement = connection.prepareStatement("SELECT COUNT(*) as total FROM orders.person;");
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
