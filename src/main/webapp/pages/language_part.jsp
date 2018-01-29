<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: palah
  Date: 16.09.2017
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div align="center">
    <c:out value="${language}"/>

    <fmt:setLocale value="${language}" />
    <fmt:setBundle basename="res" />

    <form action="/locale" method="post">
        <select name="loc">
            <option value="ru">Русский</option>
            <option value="en">English</option>
        </select>
        <p></p>
        <input type="submit" value="Submit">
    </form>
</div>
