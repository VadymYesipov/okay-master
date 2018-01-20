package ua.khpi.yesipov.project.persistence.domain;

import lombok.Data;

@Data
public class Car {

    private Integer id;

    private Brand brand;

    private String model;

    private Quality quality;

    private Double price;

    private Integer isOrdered;
}
