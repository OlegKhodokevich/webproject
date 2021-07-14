<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 04.07.2021
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="text"/>

<fmt:message key="header.home" var="text_header_home"/>
<fmt:message key="header.admin" var="text_header_admin"/>


<html>
<head>
<link href="../css/custom_styles.css" rel="stylesheet"/>
</head>
<body>

<nav class="footer custom-footer" style="height: 100px">
    <div class="container">
        <div class="col-md-5">
            <h3 style="color: black" class="text-muted">&copy; ${text_header_home}</h3>
        </div>
        <div class="col-md-7">
            <h5 style="color: darkgray"> ${text_header_admin}</h5>
            <span style="color: darkgray">+375(29)3372547</span>
        </div>
    </div>
</nav>
</body>
</html>