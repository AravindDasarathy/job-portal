<%@ page import="com.jobportal.entity.User" %>
<%--
    User: aravind
    Date: 19/04/23
    Time: 5:31 pm
--%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.jobportal.entity.User" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/base.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/signup.css">
    <title>Sign Up</title>
  </head>
  <body>
    <div class="signup-container">
      <h1 class="signup-title border-bottom">SIGN UP</h1>

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
          action="${pageContext.request.contextPath}/signup"
          method="post"
          modelAttribute="user">
        <div class="form-group">
          <label for="first_name">First Name</label>
          <form:input path="firstName" type="text"  id="first_name"/>
        </div>

        <div class="form-group">
          <label for="last_name">Last Name</label>
          <form:input path="lastName" type="text" id="last_name" />
        </div>

        <div class="form-group">
          <label for="email">Email</label>
          <form:input path="email" type="email" id="email" />
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <form:input path="password" type="password" id="password" />
        </div>

        <div class="form-group">
          <label for="designation">Designation</label>
          <form:input path="designation" type="text" id="designation" />
        </div>

        <div class="form-group">
          <label for="mobile_number">Mobile Number</label>
          <form:input path="mobileNumber" type="tel" id="mobile_number" />
        </div>

        <div class="form-group">
          <label for="user_type"> User Type </label>
          <form:radiobuttons path="userType" items="<%=User.UserType.values()%>" id="user_type" />
        </div>

<%--        <c:choose>--%>
          <c:if test="${companies.size() > 0}">
            <div class="form-group">
              <label for="company_id">Company</label>
              <form:select path="company.id" id="company_id">
                <form:options items="${companies}" itemValue="id" itemLabel="name"/>
                <form:option value="-1" label="Create New"/>
              </form:select>
            </div>
          </c:if>

<%--          <c:otherwise>--%>
            <h3 class="new-div border-bottom">Don't see your company? Create one!</h3>

            <div class="form-group">
              <label for="company_name">Company Name</label>
              <form:input type="text" path="company.name" name="company_name" id="company_name"/>
            </div>

            <div class="form-group">
              <label for="company_description">Company Description</label>
              <form:input type="text" path="company.description" name="company_description" id="company_description"/>
            </div>
<%--          </c:otherwise>--%>
<%--        </c:choose>--%>

        <div class="form-group">
          <div class="buttons">
            <input type="submit" value="Sign Up">
            <a href="${pageContext.request.contextPath}/login">Already a User? Login!</a>
          </div>
        </div>
      </form:form>
    </div>
  </body>
</html>