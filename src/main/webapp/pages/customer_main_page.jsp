<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.OrderDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.MySqlDAOFactory" %>
<%@ page import="ua.khpi.yesipov.project.persistence.domain.*" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 017 17.01.18
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Customer</title>
</head>
<header style="text-align: center;">
    <h1>Welcome</h1>
    <br/>
    <br/>
    <br/>
</header>
<body style="background-color: ghostwhite">
<div align="center">
    <form name="myForm" action="/selector" method="post">
        <table>
            <tr>
                <td>
                    Choose select by
                    <select name="selectCarsBy" onchange="showPrefferedOptions()" >
                        <option value="default"> default </option>
                        <option value="brand"> brand </option>
                        <option value="quality"> quality </option>
                        <option value="sorted model"> sorted model </option>
                        <option value="sorted price"> sorted price </option>
                    </select>
                </td>
                <script>
                    var mySelect = myForm.selectCarsBy;
                    function showPrefferedOptions() {
                        var brandSelect = myForm.selectByBrand;
                        brandSelect.options = null;
                        var qualitySelect = myForm.selectByQuality;
                        qualitySelect.options = null;
                        var cars = myForm.choiceOfCars;
                        if (mySelect.selectedIndex === 1 || mySelect.selectedIndex === 2) {
                            cars.disabled = "disabled";
                            <jsp:useBean id="carDAO" class="ua.khpi.yesipov.project.persistence.dao.impl.MySQLCarDAO"/>
                            if (mySelect.selectedIndex === 1) {
                                qualitySelect.disabled = "disabled";
                                brandSelect.disabled = "";
                            } else {
                                brandSelect.disabled = "disabled";
                                qualitySelect.disabled = "";
                            }
                        } else {
                            brandSelect.disabled = "disabled";
                            qualitySelect.disabled = "disabled";
                            cars.disabled = "";
                        }
                    }
                    mySelect.addEventListener("change", showPrefferedOptions());
                </script>
                <td>
                    <select name ="selectByBrand" disabled="disabled">
                        <% for (Brand brand : carDAO.selectAvailableBrands()) { %>
                        <option value=<%= brand.getName() %>> <%= brand.getName() %> </option>
                        <% } %>
                    </select>
                </td>
                <td>
                    <select name ="selectByQuality" disabled="disabled">
                        <% for (Quality quality : carDAO.selectAvailableQualities()) { %>
                        <option value=<%= quality.getName() %>> <%= quality.getName() %> </option>
                        <% } %>
                    </select>
                </td>
            </tr>
        </table>
        <br/>
        <input type="submit" name="showCars" value="Send to server">
        <br/>
        <br/>
        <table>
            <tr>
                <td>
                    Please, choose a car:<br/>
                    <select name="choiceOfCars">
                        <c:forEach var="car" items="${cars}">
                            <option> <c:out value="${car}"/> </option>
                        </c:forEach>
                    </select>
                </td>
                <td>
                    Please, choose when you will rent the car:<br/>
                    <input type="date" name="since">
                </td>
                <td>
                    Please, choose when you will return the car:<br/>
                    <input type="date" name="till">
                </td>
            </tr>
        </table>
        <br/>
        <br/>
        <table>
            <tr>
                <td>
                    Do you want to rent a driver?
                    <select name="selectYesNo" onchange="showDrivers()">
                        <option value="yes"> Yes </option>
                        <option value="no"> No </option>
                    </select>
                </td>
                <script>
                    var cardio = document.selectYesNo;
                    var drivers = myForm.selectDrivers;
                    function showDrivers() {
                        <jsp:useBean id="driverDAO" class="ua.khpi.yesipov.project.persistence.dao.impl.MySQLDriverDAO"/>
                        if (cardio.selectedIndex === 0) {
                            drivers.disabled = "";
                        } else {
                            drivers.disabled = "disabled";
                        }
                    }
                    cardio.addEventListener("change", showDrivers());
                </script>
                <td>
                    <select name="selectDrivers" disabled="disabled">
                        <% for (Driver driver : driverDAO.selectDrivers()) { %>
                        <option value= <%= driver.getSurname() %>> <%= driver.getName() + " " + driver.getSurname() %> </option>
                        <% } %>
                    </select>
                </td>
            </tr>
        </table>


        <br/>
        <br/>
        <input type="submit" name="showPrice" value="Show a cost of the car"/>
    </form>
    <br/>
    Car's price:
    <input disabled="disabled" type="text" value=""/>
    <br/>
    <br/>
    <br/>
    <input style="background-color: chartreuse" type="submit" name="makeOrder" value="Make the order">
</div>
<div style="margin-top: 100px;" align="center">
    <br/>
    <br/>
    <%! private MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory(); %>
    <%! private OrderDAO orderDAO = mySqlDAOFactory.getOrderDAO(); %>
    <%! private List<Order> orders; %>
    <% Person customer = (Person) session.getAttribute("person");
        orders = orderDAO.selectOrders(customer.getId()); %>
    <table border='1' bordercolor="red">
        <tr border="1">
            <th> brand </th>
            <th> model </th>
            <th> quality </th>
            <th> customer's first name </th>
            <th> customer's middle name </th>
            <th> customer's last name </th>
            <th> customer's birthday </th>
            <th> driver's name </th>
            <th> driver's surname </th>
            <th> since </th>
            <th> till </th>
            <th> price </th>
        </tr>
        <% for (Order order : orders) { %>
        <tr>
            <td> <%= order.getCar().getBrand().getName() %> </td>
            <td> <%= order.getCar().getModel() %> </td>
            <td> <%= order.getCar().getQuality().getName() %> </td>
            <td> <%= order.getPerson().getFirstName() %> </td>
            <td> <%= order.getPerson().getMiddleName() %> </td>
            <td> <%= order.getPerson().getLastName() %> </td>
            <td> <%= order.getPerson().getBirthday() %> </td>
            <td> <%= order.getDriver().getName() %> </td>
            <td> <%= order.getDriver().getSurname() %> </td>
            <td> <%= order.getSince() %> </td>
            <td> <%= order.getTill() %>  </td>
            <td> <%= order.getPrice() %>  </td>
        </tr>
        <% } %>
    </table>
</div>
</body>
</html>
