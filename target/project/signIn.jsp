<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 015 15.01.18
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<!-- %@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> -->
<!-- %@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> --><!--internationalization-->
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sign In</title>
    <link rel="stylesheet" type="text/css" href="style/style.css">
</head>
<body>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="res" />
<header class="signInHeader">
    <h1><fmt:message key="welcome"/></h1>
</header>
<jsp:include page="pages/language_part.jsp"></jsp:include>
<div>
    <form class="signInForm" action="/signIn" method="post">
        <fieldset class="signInFieldSet">
            <legend align="center"><fmt:message key="legendSignIn"/></legend>
            <fmt:message key="login"/> <br /><input type='text' name='login' required pattern="[_a-zA-Zа-яА-ЯёЁ]+$" /><p/>
            <fmt:message key="password"/>
            <br /> <input type='password' name='password' required
                          pattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$" /><p/>
            <br/><input class="submit" type="submit" name="signIn" value="<fmt:message key="SignIn"/>" /> <p/>
            <br/>
            <fmt:message key="tip"/>
            <br/>
            <a href="signUp.jsp" target="_top"><fmt:message key="SignUp"/></a>
        </fieldset>
    </form>
</div>

</body>

</html>
