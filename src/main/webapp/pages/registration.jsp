<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mes" uri="custom tag message writer" %>

<fmt:setLocale value="${sessionScope.locale}"/>
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
<fmt:message key="registration.admin_password" var="text_registration_admin_password"/>
<fmt:message key="registration.change_password" var="text_registration_change_password"/>
<fmt:message key="registration.executor_registration" var="text_registration_executor_registration"/>
<fmt:message key="registration.correct_password" var="text_registration_correct_password"/>

<fmt:message key="user.old_password" var="text_user_old_password"/>
<fmt:message key="user.confirm_changes" var="user_confirm_changes"/>
<fmt:message key="profile.edit_info" var="user_profile_edit_info"/>

<fmt:message key="region1" var="text_region1"/>
<fmt:message key="region2" var="text_region2"/>
<fmt:message key="region3" var="text_region3"/>
<fmt:message key="region4" var="text_region4"/>
<fmt:message key="region5" var="text_region5"/>
<fmt:message key="region6" var="text_region6"/>

<html>
<head>
    <title>${text_registration_title}</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/styles1.css" rel="stylesheet" media="all"/>
    <link href="../css/main.css" rel="stylesheet"/>
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>
<div class="main-content">

    <div class="container">
        <div class="container payment_window mb-1 pt-1 pb-1">
            <div class="container mt-1">
                <h2 class="mt-5"><mes:messageTag/></h2>
            </div>
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/controller" method="post" class="registration_form">
        <c:choose>
            <c:when test="${sessionScope.activeUserRole eq 'ADMIN' or sessionScope.activeUser.idUser eq sessionScope.userId}">
                <h1 style="text-align: center">${user_profile_edit_info}</h1>
            </c:when>
            <c:when test="${sessionScope.activeUserRole eq 'GUEST'}">
                <h1 style="text-align: center">${text_registration_title}</h1>
            </c:when>
        </c:choose>
        <div>
            <input type="text" placeholder="${text_registration_ivan}" id="Firstname" name="firstName" minlength="1"
                   maxlength="20" required value="${sessionScope.firstName}">
            <label for="Firstname">${text_registration_firstname}</label>
        </div>
        <div>
            <input type="text" placeholder="${text_registration_ivanov}" id="Lastname" name="lastName" minlength="1"
                   maxlength="20" required value="${sessionScope.lastName}">
            <label for="Lastname">${text_registration_lastname}</label>
        </div>
        <c:if test="${sessionScope.activeUserRole ne 'GUEST'}">
            <div>
                <input type="password" placeholder="" id="ConfirmedPassword" name="confirmedPassword" required
                       minlength="6" maxlength="20">
                <c:choose>
                    <c:when test="${sessionScope.activeUserRole eq 'ADMIN'}">
                        <label for="ConfirmedPassword">${text_registration_admin_password}</label>
                    </c:when>

                    <c:when test="${sessionScope.activeUserRole ne 'ADMIN'}">
                        <label for="ConfirmedPassword">${text_user_old_password}</label>
                    </c:when>
                </c:choose>
            </div>
        </c:if>

        <%--        <c:if test="${sessionScope.activeUserRole ne 'GUEST'}">--%>
        <div class="${sessionScope.activeUserRole eq 'GUEST'  ? 'hidden' : null}">
            <input type="checkbox" onchange="toggleChangePassword()" id="ChangePassword"
                   style="order: 1; flex-basis: auto; width: 20px;" name="changePassword" value="on">
            <label for="ChangePassword">${text_registration_change_password}</label>
        </div>
        <%--        </c:if>--%>

        <div class="change-password-input ${sessionScope.activeUserRole ne 'GUEST'  ? 'hidden' : null}">
            <input type="password" placeholder="${text_registration_password}" id="Password"
                   name="password" ${sessionScope.activeUserRole eq 'GUEST' ? 'required' : null}
                   minlength="6" maxlength="20">
            <label for="Password">${text_registration_password}</label>
            <p style="order: 2; margin-top: 0; margin-left: 30%; margin-bottom: 0; width: 65%; font-size: 12px;">${text_registration_correct_password}</p>
        </div>
        <div class="change-password-input ${sessionScope.activeUserRole ne 'GUEST'  ? 'hidden' : null}">
            <input type="password" placeholder="${text_registration_repeat_password}" id="Psw-repeat" name="psw-repeat"
            ${sessionScope.activeUserRole eq 'GUEST' ? 'required' : null} minlength="6" maxlength="20">
            <label for="Psw-repeat">${text_registration_repeat_password}</label>
        </div>
        <div>
            <input type="email" placeholder="e-mail-adress@gmail.com" id="Emailinput" name="eMail" required
                   minlength="5"
                   maxlength="45"
                   value="${sessionScope.eMail}">
            <label for="Emailinput">E-mail</label>
        </div>
        <div>
            <label for="Phone">${text_registration_phone}</label>
            <input type="tel" id="Phone" placeholder="+375293333333" name="phone" pattern="\+375[0-9]{9}" required
                   minlength="13" maxlength="13"
                   value="${sessionScope.phone}">
        </div>
        <div>
            <label for="Region">${text_registration_region}</label>
            <select name="region" id="Region" required>
                <c:forEach var="reginType" items="${applicationScope.regionList}">
                    <fmt:message key="${reginType.key}" var="text_region"/>
                    <option value="${reginType.name()}" ${sessionScope.region eq reginType ? 'selected' : null}>${text_region}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <input type="text" id="City" name="city" required minlength="2" maxlength="60" value="${sessionScope.city}">
            <label for="City">${text_registration_city}</label>
        </div>
        <c:choose>
            <c:when test="${sessionScope.activeUserRole ne 'GUEST'}">
                <div>
                    <input type="hidden" name="command" value="edit_user">
                    <input type="hidden" name="userId" value="${sessionScope.userId}">
                    <input type="submit" value="${user_confirm_changes}">
                </div>
            </c:when>
            <c:when test="${sessionScope.activeUserRole eq 'GUEST'}">
                <div>
                    <input type="hidden" name="command" value="register">
                    <input type="submit" value="${text_registration_register}">
                </div>
            </c:when>
        </c:choose>
        <div>
            <p style="margin-left: 100px; font-weight: bold; font-size: 20px;">${text_registration_executor_registration}</p>
        </div>
    </form>
</div>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
<c:if test="${sessionScope.activeUserRole ne 'GUEST'}">
    <script>
        function toggleChangePassword() {
            const passwordFields = document.querySelectorAll('.change-password-input');
            passwordFields.forEach(el => el.classList[event.target.checked ? 'remove' : 'add']('hidden'));

        }
    </script>
</c:if>
</body>
</html>



