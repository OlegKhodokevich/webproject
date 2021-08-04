
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<fmt:message key="error.error404.title" var="text_error_404_title"/>
<fmt:message key="error.error404" var="text_error_404"/>


<html>
<head>
    <title>${text_error_404_title}</title>
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<jsp:include page="/pages/header.jsp"/>

<div class="container py-2 mt-5 label_window text-md-center">
    <h3 class="mb-0 ml-3" style="color: black">${text_error_404}!</h3>
</div>


<footer class="custom-footer">
    <jsp:include page="/pages/footer.jsp"/>
</footer>
</body>
</html>