package ua.khpi.yesipov.project.persistence.dao;

import ua.khpi.yesipov.project.persistence.domain.Person;

import java.util.List;

public interface PersonDAO {

    public int insertPerson(Person person);

    public boolean deletePerson(Person person);

    public Person findPerson(String login);

    public Person findPerson(String login, String password);

    public boolean updatePerson(Person person);

    public int selectCount();

    public List<Person> selectPersons();
}
