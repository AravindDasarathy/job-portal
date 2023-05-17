<%--
  Created by IntelliJ IDEA.
  User: aravind
  Date: 24/04/23
  Time: 5:26 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.jobportal.entity.Application" %>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/base.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/job_seeker.css">
        <title>JOB APPLICANTS</title>
    </head>
    <body>
        <div class="top-bar">
            <h2>Applicants</h2>
            <div class="buttons">
                <a href="${pageContext.request.contextPath}/job_recruiter">Back</a>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
        <div class="jobs-container">
            <c:choose>
                <c:when test="${empty job_applicants}">
                    <p class="bold-text">No Applicants.</p>
                </c:when>

                <c:otherwise>
                    <c:forEach items="${job_applicants}" var="jobApp">
                        <div class="job">
                            <h3>${jobApp.fullName}</h3>
                            <p>${jobApp.pronouns}</p>
                            <p>${jobApp.email}</p>
                            <p>${jobApp.phone}</p>
                            <p>${jobApp.linkedin}</p>
                            <p>${jobApp.github}</p>
                            <p>${jobApp.additionalInfo}</p>

                            <form method="post" action="/job_recruiter/applicants/${jobApp.id}">
                                <label for="status">Status</label>

                                <select name="status" id="status">
                                    <c:forEach items="<%=Application.ApplicationStatus.values()%>" var="status">
                                        <option
                                            value="${status}"
                                            ${status == jobApp.status ? 'selected' : ''}>
                                                ${status}
                                        </option>
                                    </c:forEach>
                                </select>

                                <input type="submit" value="Update">
                            </form>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
