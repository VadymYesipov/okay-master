<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.List" %>
<%@ page import="ua.khpi.yesipov.project.persistence.dao.OrderDAO" %>
<%@ page import="ua.khpi.yesipov.project.persistence.MySqlDAOFactory" %>
<%@ page import="ua.khpi.yesipov.project.persistence.domain.*" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 017 17.01.18
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="res" />
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Customer</title>
</head>
<body style="background-color: ghostwhite">
<header style="text-align: center;">
    <h1><fmt:message key="welcome"/> </h1>
    <br/>
</header>
<jsp:include page="pages/language_part.jsp"></jsp:include>
<br/>
<br/>
<div align="center">
    <div align="center">
        <form name="myForm" action="/selector" method="post">
            <table>
                <tr>
                    <fmt:message key="chooseSelect"/>
                    <td>
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
                    <fmt:message key="brand"/>
                    <td>
                        <select name ="selectByBrand" disabled="disabled">
                            <% for (Brand brand : carDAO.selectAvailableBrands()) { %>
                            <option> <%= brand.getName() %> </option>
                            <% } %>
                        </select>
                    </td>
                    <fmt:message key="quality"/>
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
            <input type="submit" name="showCars" value=<fmt:message key="send"/>
            <br/>
            <br/>
            <table>
                <tr>
                    <td>
                        <fmt:message key="chooseCar"/><br/>
                        <select name="choiceOfCars" onclick="makeSinceAble()">
                            <c:forEach var="car" items="${cars}">
                                <option> <c:out value="${car}" /> </option>
                            </c:forEach>
                        </select>
                    </td>
                    <script>
                        function makeSinceAble() {
                            var since = myForm.since;
                            since.disabled = "";

                            var today = new Date();
                            var dd = today.getDate();
                            var mm = today.getMonth() + 1; //January is 0!
                            var yyyy = today.getFullYear();
                            if (dd < 10) {
                                dd = '0' + dd
                            }
                            if (mm < 10) {
                                mm = '0' + mm
                            }
                            today = yyyy + '-' + mm + '-' + dd;
                            since.min = today;
                        }
                    </script>
                    <td>
                        <fmt:message key="chooseSince"/> <br/>
                        <input type="date" name="since" disabled="disabled" onchange="makeTillAble()">
                    </td>
                    <script>
                        function makeTillAble() {
                            var since = myForm.since;
                            var till = myForm.till;
                            till.disabled = "";
                            till.min = since.value;
                        }
                    </script>
                    <td>
                        <fmt:message key="chooseTill"/><br/>
                        <input type="date" name="till" disabled="disabled" onchange="makeSelectYesNoAble()">
                    </td>
                    <script>
                        function makeSelectYesNoAble() {
                            var cardio = myForm.selectYesNo;
                            cardio.disabled = "";
                        }
                    </script>
                </tr>
            </table>
            <br/>
            <table>
                <tr>
                    <td>
                        <fmt:message key="chooseDrivers"/>
                        <select name="selectYesNo" disabled="disabled" onclick="showDrivers()">
                            <option value="no"> No </option>
                            <option value="yes"> Yes </option>
                        </select>
                    </td>
                    <script>
                        var cardio = myForm.selectYesNo;
                        function showDrivers() {
                            var drivers = myForm.selectDrivers;
                            <jsp:useBean id="driverDAO" class="ua.khpi.yesipov.project.persistence.dao.impl.MySQLDriverDAO"/>
                            var price = myForm.showPrice;
                            if (cardio.selectedIndex === 0) {
                                drivers.disabled = "disabled";
                                price.disabled = "";
                            } else {
                                drivers.disabled = "";
                            }
                        }
                        function showDriverList() {
                            var price = myForm.showPrice;
                            price.disabled = "";
                        }
                        cardio.addEventListener("change", showDrivers());
                    </script>
                    <td>
                        <select name="selectDrivers" disabled="disabled" onclick="showDriverList()">
                            <% for (Driver driver : driverDAO.selectDrivers()) { %>
                            <option> <%= driver.getName() + " " + driver.getSurname() %> </option>
                            <% } %>
                        </select>
                    </td>
                </tr>
            </table>
            <br/>
            <input type="submit" disabled="disabled" name="showPrice" formaction="/priceGetter" value=<fmt:message key="showCost"/> />
            <br/>
            <br/>
            Car's price:
            <input disabled="disabled" name="price" type="text" value="${priceCar}"/>
            <br/>
            <br/>
            <input style="background-color: chartreuse" type="submit" name="makeOrder" formaction="/rent" value=<fmt:message key="makeOrder"/>/>
        </form>
    </div>
    <div style="margin-top: 100px;" align="center">
        <h1><fmt:message key="approvedOrders"/></h1>
        <br/>
        <br/>
        <%! private MySqlDAOFactory mySqlDAOFactory = new MySqlDAOFactory(); %>
        <%! private OrderDAO orderDAO = mySqlDAOFactory.getOrderDAO(); %>
        <%! private List<Order> orders; %>
        <% Person customer = (Person) session.getAttribute("person");
            orders = orderDAO.selectOrders(customer.getId()); %>
        <table border='1' bordercolor="red">
            <tr border="1">
                <th> <fmt:message key="table_brand"/> </th>
                <th> <fmt:message key="table_model"/> </th>
                <th> <fmt:message key="table_quality"/> </th>
                <th> <fmt:message key="driverName"/> </th>
                <th> <fmt:message key="driverSurname"/> </th>
                <th> <fmt:message key="since"/> </th>
                <th> <fmt:message key="till"/> </th>
                <th> <fmt:message key="table_price"/> </th>
            </tr>
            <% for (Order order : orders) { %>
            <tr>
                <td> <%= order.getCar().getBrand().getName() %> </td>
                <td> <%= order.getCar().getModel() %> </td>
                <td> <%= order.getCar().getQuality().getName() %> </td>
                <td> <%= order.getDriver().getName() %> </td>
                <td> <%= order.getDriver().getSurname() %> </td>
                <td> <%= order.getSince() %> </td>
                <td> <%= order.getTill() %>  </td>
                <td> <%= order.getPrice() %>  </td>
            </tr>
            <% } %>
        </table>
    </div>
    <div style="margin-top: 100px;" align="center">
        <h1><fmt:message key="deniedFined"/> </h1>
        <br/>
        <br/>
        <table border='1' bordercolor="red">
            <% Map<String, List<String>> map = (Map<String, List<String>>) session.getServletContext().getAttribute("sessionMap"); %>
            <% String login = ((Person) session.getAttribute("person")).getLogin(); %>
            <% session.setAttribute("reasons", map.get(login)); %>
            <c:forEach var="deniedOrder" items="${reasons}" >
                <tr>
                    <td>
                        <c:out value="${deniedOrder}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
