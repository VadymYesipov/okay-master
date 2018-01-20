package ua.khpi.yesipov.project.persistence.dao;

import ua.khpi.yesipov.project.persistence.domain.Brand;

import java.util.List;

public interface BrandDAO {

    public int insertBrand(Brand brand);

    public boolean deleteBrand(Brand brand);

    public Brand findBrand(int id);

    public boolean updateBrands(Brand brand);

    public List<Brand> select();
}
