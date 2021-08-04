<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<html>
<head>
    <title>${text_executor_executor_info}</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/custom_card.css" rel="stylesheet"/>
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
                                        <div>
                                            <p style="text-align: left">${skill.specialization}
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
                                ${sessionScope.executor.region}
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
                                ${sessionScope.executor.executorOption.averageMark}
                            </div>
                        </div>
                        <hr>
                        <c:if test="${sessionScope.activeUser !=null and sessionScope.activeUser.idUser eq sessionScope.executor.idUser or sessionScope.activeUser.role eq 'ADMIN'}">
                            <div class="col-sm-12">
                                <a class="btn btn-info " href="/controller?command=prepare_edit_user&userId=${sessionScope.executor.idUser}">${text_user_edit_user}</a>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<footer class="custom-footer">
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>