<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.BrandDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.domain.Brand" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.QualityDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.domain.Quality" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.CarDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.domain.Car" %>
<%@ page import="ua.khpi.yesipov.project.persistence.domain.Person" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.PersonDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.impl.MySQLBrandDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.impl.MySQLQualityDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.impl.MySQLCarDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.impl.MySQLPersonDAO" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 021 21.01.18
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="res" />
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Admin</title>
</head>
<body style="background-color: ghostwhite">
<header style="text-align: center;">
    <h1><fmt:message key="welcome"/></h1>
    <br/>
</header>
<jsp:include page="pages/language_part.jsp"></jsp:include>
<div align="center">
    <div>
        <form name="addForm" action="/carAdder" method="post">
            <fieldset>
                <br/>
                <%! private BrandDAO brandDAO = new MySQLBrandDAO(); %>
                <%! private List<Brand> brands  = (List<Brand>) brandDAO.select(); %>
                <table>
                    <tr>
                        <td>
                            <fmt:message key="brand"/>
                            <select name="selectBrands">
                                <% for (Brand brand : brands) { %>
                                <option> <%= brand.getName() %> </option>
                                <% } %>
                            </select>
                        </td>
                        <td>
                            <fmt:message key="model"/>
                            <input type="text" name="model" placeholder="<fmt:message key="typeModel"/>" required/>
                        </td>
                        <%! private QualityDAO qualityDAO = new MySQLQualityDAO(); %>
                        <%! private List<Quality> qualities  = (List<Quality>) qualityDAO.select(); %>
                        <td>
                            <fmt:message key="quality"/>
                            <select name="selectQualities">
                                <% for (Quality quality : qualities) { %>
                                <option> <%= quality.getName() %> </option>
                                <% } %>
                            </select>
                        </td>
                        <td>
                            <fmt:message key="price"/>
                            <input type="number" name="price" placeholder="<fmt:message key="typePrice"/>" min="10" required/>
                        </td>
                    </tr>
                </table>
                <br/>
                <input type="submit" name="addCar" value="<fmt:message key="addCar"/>"/>
            </fieldset>
        </form>
    </div>

    <div style="margin-top: 50px">
        <form action="/carChanger" method="post">
            <br/>
            <fmt:message key="chooseCar"/>
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
                        <fmt:message key="brand"/>
                        <select name="selectBrands">
                            <option/>
                            <% for (Brand brand : brands) { %>
                            <option> <%= brand.getName() %> </option>
                            <% } %>
                        </select>
                    </td>
                    <td>
                        <fmt:message key="model"/>
                        <input type="text" name="modelChange" placeholder="<fmt:message key="typeModel"/>"/>
                    </td>
                    <td>
                        <fmt:message key="quality"/>
                        <select name="selectQualities">
                            <option/>
                            <% for (Quality quality : qualities) { %>
                            <option> <%= quality.getName() %> </option>
                            <% } %>
                        </select>
                    </td>
                    <td>
                        <fmt:message key="price"/>
                        <input type="number" name="priceChange" min="10" placeholder="<fmt:message key="typePrice"/>"/>
                    </td>
                </tr>
            </table>
            <br/>
            <input type="submit" name="editCar" value="<fmt:message key="editCar"/>"/>
        </form>
    </div>

    <div style="margin-top: 50px">
        <br/>
        <form name="deleteForm" action="/carDeleter" method="post">
            <%! private CarDAO carDAO = new MySQLCarDAO(); %>
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
                        <input type="submit" name="deleteCar" value="<fmt:message key="deleteCar"/>">
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div style="margin-top: 50px">
        <br/>
        <form name="blockUnblockForm" action="/blockUnblock">
            <%! private PersonDAO personDAO = new MySQLPersonDAO(); %>
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
                        <input type="submit" name="blockUnblock" value="<fmt:message key="blockUnblock"/>">
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
                            <fmt:message key="firstName"/>
                            <input type="text" name="firstNameField" placeholder="<fmt:message key="typeFirstName"/>" pattern="^[A-ZА-Я][a-zа-яё]+$" required>
                        </td>
                        <td>
                            <fmt:message key="middleName"/>
                            <input type="text" name="middleField" placeholder="<fmt:message key="typeMiddleName"/>" pattern="^[A-ZА-Я][_a-zа-яё]+$" required>
                        </td>
                        <td>
                            <fmt:message key="lastName"/>
                            <input type="text" name="lastNameField" placeholder="<fmt:message key="typeLastName"/>" pattern="^[A-ZА-ЯЁ][_a-zA-Zа-яА-ЯёЁ]+$"required>
                        </td>
                        <td>
                            <fmt:message key="birthday"/>
                            <input type="date" name="birthdayField" placeholder="<fmt:message key="typeBirthday"/>" required>
                        </td>
                        <td>
                            <fmt:message key="login"/>
                            <input type="text" name="loginField" placeholder="<fmt:message key="typeLogin"/>" pattern="[_a-zA-Zа-яА-ЯёЁ]+$" required>
                        </td>
                        <td>
                            <fmt:message key="password"/>
                            <input type="password" name="passwordField" placeholder="<fmt:message key="typePassword"/>" required
                                   pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$">
                        </td>
                    </tr>
                </table>
                <br/>
                <input type="submit" name="register" value="<fmt:message key="registerManager"/>">
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
