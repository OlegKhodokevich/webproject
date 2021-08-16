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

<fmt:message key="executor.executor_info" var="text_executor_executor_info"/>
<fmt:message key="executor.unp" var="text_executor_unp"/>
<fmt:message key="executor.description" var="text_executor_description"/>
<fmt:message key="executor.number_contracts_in_progress" var="text_executor_number_contracts_in_progress"/>
<fmt:message key="executor.number_completion_contracts" var="text_executor_number_completion_contracts"/>
<fmt:message key="executor.skills" var="text_executor_skills"/>
<fmt:message key="executor.average_mark" var="text_executor_average_mark"/>

<fmt:message key="user.edit_user" var="text_user_edit_user"/>

<fmt:message key="revoke.title" var="text_revoke_title"/>
<fmt:message key="revoke.executor_revoke" var="text_revoke_executor_revoke"/>

<fmt:message key="revoke.rating" var="text_ravoke_reting"/>
<html>
<head>
    <title>${text_executor_executor_info}</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/custom_card.css" rel="stylesheet"/>
    <link href="../css/revoke_style.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>
<div class="container">
    <div class="main-body">
        <div class="row gutters-sm">
            <div class="col-md-4 mb-3">
                <div class="card custom-card-executor-main" style=" ">
                    <div class="card-body">
                        <div class="d-flex flex-column align-items-center text-center">
                            <img src="${sessionScope.executor.executorOption.urlPersonalFoto}" alt="image" width="120px"
                                 height="120px">
                            <div class="mt-3">
                                <h4>${sessionScope.executor.firstName} ${sessionScope.executor.lastName}</h4>
                                <br/>
                                <h6 class="d-flex align-items-center mb-3">${text_executor_skills}</h6>
                                <c:if test="${sessionScope.executor.executorOption.skills != null}">
                                    <c:forEach var="skill" items="${sessionScope.executor.executorOption.skills}">
                                        <fmt:message key="${skill.specialization.key}" var="text_specialisation"/>
                                        <div>
                                            <p style="text-align: left">${text_specialisation}
                                                <span> ${skill.cost} </span><span>${skill.measure}</span></p>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br>
            <div class="col-md-8">
                <div class="card mb-3 custom-card-executor-additional">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">${text_registration_firstname}</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${sessionScope.executor.firstName}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">${text_registration_lastname}</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${sessionScope.executor.lastName}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">Email</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${sessionScope.executor.EMail}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0"> ${text_registration_phone}</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${sessionScope.executor.phone}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">${text_registration_region}</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                <fmt:message key="${applicationScope.regionMap.get(sessionScope.executor.region)}"
                                             var="text_region"/>
                                ${text_region}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">${text_registration_city}</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${sessionScope.executor.city}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">${text_executor_unp}</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${sessionScope.executor.executorOption.unp}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">${text_executor_description}</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${sessionScope.executor.executorOption.description}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">${text_executor_number_contracts_in_progress}</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${sessionScope.executor.executorOption.numberContractsInProgress}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">${text_executor_number_completion_contracts}</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${sessionScope.executor.executorOption.numberCompletionContracts}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">${text_executor_average_mark}</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">
                                ${sessionScope.executor.executorOption.truncatedMark}
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-sm-3">
                                <h6 class="mb-0">${text_ravoke_reting}</h6>
                            </div>
                            <div class="col-sm-9 text-secondary">

                                    <c:forEach items="${applicationScope.markList}" varStatus="loop">
                                        <c:choose>
                                            <c:when test="${(loop.index + 1) <= sessionScope.executor.executorOption.averageMark}">
                                                <span class="fa fa-star checked"></span>
                                            </c:when>
                                            <c:when test="${(loop.index + 1) > sessionScope.executor.executorOption.averageMark}">
                                                <span class="fa fa-star"></span>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>

                                <a href="/controller?command=executor_revoke&executorId=${sessionScope.executor.idUser}">
                                    ${text_revoke_title}
                                </a>
                            </div>
                        </div>
                        <hr>
                        <c:if test="${sessionScope.activeUser !=null and sessionScope.activeUser.idUser eq sessionScope.executor.idUser or sessionScope.activeUser.role eq 'ADMIN'}">
                            <div class="col-sm-12">
                                <a class="btn btn-info "
                                   href="/controller?command=prepare_edit_user&userId=${sessionScope.executor.idUser}">${text_user_edit_user}</a>
                            </div>
                        </c:if>
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
