<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<body style="background-color: ghostwhite">
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="res" />
<header style="text-align: center;">
    <h1><fmt:message key="welcome"/></h1>
    <br/>
</header>
<jsp:include page="language_part.jsp"></jsp:include>
<div align="center">
    <br/>
    <br/>
    <br/>
    <form name="strictForm" action="/cancelServlet" method="post">
        <fmt:message key="strictForm"/></br></br>
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
                    <fmt:message key="reasonPointer"/>
                </td>
                <td></td>
            </tr>
            <tr>
                <td>
                    <textarea name="reason" style="resize: none; height: 100px; width: 400px" placeholder="<fmt:message key="TypeReason"/>"></textarea>
                </td>
                <td>
                    <input type="submit" value="<fmt:message key="cancelOrder"/>"/>
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
                    <fmt:message key="damageForm"/>
                </td>
                <td>
                    <fmt:message key="typeFine"/>
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
                    <input type="number" name="number" min="100"/>
                </td>
                <td>
                    <input type="submit" value="<fmt:message key="registerOrFine"/>" />
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>