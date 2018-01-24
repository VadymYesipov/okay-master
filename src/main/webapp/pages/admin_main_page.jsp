<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ua.khpi.yesipov.project.persistence.MySqlDAOFactory" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.BrandDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.domain.Brand" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.QualityDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.domain.Quality" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.CarDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.domain.Car" %>
<%@ page import="ua.khpi.yesipov.project.persistence.domain.Person" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.PersonDAO" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 021 21.01.18
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin</title>
</head>
<body style="background-color: ghostwhite">
<header style="text-align: center;">
    <h1>Welcome</h1>
    <br/>
</header>
<div align="center">
    <div>
        <form name="addForm" action="/carAdder" method="post">
            <fieldset>
                <br/>
                <%! private MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory(); %>
                <%! private BrandDAO brandDAO = mySqlDAOFactory.getBrandDAO(); %>
                <%! private List<Brand> brands  = (List<Brand>) brandDAO.select(); %>
                <table>
                    <tr>
                        <td>
                            Brand:
                            <select name="selectBrands">
                                <% for (Brand brand : brands) { %>
                                <option> <%= brand.getName() %> </option>
                                <% } %>
                            </select>
                        </td>
                        <td>
                            Model:
                            <input type="text" name="model" required/>
                        </td>
                        <%! private QualityDAO qualityDAO = mySqlDAOFactory.getQualityDAO(); %>
                        <%! private List<Quality> qualities  = (List<Quality>) qualityDAO.select(); %>
                        <td>
                            Quality:
                            <select name="selectQualities">
                                <% for (Quality quality : qualities) { %>
                                <option> <%= quality.getName() %> </option>
                                <% } %>
                            </select>
                        </td>
                        <td>
                            Price:
                            <input type="text" name="price" required/>
                        </td>
                    </tr>
                </table>
                <br/>
                <input type="submit" name="addCar" value="Add the car"/>
            </fieldset>
        </form>
    </div>

    <div style="margin-top: 50px">
        <form action="/carChanger" method="post">
            <br/>
            Please, choose a car:
            <select name="choiceOfCars">
                <% for (Car car : carDAO.selectAllCars()) { %>
                <option> <%= "id: " + car.getId() + " — " + car.getBrand().getName() + " " + car.getModel() + ", quality: " +
                        car.getQuality().getName() + "; ordered:" + car.getIsOrdered() + " — " + car.getPrice() + "$" %> </option>
                <% } %>
            </select>
            <br/>
            <br/>
            <table>
                <tr>
                    <td>
                        Brand:
                        <select name="selectBrands">
                            <option/>
                            <% for (Brand brand : brands) { %>
                            <option> <%= brand.getName() %> </option>
                            <% } %>
                        </select>
                    </td>
                    <td>
                        Model:
                        <input type="text" name="modelChange" placeholder="Type in a model"/>
                    </td>
                    <td>
                        Quality:
                        <select name="selectQualities">
                            <option/>
                            <% for (Quality quality : qualities) { %>
                            <option> <%= quality.getName() %> </option>
                            <% } %>
                        </select>
                    </td>
                    <td>
                        Price:
                        <input type="text" name="priceChange" placeholder="Type in a price"/>
                    </td>
                </tr>
            </table>
            <br/>
            <input type="submit" name="editCar" value="Edit the car"/>
        </form>
    </div>

    <div style="margin-top: 50px">
        <br/>
        <form name="deleteForm" action="/carDeleter" method="post">
            <%! private CarDAO carDAO = mySqlDAOFactory.getCarDAO(); %>
            <table>
                <tr>
                    <td>
                        <select name="choiceOfCars">
                            <% for (Car car : carDAO.selectAllCars()) { %>
                            <option> <%= "id: " + car.getId() + " — " + car.getBrand().getName() + " " + car.getModel() + ", quality: " +
                                    car.getQuality().getName() + "; ordered:" + car.getIsOrdered() + " — " + car.getPrice() + "$" %> </option>
                            <% } %>
                        </select>
                    </td>
                    <td>
                        <input type="submit" name="deleteCar" value="Delete the car">
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div style="margin-top: 50px">
        <br/>
        <form name="blockUnblockForm" action="/blockUnblock">
            <%! private PersonDAO personDAO = mySqlDAOFactory.getPersonDAO(); %>
            <table>
                <tr>
                    <td>
                        <select name="choiceOfCustomers">
                            <% for (Person person : personDAO.selectPersons()) { %>
                            <option> <%= "id: " + person.getId() + " — login: " + person.getLogin() + "; password: " +
                                    person.getPassword() + "; " + person.getFirstName() + " " + person.getMiddleName() + " " +
                                    person.getLastName() + " " + person.getBirthday() + "; blocked: " + person.getIsBlocked() %> </option>
                            <% } %>
                        </select>
                    </td>
                    <td>
                        <input type="submit" name="blockUnblock" value="Block/Unblock the customer">
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div style="margin-top: 50px">
        <br/>
        <br/>
        <form name="registerForm" action="/register" method="post" onclick="changeTime()">
            <script>
                function changeTime() {
                    var birthday = registerForm.birthdayField;

                    var today = new Date();
                    var dd = today.getDate();
                    var mm = today.getMonth() + 1; //January is 0!
                    var yyyy = today.getFullYear() - 18;
                    if (dd < 10) {
                        dd = '0' + dd
                    }
                    if (mm < 10) {
                        mm = '0' + mm
                    }
                    today = yyyy + '-' + mm + '-' + dd;
                    birthday.max = today;
                }
            </script>
            <fieldset>
                <table>
                    <tr>
                        <td>
                            First name:
                            <input type="text" name="firstNameField" placeholder="Type in a first name" required>
                        </td>
                        <td>
                            Middle name:
                            <input type="text" name="middleField" placeholder="Type in a middle name" required>
                        </td>
                        <td>
                            Last Name:
                            <input type="text" name="lastNameField" placeholder="Type in a last name" required>
                        </td>
                        <td>
                            Birthday:
                            <input type="date" name="birthdayField" placeholder="Type in a birthday" required>
                        </td>
                        <td>
                            Login:
                            <input type="text" name="loginField" placeholder="Type in a login" required>
                        </td>
                        <td>
                            Password:
                            <input type="password" name="passwordField" placeholder="Type in a password" required>
                        </td>
                    </tr>
                </table>
                <br/>
                <input type="submit" name="register" value="Register the new manager">
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
