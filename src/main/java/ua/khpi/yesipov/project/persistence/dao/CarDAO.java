package ua.khpi.yesipov.project.persistence.dao;

import ua.khpi.yesipov.project.persistence.domain.Brand;
import ua.khpi.yesipov.project.persistence.domain.Car;
import ua.khpi.yesipov.project.persistence.domain.Quality;

import java.util.List;

public interface CarDAO {

    //admin
    public int insertCar(Car car);

    //admin
    public boolean deleteCar(Car car);

    public Car findCar(int id);

    public boolean updateDamaged(Car car);

    //admin
    public boolean updateCar(Car car);

    //customer
    public List<Car> selectCars();

    public List<Car> selectAllCars();

    //customer
    public List<Car> selectCarsByBrand(String brand);

    //customer
    public List<Car> selectCarsByQuality(String quality);

    //customer
    public List<Car> selectSortedByModel();

    //customer
    public List<Car> selectSortedByPrice();

    public List<Brand> selectAvailableBrands();

    public List<Quality> selectAvailableQualities();
}
