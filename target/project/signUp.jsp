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
    <link rel="stylesheet" type="text/css" href="style/style.css">
</head>
<body>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="res" />
<header class="signUpHeader">
    <h1><fmt:message key="signUp"/></h1>
</header>
<jsp:include page="pages/language_part.jsp"></jsp:include>
<div align="center">
    <form align="center" class="signInForm" action="/signUp" name="myForm" method="post" onclick="makeBirthday()">
        <script>
            function makeBirthday() {
                var birthday = myForm.birthday;

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
        <fieldset align="center" class="signInFieldSet">
            <legend align="center"><fmt:message key="legendSignUp"/></legend>
            <fmt:message key="login"/> <br /><input type='text' name='login' pattern="[_a-zA-Zа-яА-ЯёЁ]+$" required/><p/>
            <fmt:message key="password"/>
            <br /> <input type='password' name='password' required
                          pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"/><p/>
            <fmt:message key="firstName"/> <br /><input type='text' name='firstName' required pattern="^[A-ZА-Я][a-zа-яё]+$"/><p/>
            <fmt:message key="middleName"/> <br /><input type='text' name='middleName' required pattern="^[A-ZА-Я][_a-zа-яё]+$"/><p/>
            <fmt:message key="lastName"/> <br /><input type='text' name='lastName' required pattern="^[A-ZА-ЯЁ][_a-zA-Zа-яА-ЯёЁ]+$"/><p/>
            <fmt:message key="birthday"/> <br /><input type="date" name="birthday" required ><p/>
            <br/><input class="submit" type="submit" name="signUp" value="<fmt:message key="SignUp"/>" > <p/>
        </fieldset>
    </form>
</div>
</body>
</html>
