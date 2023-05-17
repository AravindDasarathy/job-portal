<%-- 
    Document   : login
    Created on : 16 Apr 2023, 09:20:02
    Author     : aravind
    --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/login.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/base.css">
        <title>Login</title>
    </head>
    <body>
    <div class="login-container">
        <h1 class="login-title border-bottom">LOG IN</h1>
        <form
            class="login-form border-bottom"
            action="${pageContext.request.contextPath}/login"
            method="post"
        >
            <div class="form-group">
                <label for="user_email"> Email </label>
                <input type="text" name="user_email" id="user_email">
            </div>
            <div class="form-group">
                <label for="user_password"> Password </label>
                <input type="password" name="user_password" id="user_password">
            </div>
            <div class="form-group">
                <div class="buttons">
                    <input type="submit" value="Log In">
                    <a href="${pageContext.request.contextPath}/signup">Register</a>
                </div>
            </div>
        </form>
        <div class="form-group">
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
        </div>
    </div>
    </body>
</html>