<%--
  User: aravind
  Date: 28/04/23
  Time: 12:10 am
--%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/base.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/job_seeker.css">
        <title>JOB APPLICATIONS</title>
    </head>
    <body>
        <div class="top-bar">
            <h2>Applications Page</h2>
            <div class="buttons">
                <a href="${pageContext.request.contextPath}/job_seeker">Back</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
        <div class="jobs-container">
            <c:choose>
                <c:when test="${empty applications}">
                    <p class="bold-text">No Applications.</p>
                </c:when>

                <c:otherwise>
                    <c:forEach items="${applications}" var="jobApp">
                        <div class="job">
                            <h3>${jobApp.fullName}</h3>
                            <p>${jobApp.pronouns}</p>
                            <p>${jobApp.email}</p>
                            <p>${jobApp.phone}</p>
                            <p>${jobApp.linkedin}</p>
                            <p>${jobApp.github}</p>
                            <p>${jobApp.additionalInfo}</p>
                            <p>${jobApp.status}</p>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
