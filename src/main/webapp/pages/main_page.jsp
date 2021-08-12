<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>
<%@ taglib prefix="mes" uri="custom tag message writer" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<fmt:message key="header.home" var="text_header_home"/>
<fmt:message key="main.title" var="text_main_title"/>
<fmt:message key="main.description" var="text_main_description"/>
<fmt:message key="header.logging" var="text_header_logging"/>

<html>
<head>
    <title>main</title>
    <link href="${pageContext.request.contextPath}../css/custom_styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}../css/custom_image.css" rel="stylesheet"/>
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>
<main>
    <div class="position-relative overflow-hidden text-center bg-light">
        <div class="p-lg-5 mx-auto">
            <h1 class="display-4 fw-normal"><ctg:welcome/></h1>
            <h1 class="display-4 fw-normal"><mes:messageTag/></h1>
            <h1 class="display-4 fw-normal">${text_header_home}</h1>
            <p class="lead fw-normal">${text_main_description}</p>
            <c:if test="${sessionScope.activeUserRole eq 'GUEST'}">
                <a class="btn btn-outline-secondary" href="/controller?command=go_to_sign_in">${text_header_logging}</a>
            </c:if>
        </div>
        <div class="product-device shadow-sm d-none d-md-block"></div>
        <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col">
                <img src="/image/design2.jpg" class="custom-image-main">
            </div>
            <div class="col">
                <img src="/image/design3.jpg" class="custom-image-main">
            </div>
        </div>
        <div class="row">
            <div class="col">
                <img src="/image/design4.jpg" class="custom-image-main">
            </div>
            <div class="col">
                <img src="/image/design5.jpg" class="custom-image-main">
            </div>
        </div>
    </div>
</main>

<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
