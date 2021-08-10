<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mes" uri="custom tag message writer" %>

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

<html>
<head>
    <title>${text_logging_title}</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/styles1.css" rel="stylesheet"/>
    <link href="../css/custom_button.css" rel="stylesheet" media="all"/>
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>

<div class="container">
    <div class="container payment_window pt-1 pb-1">
        <div class="container">
            <h2 class="mt-1"><mes:messageTag/></h2>
        </div>
    </div>
</div>

<form action="${pageContext.request.contextPath}/controller" method="post" class="registration_form">
    <h1 style="text-align: center">${text_logging_title}</h1>
    <div>
        <input type="email" placeholder="e-mail-adress@gmail.com" id="Emailinput" name="eMail" required>
        <label for="Emailinput">E-mail</label>
    </div>
    <div>
        <input type="password" placeholder="Пароль" id="Password" name="password" required>
        <label for="Password">${text_registration_password}</label>
    </div>

    <div>
        <input type="hidden" name="command" value="sign_in">
        <input type="submit" value="${text_logging_sign_in}">
    </div>
    <a class="custom-button-register" href="/controller?command=go_to_registration" id="Button">${text_registration_title}</a>
    <br/>
    <br/>
</form>

<footer class="custom-footer">
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
