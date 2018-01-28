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
</head>
<body style="background-color: ghostwhite">
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="res" />
<header style="text-align: center;">
    <br/>
    <br/>
    <br/>
    <br/>
    <h1><fmt:message key="welcome"/></h1>
    <br/><br/>
</header>
<jsp:include page="language_part.jsp"></jsp:include>
<div style="text-align: center;">
    <form style="align-content: center; padding-top: 100px; padding-left: 200px; padding-right: 200px; padding-bottom: 100px;" action="/signIn" method="post">
        <fieldset style="align-content: center; margin-left: 500px; margin-right: 500px;">
            <legend align="center"><fmt:message key="legendSignIn"/></legend>
            <fmt:message key="login"/> <br /><input type='text' name='login' required /><p/>
            <fmt:message key="password"/> <br /> <input type='password' name='password' required/><p/>
            <br/><input style="background-color: chartreuse" type="submit" name="signIn" value="<fmt:message key="SignIn"/>" /> <p/>
            <br/>
            <fmt:message key="tip"/>
            <br/>
            <a href="pages/signUp.jsp" target="_top"><fmt:message key="SignUp"/></a>
        </fieldset>
    </form>
</div>

</body>

</html>
