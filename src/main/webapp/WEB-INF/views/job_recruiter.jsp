<%--
  Author: aravind
  Date: 23/04/23
  Time: 11:13 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix='c'%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/job_seeker.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/base.css">
        <title>JOB SEEKER</title>
    </head>

    <body>
        <div class="top-bar">
            <h2>Welcome Job Recruiter!</h2>
            <div class="buttons">
                <a href="${pageContext.request.contextPath}job_recruiter/job">Post a Job</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>

        <div class="jobs-container">
            <c:choose>
                <c:when test="${empty jobs}">
                    <p class="bold-text">No jobs posted.</p>
                </c:when>

                <c:otherwise>
                    <c:forEach items="${jobs}" var="job">
                        <div class="job">
                            <h3>${job.title}</h3>
                            <p>${job.postedDate}</p>
                            <p>${job.salary} $</p>
                            <a href="${pageContext.request.contextPath}/job_recruiter/job/${job.id}/applicants">
                                View Applicants
                            </a>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
