<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>

<fmt:setBundle basename="text"/>
<c:if test="${not empty requestScope.message}">
    <fmt:message key="logging.welcome_user" var="text_welcome_user"/>
</c:if>

<html>
<head>
    <title>main</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>

<jsp:include page="header.jsp"/>
<footer class="custom-footer">
    <jsp:include page="footer1.jsp"/>
</footer>
</body>
</html>
