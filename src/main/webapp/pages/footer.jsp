<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="text"/>

<fmt:message key="header.home" var="text_header_home"/>
<fmt:message key="header.admin" var="text_header_admin"/>

<html>
<head>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
</head>
<body>

<footer class="footer custom-footer">
    <div class="container">
        <div class="col-md-5">
            <h3 style="color: black" class="text-muted">&copy; ${text_header_home}</h3>
        </div>
        <div class="col-md-7">
            <h5 style="color: darkgray"> ${text_header_admin}</h5>
            <span style="color: darkgray">+375(29)3372547</span>
        </div>
    </div>
</footer>
<script src="../js/date_formatter.js"></script>
</body>
</html>