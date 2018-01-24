<%@ page import="ua.khpi.yesipov.project.persistence.MySqlDAOFactory" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.OrderDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.domain.Order" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 023 23.01.18
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Manager</title>
</head>
<header style="text-align: center;">
    <h1>Welcome</h1>
    <br/>
</header>
<body style="background-color: ghostwhite">
<div align="center">
    <br/>
    <br/>
    <br/>
    <form name="strictForm" action="/cancelServlet" method="post">
        Choose a possible future order:</br></br>
        <%! private MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory(); %>
        <%! private OrderDAO orderDAO = mySqlDAOFactory.getOrderDAO(); %>
        <select name="possibleOrders">
            <% for (Order order : orderDAO.selectFutureOrders()) { %>
            <option> <%= order.getId() + ": login: " + order.getPerson().getLogin() + ", name: " + order.getPerson().getFirstName() +
                    ", surname: " + order.getPerson().getLastName() + "| car: " + order.getCar().getBrand().getName() + " " + order.getCar().getModel() +
                    ", quality - " + order.getCar().getQuality().getName() + ", since: " + order.getSince() + ", till: " + order.getTill() + "| driver: " +
                    order.getDriver().getName() + " " + order.getDriver().getSurname() + "| price: " + order.getPrice() + "$"%> </option>
            <% } %>
        </select>
        </br>
        </br>
        <table>
            <tr>
                <td>
                    Point the reason:
                </td>
                <td></td>
            </tr>
            <tr>
                <td>
                    <textarea name="reason" style="resize: none; height: 100px; width: 400px" placeholder="Type in a reason"></textarea>
                </td>
                <td>
                    <input type="submit" value="Cancel the order"/>
                </td>
            </tr>
        </table>
    </form>
    <br/>
    <br/>
    <br/>
    <form name="damageForm" action="/punisher" method="post">
        <table>
            <tr>
                <td>
                    Choose a yesterday returned car:
                </td>
                <td>
                    Type in a fine (if it's damaged):
                </td>
                <td></td>
            </tr>
            <tr>
                <td>
                    <select name="returnedCars">
                        <% for (Order order : orderDAO.selectPastOrders()) { %>
                        <option> <%= order.getId() + ": login: " + order.getPerson().getLogin() + ", name: " + order.getPerson().getFirstName() +
                                ", surname: " + order.getPerson().getLastName() + "| car: " + order.getCar().getBrand().getName() + " " + order.getCar().getModel() +
                                ", quality - " + order.getCar().getQuality().getName() + ", since: " + order.getSince() + ", till: " + order.getTill() + "| driver: " +
                                order.getDriver().getName() + " " + order.getDriver().getSurname() + "| price: " + order.getPrice() + "$"%> </option>
                        <% } %>
                    </select>
                </td>
                <td>
                    <input type="number" name="number" min="100" name="fine"/>
                </td>
                <td>
                    <input type="submit" value="Register the car/Fine the customer">
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
