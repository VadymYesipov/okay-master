package ua.khpi.yesipov.project.persistence.dao.impl;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import ua.khpi.yesipov.project.persistence.dao.Creatable;
import ua.khpi.yesipov.project.persistence.dao.PersonDAO;
import ua.khpi.yesipov.project.persistence.domain.Person;
import ua.khpi.yesipov.project.persistence.domain.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLPersonDAO implements PersonDAO, Creatable {

    public int insertPerson(Person person) {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
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

    public Person findPerson(String login) {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT person.id, role.id, role.role, first_name, middle_name, last_name, birthday, login, password " +
                    "FROM orders.person person  " +
                    "LEFT JOIN orders.role role on person.role_id=role.id " +
                    "WHERE login=? and isBlocked=0");

            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            Person person = new Person();

            method(person, resultSet);

            resultSet.close();
            preparedStatement.close();
            //connection.close();

            return person.getRole() == null ? null : person;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Person findPerson(String login, String password) {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT person.id, role.id, role.role, first_name, middle_name, last_name, birthday, login, password " +
                    "FROM orders.person person  " +
                    "LEFT JOIN orders.role role on person.role_id=role.id " +
                    "WHERE login=? and password=? and isBlocked=0");

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            Person person = new Person();

            method(person, resultSet);

            resultSet.close();
            preparedStatement.close();
            //connection.close();

            return person.getRole() == null ? null : person;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void method(Person person, ResultSet resultSet) throws SQLException {
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
    }

    public boolean updatePerson(Person person) {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE orders.person SET isBlocked=" + person.getIsBlocked()
                    + " WHERE id=" + person.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int selectCount() {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) as total FROM orders.person;");
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

    public List<Person> selectPersons() {
        try (Connection connection = createConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT " +
                    "person.id, role.id as role_id, role.role, first_name, middle_name, last_name, birthday, login, password, isBlocked\n" +
                    "FROM orders.person person\n" +
                    "LEFT JOIN orders.role role on person.role_id=role.id\n" +
                    "WHERE role_id=2;");


            ResultSet resultSet = preparedStatement.executeQuery();

            List<Person> persons = new ArrayList<Person>();

            while (resultSet.next()) {
                Person person = new Person();

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

                person.setIsBlocked(resultSet.getInt(10));

                persons.add(person);
            }

            resultSet.close();
            preparedStatement.close();

            return persons;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
