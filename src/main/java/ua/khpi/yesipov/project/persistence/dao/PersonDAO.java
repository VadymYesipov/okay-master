package ua.khpi.yesipov.project.persistence.dao;

import ua.khpi.yesipov.project.persistence.domain.Person;

import java.util.List;

public interface PersonDAO {

    public int insertPerson(Person person);

    public boolean deletePerson(Person person);

    public Person findCustomer(String login, String password);

    public Person findAdmin(String login, String password);

    public Person findManager(String login, String password);

    public boolean updatePerson(Person person);

    public List<Person> selectPerson();

    public int selectCount();
}
