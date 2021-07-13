<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

    <fmt:setLocale value="${sessionScope.locale}"/>

<fmt:setBundle basename="text"/>

<fmt:message key="header.home" var="text_header_home"/>
<fmt:message key="project.executors" var="text_project_executors"/>
<fmt:message key="project.customers" var="text_project_customers"/>
<fmt:message key="header.admin" var="text_header_admin"/>
<fmt:message key="project.orders" var="text_project_orders"/>
<fmt:message key="header.logging" var="text_header_logging"/>
<fmt:message key="header.registration" var="text_header_registration"/>

<fmt:message key="registration.password" var="text_registration_password"/>
<fmt:message key="registration.title" var="text_registration_title"/>

<fmt:message key="logging.title" var="text_logging_title"/>
<fmt:message key="logging.sign_in" var="text_logging_sign_in"/>

<c:if test="${not empty param['message']}">
    <fmt:message key="${param['message']}" var="text_message"/>
</c:if>

<html>
<head>
    <title>${text_logging_title}</title>
    <link href="/static/css/custom_styles.css" rel="stylesheet"/>
    <link href="/static/css/styles1.css" rel="stylesheet"/>
</head>
<body style="background-image: url(../static/image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>

<c:if test="${not empty param['message']}">
    <div class="container">
        <div class="container payment_window mb-5 pt-3 pb-5">
            <div class="container mt-5">
                <h2 class="mt-5">${text_message}</h2>
            </div>
        </div>
    </div>
</c:if>

<form action="${pageContext.request.contextPath}/controller" method="post" class="registration_form">
    <h1 style="text-align: center">${text_logging_title}</h1>
    <div>
        <input type="email" placeholder="e-mail-adress@gmail.com" id="emailinput" name="eMail" required>
        <label for="emailinput">E-mail</label>
    </div>
    <div>
        <input type="password" placeholder="Пароль" id="password" name="password" required>
        <label for="password">${text_registration_password}</label>
    </div>

    <div>
        <input type="hidden" name="command" value="sign_in">
        <input type="submit" value="${text_logging_sign_in}">
    </div>

    <c:if test="${not empty requestScope.error}">
        <div>
            <div class="container payment_window mb-5 pt-3 pb-5">
                <div class="container mt-5">
                    <h2 class="mt-5">${text_error}</h2>
                </div>
            </div>
        </div>
    </c:if>

</form>

<form action="/controller" method="post" class="registration_form">
    <div>
        <input type="hidden" name="command" value="go_to_registration"/>
        <input type="submit" value="${text_registration_title}">
    </div>
</form>
<footer class="custom-footer">
    <jsp:include page="footer1.jsp"/>
</footer>
</body>
</html>
