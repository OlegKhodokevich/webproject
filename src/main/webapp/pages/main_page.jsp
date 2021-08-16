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
<fmt:message key="header.registration" var="text_header_registration"/>

<html>
<head>
    <title>main</title>
    <link href="${pageContext.request.contextPath}../css/custom_styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}../css/custom_image.css" rel="stylesheet"/>
</head>
<body>
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
                    <a class="btn btn-outline-secondary mr-4" href="/controller?command=go_to_sign_in">${text_header_logging}</a>
                    <a class="btn btn-outline-success ml-3" href="/controller?command=go_to_registration">${text_header_registration}</a>
            </c:if>
        </div>
        <div class="product-device shadow-sm d-none d-md-block"></div>
        <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
    </div>

<%--    <h4>Примеры выполненных работ нашими исполнителями</h4>--%>
<%--    <section>--%>
<%--        <div class="gallery row">--%>

<%--            <div class="col-md-3 col-sm-3 col-xs-6">--%>
<%--                <div><a href="../image/foto/design2.jpg"><img src="../image/foto/design2.jpg" alt="спальня в классическом стиле" title="спальня в классическом стиле"/></a></div>--%>
<%--            </div>--%>

<%--            <div class="col-md-3 col-sm-3 col-xs-6">--%>
<%--                <div><a href="../image/foto/design3.jpg"><img src="../image/foto/design3.jpg" alt="гостиная в светлых тонах" title="гостиная в светлых тонах"/></a></div>--%>
<%--            </div>--%>


<%--            <div class="col-md-3 col-sm-3 col-xs-6">--%>
<%--                <div><a href="../image/foto/design4.jpg"><img src="../image/foto/design4.jpg" alt="детская в постельных тонах" title="детская в постельных тонах"/></a></div>--%>
<%--            </div>--%>


<%--            <div class="col-md-3 col-sm-3 col-xs-6">--%>
<%--                <div><a href="../image/foto/design5.jpg"><img src="../image/foto/design5.jpg" alt="светлая спальня" title="светлая спальня"/></a></div>--%>
<%--            </div>--%>

<%--            <div class="col-md-3 col-sm-3 col-xs-6">--%>
<%--                <div><a href="../image/foto/dfoto_533.jpg"><img src="../image/foto/dfoto_533.jpg" alt="гостиная-кухня с зеленыйм акцентом" title="гостиная-кухня с зеленыйм акцентом"/></a></div>--%>
<%--            </div>--%>

<%--            <div class="col-md-3 col-sm-3 col-xs-6">--%>
<%--                <div><a href="../image/foto/dfoto_654.jpg"><img src="../image/foto/dfoto_654.jpg" alt="солнечная кухня" title="солнечная кухня"/></a></div>--%>
<%--            </div>--%>

<%--            <div class="col-md-3 col-sm-3 col-xs-6">--%>
<%--                <div><a href="../image/foto/gl-foto-05.jpg"><img src="../image/foto/gl-foto-05.jpg" alt="уютна лоджия" title="уютна лоджия"/></a></div>--%>
<%--            </div>--%>

<%--            <div class="col-md-3 col-sm-3 col-xs-6">--%>
<%--                <div><a href="../image/foto/gl-foto-10.jpg"><img src="../image/foto/gl-foto-10.jpg" alt="светлая спальня с гардеробной" title="светлая спальня с гардеробной"/></a></div>--%>
<%--            </div>--%>

<%--            <div class="col-md-3 col-sm-3 col-xs-6">--%>
<%--                <div><a href="../image/foto/gl-foto-11.jpg"><img src="../image/foto/gl-foto-11.jpg" alt="спальня в стиле модерн" title="спальня в стиле модерн"/></a></div>--%>
<%--            </div>--%>


<%--        </div>--%>
<%--    </section>--%>




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
