<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 016 16.01.18
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html" charset="UTF-8">
    <title>Sign Up</title>
</head>
<body style="background-color: ghostwhite">
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="res" />
<header style="text-align: center;">
    <br/>
    <br/>
    <br/>
    <br/>
    <h1><fmt:message key="SignUp"/></h1>
    <br/><br/>
</header>
<jsp:include page="language_part.jsp"></jsp:include>
<div align="center">
    <form align="center" action="/signUp" name="myForm" method="post">
        <fieldset align="center" style="margin-left: 500px; margin-right: 500px;">
            <legend align="center"><fmt:message key="legendSignUp"/></legend>
            <fmt:message key="login"/> <br /><input type='text' name='login' required/><p/>
            <fmt:message key="password"/> <br /> <input type='password' name='password' required/><p/>
            <fmt:message key="firstName"/> <br /><input type='text' name='firstName' required/><p/>
            <fmt:message key="middleName"/> <br /><input type='text' name='middleName' required/><p/>
            <fmt:message key="lastName"/> <br /><input type='text' name='lastName' required/><p/>
            <fmt:message key="birthday"/> <br /><input type="date" name="birthday" required><p/>
            <br/><input style="background-color: chartreuse;" type="submit" name="signUp" value="<fmt:message key="SignUp"/>" > <p/>
        </fieldset>
    </form>
</div>
</body>
</html>
