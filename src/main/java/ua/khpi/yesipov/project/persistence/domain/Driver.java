package ua.khpi.yesipov.project.persistence.domain;

import lombok.Data;

@Data
public class Driver {

    private Integer id;

    private String name;
    private String surname;

    private Integer isBusy;
}
