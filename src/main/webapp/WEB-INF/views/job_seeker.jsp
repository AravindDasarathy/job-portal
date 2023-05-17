<%--
    Document   : job_seeker
    Created on : 16 Apr 2023, 12:53:13
    Author     : aravind
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/base.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/job_seeker.css">
        <title>JOB SEEKER</title>
    </head>

    <body>
        <div class="top-bar">
            <h2>Welcome Job Seeker!</h2>
            <div class="buttons">
                <a href="${pageContext.request.contextPath}/job_seeker/${user.id}/applications">View Applications</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>

        <form action="/job_seeker" method="GET">
            <div class="search">
                <div>
                    <input type="text" name="search" placeholder="Search for jobs..." value="${search}">
                    <input type="submit" value="Search">
                </div>
            </div>
        </form>

        <div class="jobs-container">
            <c:choose>
                <c:when test="${empty jobs}">
                    <p class="bold-text">No jobs found.</p>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${jobs}" var="job">
                        <div class="job">
                            <h3>${job.title}</h3>
                            <p>${job.company.name}</p>
                            <p>${job.postedDate}</p>
                            <a href="${pageContext.request.contextPath}/job_seeker/apply/${job.id}">Apply</a>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
