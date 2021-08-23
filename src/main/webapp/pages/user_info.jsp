<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<fmt:message key="registration.firstname" var="text_registration_firstname"/>
<fmt:message key="registration.lastname" var="text_registration_lastname"/>
<fmt:message key="registration.city" var="text_registration_city"/>
<fmt:message key="registration.region" var="text_registration_region"/>
<fmt:message key="registration.phone" var="text_registration_phone"/>

<fmt:message key="user.edit_user" var="text_user_edit_user"/>
<fmt:message key="user.user_info" var="text_user_user_info"/>

<html>
<head>
    <title>${text_user_user_info}</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/custom_card.css" rel="stylesheet"/>
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
        <div class="main-body">
            <div class="row gutters-sm">
                <br>
                <div class="col-md-8">
                    <div class="card mb-3 custom-card-user">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">${text_registration_firstname}</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    ${sessionScope.user.firstName}
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">${text_registration_lastname}</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    ${sessionScope.user.lastName}
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">Email</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    ${sessionScope.user.EMail}
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0"> ${text_registration_phone}</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    ${sessionScope.user.phone}
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">${text_registration_region}</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    ${sessionScope.user.region}
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-sm-3">
                                    <h6 class="mb-0">${text_registration_city}</h6>
                                </div>
                                <div class="col-sm-9 text-secondary">
                                    ${sessionScope.user.city}
                                </div>
                            </div>
                            <hr>
                            <c:if test="${sessionScope.activeUser.idUser eq sessionScope.user.idUser or sessionScope.activeUserRole eq 'ADMIN'}">
                                <div class="col-sm-12">
                                    <a class="btn btn-success" href="/controller?command=prepare_edit_user&userId=${sessionScope.user.idUser}" style="width: 250px;align-self: center;margin-bottom: 20px;">${text_user_edit_user}</a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>

