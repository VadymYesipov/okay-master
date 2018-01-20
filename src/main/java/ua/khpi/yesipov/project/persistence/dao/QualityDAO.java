package ua.khpi.yesipov.project.persistence.dao;

import ua.khpi.yesipov.project.persistence.domain.Quality;

import java.util.List;

public interface QualityDAO {

    public int insertQuality(Quality quality);

    public boolean deleteQuality(Quality quality);

    public Quality findQuality(int id);

    public boolean updateQuality(Quality quality);

    public List<Quality> select();
}
