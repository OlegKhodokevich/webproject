
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="text"/>
<c:if test="${not empty requestScope.message}">
    <fmt:message key="logging.welcome_user" var="text_welcome_user"/>
</c:if>

<html>
<head>
    <title>main</title>
    <link href="/static/css/custom_styles.css" rel="stylesheet"/>
</head>
<body  style="background-image: url(../static/image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>
<c:if test="${not empty requestScope.message}">
    <div class="container">
        <div class="container payment_window mb-5 pt-3 pb-5">
            <div class="container mt-5">
                <h2 class="mt-5">${requestScope.message}${text_welcome_user}</h2>
            </div>
        </div>
    </div>
</c:if>
<li class="nav-link active text-md-center">
    <a class="nav-link" href="/controller?command=go_to_order_page" style="color: black">Orders</a>
</li>
<li class="nav-link active text-md-center">
    <a class="header_button" href="/controller?command=log_out">exit</a>
</li>
<li class="nav-link active text-md-center">
    <a class="header_button" href="order_info.jsp">Order Info</a>
</li>
<footer class="custom-footer">

    <jsp:include page="footer1.jsp"/>
</footer>
</body>
</html>
