package ua.khpi.yesipov.project.persistence.dao;

import ua.khpi.yesipov.project.persistence.domain.Driver;

import java.util.List;

public interface DriverDAO {

    public int insertDriver(Driver driver);

    public boolean deleteDriver(Driver driver);

    public Driver findDriver(int id);

    public int updateDriver(Driver driver);

    public List<Driver> selectDrivers();
}
