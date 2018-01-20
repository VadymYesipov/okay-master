<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 015 15.01.18
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<!-- %@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> -->
<!-- %@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> --><!--internationalization-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sign In</title>
</head>

<body style="background-color: ghostwhite">

<header style="text-align: center;">
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <h1>Welcome</h1>
</header>
<div style="text-align: center;">
    <form style="align-content: center; padding-top: 100px; padding-left: 200px; padding-right: 200px; padding-bottom: 100px;" action="/signIn" method="post">
        <fieldset style="align-content: center; margin-left: 500px; margin-right: 500px";>
            <legend align="center">Enter your login and password</legend>
            Login <br /><input type='text' name='login' required /><p/>
            Password <br /> <input type='password' name='password' required/><p/>
            <br/><input style="background-color: chartreuse" type="submit" name="signIn" value="Sign In"/><p/>
            <br/>
            If you haven't gotten registered, you can follow this link
            <br/>
            <a href="pages/signUp.jsp" target="_top">Sign up</a>
        </fieldset>
    </form>
</div>

</body>

</html>
