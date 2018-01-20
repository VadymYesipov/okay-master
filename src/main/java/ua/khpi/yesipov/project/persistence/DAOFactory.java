package ua.khpi.yesipov.project.persistence;

import ua.khpi.yesipov.project.persistence.dao.*;

public interface DAOFactory {

    // Здесь будет метод для каждого DAO, который может быть
    // создан. Реализовывать эти методы
    // должны конкретные генераторы.
    public abstract BrandDAO getBrandDAO();

    public abstract CarDAO getCarDAO();

    public abstract DriverDAO getDriverDAO();

    public abstract OrderDAO getOrderDAO();

    public abstract QualityDAO getQualityDAO();

    public abstract RoleDAO getRoleDAO();

    public abstract PersonDAO getPersonDAO();
}
