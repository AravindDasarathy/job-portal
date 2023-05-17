<%--
  Created by IntelliJ IDEA.
  User: aravind
  Date: 25/04/23
  Time: 3:42 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/base.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/signup.css">
        <title>JOB FORM</title>
    </head>
    <body>
        <div class="top-bar">
            <h2>Post a Job</h2>
            <div class="buttons">
                <a href="${pageContext.request.contextPath}/job_recruiter">View Applications</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
        <div class="signup-container">
            <h1 class="signup-title border-bottom">JOB FORM</h1>

            <c:if test="${not empty errors}">
                <div class="error border-bottom">
                    <ul>
                        <c:forEach items="${errors}" var="error">
                            <li>${error.defaultMessage}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form:form
                class="signup-form border-bottom"
                action="${pageContext.request.contextPath}/job_recruiter/job"
                method="post"
                modelAttribute="job">
                <div class="form-group">
                    <label for="title">Title</label>
                    <form:input path="title" type="text" id="title"/>
                </div>

                <div class="form-group">
                    <label for="description">Description</label>
                    <form:input path="description" type="text" id="description" />
                </div>

                <div class="form-group">
                    <label for="salary">Salary</label>
                    <form:input path="salary" type="text" id="salary" />
                </div>

                <div class="form-group">
                    <div class="buttons">
                        <input type="submit" value="Submit">
                        <a href="${pageContext.request.contextPath}/job_recruiter">Back</a>
                    </div>
                </div>
            </form:form>
        </div>
    </body>
</html>
