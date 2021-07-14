
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="text"/>

<fmt:message key="registration.title" var="text_registration_title"/>
<fmt:message key="registration.register" var="text_registration_register"/>
<fmt:message key="registration.valid_sign_password" var="text_registration_valid_sign_password"/>
<fmt:message key="registration.password" var="text_registration_password"/>
<fmt:message key="registration.repeat_password" var="text_registration_repeat_password"/>
<fmt:message key="registration.city" var="text_registration_city"/>
<fmt:message key="registration.region" var="text_registration_region"/>
<fmt:message key="registration.firstname" var="text_registration_firstname"/>
<fmt:message key="registration.lastname" var="text_registration_lastname"/>
<fmt:message key="registration.phone" var="text_registration_phone"/>

<fmt:message key="registration.ivan" var="text_registration_ivan"/>
<fmt:message key="registration.ivanov" var="text_registration_ivanov"/>

<fmt:message key="region1" var="text_region1"/>
<fmt:message key="region2" var="text_region2"/>
<fmt:message key="region3" var="text_region3"/>
<fmt:message key="region4" var="text_region4"/>
<fmt:message key="region5" var="text_region5"/>
<fmt:message key="region6" var="text_region6"/>
<fmt:message key="registration.message_data_not_correct" var="test"/>

<c:if test="${not empty requestScope.message}">
    <fmt:message key="${requestScope.message}" var="text_message"/>
</c:if>

<html>
<head>
    <title>${text_registration_title}</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/styles1.css" rel="stylesheet" media="all"/>
</head>
<body  style="background-image: url(../image/building_3_c1.jpg);
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
                <h2 class="mt-5">${text_message}</h2>
            </div>
        </div>
    </div>
</c:if>

<form action="${pageContext.request.contextPath}/controller" method="post" class="registration_form">

    <h1 style="text-align: center">${text_registration_title}</h1>
    <div>
        <input type="text" placeholder="${text_registration_ivan}" id="Firstname" name="firstName" maxlength="20"
               required value="${requestScope.firstName}">
        <label for="Firstname">${text_registration_firstname}</label>
    </div>
    <div>
        <input type="text" placeholder="${text_registration_ivanov}" id="Lastname" name="lastName" maxlength="20"
               required value="${requestScope.lastName}">
        <label for="Lastname">${text_registration_lastname}</label>
    </div>
    <div>
        <input type="password" placeholder="${text_registration_password}" id="Password" name="password" required minlength="6" maxlength="20">
        <label for="Password">${text_registration_password}</label>
    </div>
    <div>
        <input type="password" placeholder="${text_registration_repeat_password}" id="psw-repeat" name="psw-repeat"
               required minlength="6" maxlength="20">
        <label for="psw-repeat">${text_registration_repeat_password}</label>
    </div>

    <%--    <fieldset>--%>
    <%--        <legend>Gender</legend>--%>
    <%--        <div>--%>
    <%--            <input type="radio" name="gender" value="male" id="gendermale">--%>
    <%--            <label for="gendermale"> Male</label>--%>
    <%--        </div>--%>
    <%--        <div>--%>
    <%--            <input type="radio" name="gender" value="female" id="genderfemale" checked>--%>
    <%--            <label for="genderFemale"> Female</label>--%>
    <%--        </div>--%>
    <%--    </fieldset>--%>
    <%--    <div>--%>
    <%--        <input type="date" id="Birth-date" name="Birth-date" required>--%>
    <%--        <label for="Birth-date">Birth date</label>--%>
    <%--    </div>--%>
    <div>
        <input type="email" placeholder="e-mail-adress@gmail.com" id="emailinput" name="eMail" required maxlength="45"
               value="${requestScope.eMail}">
        <label for="emailinput">E-mail</label>
    </div>
    <div>
        <label for="phone">${text_registration_phone}</label>
        <input type="tel" id="phone" placeholder="+375293333333" name="phone" pattern="\+375[0-9]{9}" required   minlength="13" maxlength="13"
               value="${requestScope.phone}">
    </div>

    <div>
        <label for="Region">${text_registration_region}</label>
        <select name="region" id="Region" required value="${requestScope.region}">
            <option value="MINSK_REGION">${text_region1}</option>
            <option value="HOMYEL_REGION">${text_region2}</option>
            <option value="MAHILOU_REGION">${text_region3}</option>
            <option value="VITEBSK_REGION">${text_region4}</option>
            <option value="HRODNA_REGION">${text_region5}</option>
            <option value="BREST_REGION">${text_region6}</option>

        </select>
    </div>
    <div>
        <textarea id="City" name="city" required  minlength="2" maxlength="60" value="${requestScope.city}"></textarea>
        <label for="City">${text_registration_city}</label>
    </div>
    <%--    <div class="checkbox">--%>
    <%--        <input type="checkbox" name="agreement" id="agree" required>--%>
    <%--        <label for="agree"> I accept the terms in the <a href="https://www.google.com"> license agreement </a> </label>--%>
    <%--    </div>--%>

    <div>
        <input type="hidden" name="command" value="register">
        <input type="submit" value="${text_registration_register}">
    </div>
</form>
<footer class="custom-footer">
    <jsp:include page="footer1.jsp"/>
</footer>
</body>
</html>



