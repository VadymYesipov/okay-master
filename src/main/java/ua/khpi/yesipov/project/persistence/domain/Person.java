package ua.khpi.yesipov.project.persistence.domain;

import lombok.Data;

import java.sql.Date;

@Data
public class Person {

    private Integer id;

    private Role role;

    private String firstName;
    private String middleName;
    private String lastName;

    private Date birthday;

    private String login;
    private String password;

    private Integer isBlocked;
}
