<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 016 16.01.18
  Time: 15:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html" charset="UTF-8">
    <title>Sign Up</title>
</head>
<body style="background-color: ghostwhite">
<header style="text-align: center;">
    <br/>
    <br/>
    <br/>
    <br/>
    <h1>Sign up</h1>
</header>
<div style="text-align: center;">
    <form style="align-content: center; padding-top: 100px; padding-left: 200px; padding-right: 200px; padding-bottom: 100px;" action="/signUp" name="myForm" method="post">
        <fieldset style="align-content: center; margin-left: 500px; margin-right: 500px";>
            <legend align="center">Enter your login and password</legend>
            login <br /><input type='text' name='login' required/><p/>
            password <br /> <input type='password' name='password' required/><p/>
            First name <br /><input type='text' name='firstName' required/><p/>
            Middle name <br /><input type='text' name='middleName' required/><p/>
            Last name <br /><input type='text' name='lastName' required/><p/>
            Birthday <br /><input type="date" name="birthday" required><p/>
            <br/><input style="background-color: chartreuse;" type="submit" name="signUp" value="Sign Up"/><p/>
        </fieldset>
    </form>
</div>
</body>
</html>
