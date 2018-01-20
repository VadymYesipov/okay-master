package ua.khpi.yesipov.project.persistence.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Order {

    private Integer id;

    private Car car;

    private Person person;

    private Date since;
    private Date till;

    private Driver driver;

    private Double price;
}
