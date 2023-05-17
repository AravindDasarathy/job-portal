<%--
  Created by IntelliJ IDEA.
  User: aravind
  Date: 23/04/23
  Time: 6:44 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>JOB APPLICATION</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/base.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/signup.css">
    </head>
    <body>
        <div class="top-bar">
            <h2>Apply here</h2>
            <div class="buttons">
                <a href="${pageContext.request.contextPath}/job_seeker">Back</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
        <div class="signup-container">
            <h1 class="signup-title border-bottom">JOB DESCRIPTION</h1>

            <div class="signup-form">
                <p class="form-group">${application.job.title}, ${application.job.company.name}</p>
                <p class="form-group">Posted Date: ${application.job.postedDate}</p>
                <p class="form-group">Company Description: ${application.job.company.description}</p>
                <p class="form-group">Job Description: ${application.job.description}</p>
                <p class="form-group">Pay: ${application.job.salary} $</p>
            </div>
        </div>
        <div class="signup-container">
            <h1 class="signup-title border-bottom">APPLY</h1>

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
                action="${pageContext.request.contextPath}/job_seeker/apply/${application.job.id}"
                method="post"
                modelAttribute="application">
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <form:input path="fullName" type="text" id="fullName"/>
                </div>

                <div class="form-group">
                    <label for="pronouns">Pronouns</label>
                    <form:input path="pronouns" type="text" id="pronouns" />
                </div>

                <div class="form-group">
                    <label for="email">Email</label>
                    <form:input path="email" type="text" id="email" />
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <form:input path="phone" type="text" id="phone" />
                </div>

                <div class="form-group">
                    <label for="linkedin">LinkedIn URL</label>
                    <form:input path="linkedin" type="text" id="linkedin" />
                </div>

                <div class="form-group">
                    <label for="github">Github URL</label>
                    <form:input path="github" type="text" id="github" />
                </div>

                <div class="form-group">
                    <label for="additionalInfo">Additional Information</label>
                    <form:textarea path="additionalInfo" type="text" id="additionalInfo" />
                </div>

                <div class="form-group">
                    <div class="buttons">
                        <input type="submit" value="Apply">
                        <a href="${pageContext.request.contextPath}/job_seeker">Back</a>
                    </div>
                </div>
            </form:form>
        </div>
    </body>
</html>
