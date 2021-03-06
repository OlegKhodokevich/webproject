<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

    <fmt:setLocale value="${sessionScope.locale}"/>

<fmt:setBundle basename="text"/>

<fmt:message key="error.tech_problems" var="text_error_tech_problems"/>
<fmt:message key="error.tech_come_later" var="text_error_tech_come_later"/>
<fmt:message key="error.error" var="text_error_error"/>


<html>
<head>
    <title>${text_error_error}</title>
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>


<div class="container payment_window mb-5 pt-3 pb-5">
    <div class="container mt-5">
        <h2 class="mt-5">${text_error_tech_problems}</h2>
        <h2 class="mt-1">${text_error_tech_come_later}</h2>
    </div>
</div>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>

</body>
</html>