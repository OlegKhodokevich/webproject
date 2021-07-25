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


<%--<c:choose>--%>
<%--    <c:when test="${sessionScope.executor.executorOption.s}"></c:when>--%>
<%--</c:choose>--%>
<%--<script>--%>
<%--    function getKeySpecialization(value) {--%>
<%--        let str;--%>
<%--        switch (value.toLowerCase()) {--%>
<%--            case "electrical":--%>
<%--                str = "specialization.electrical";--%>
<%--                break;--%>
<%--            case "plumbing":--%>
<%--                str = "specialization.plumbing";--%>
<%--                break;--%>
<%--            case "plastering":--%>
<%--                str = "specialization.plastering";--%>
<%--                break;--%>
<%--            case "laying_tiles":--%>
<%--                str = "specialization.laying_tiles";--%>
<%--                break;--%>
<%--            case "painting":--%>
<%--                str = "specialization.painting";--%>
<%--                break;--%>
<%--            case "wallpapering":--%>
<%--                str = "specialization.wallpapering";--%>
<%--                break;--%>
<%--            case "cement_floor":--%>
<%--                str = "specialization.cement_floor";--%>
<%--                break;--%>
<%--            case "floor_covering":--%>
<%--                str = "specialization.floor_covering";--%>
<%--                break;--%>
<%--            case "carpentry_work":--%>
<%--                str = "specialization.carpentry_work";--%>
<%--                break;--%>
<%--            case "turnkey_house":--%>
<%--                str = "specialization.turnkey_house";--%>
<%--                break;--%>
<%--            case "roof":--%>
<%--                str = "specialization.roof";--%>
<%--                break;--%>
<%--            case "monolite":--%>
<%--                str = "specialization.monolite";--%>
<%--                break;--%>
<%--            case "bricklaying":--%>
<%--                str = "specialization.bricklaying";--%>
<%--                break;--%>
<%--            case "fasad":--%>
<%--                str = "specialization.fasad";--%>
<%--                break;--%>
<%--            case "landscaping":--%>
<%--                str = "specialization.landscaping";--%>
<%--                break;--%>
<%--            default:--%>
<%--                alert( "Нет таких значений" );--%>
<%--        }--%>
<%--        return str;--%>
<%--    }--%>
<%--</script>--%>


<html>
<head>
    <title>${text_executor_executor_info}</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
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
                <div class="card" style="width: 150px; height: 400px; ">
                    <div class="card-body">
                        <div class="d-flex flex-column align-items-center text-center">
                            <img src="${sessionScope.executor.executorOption.urlPersonalFoto}" alt="image" width="120px"
                                 height="120px">
                            <div class="mt-3">
                                <h4>${sessionScope.executor.firstName} ${sessionScope.executor.lastName}</h4>
                            </div>
                        </div>
                    </div>
                </div>
                <br>

                <div class="col-sm-6 mb-3">
                    <div class="card h-100">
                        <div class="card-body">
                            <h6 class="d-flex align-items-center mb-3">${text_executor_skills}</h6>
                            <c:if test="${sessionScope.executor.executorOption.skills != null}">
                                <c:forEach var="skill" items="${sessionScope.executor.executorOption.skills}">

                                    <%--                                    <fmt:message key="" var="spec"/>--%>
                                    <div>
                                        <small>${skill.specialization} </small><small> ${skill.cost} </small><small>${skill.measure}</small>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card mb-3">
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
                        <div class="col-sm-12">
                            <a class="btn btn-info " target="__blank"
                               href="https://www.bootdey.com/snippets/view/profile-edit-data-and-skills">Edit</a>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>


<%--<script>--%>
<%--    function getKeySpecialization(value) {--%>
<%--        let str;--%>
<%--        switch (value.toLowerCase()) {--%>
<%--            case "electrical":--%>
<%--                str = "specialization.electrical";--%>
<%--                break;--%>
<%--            case "plumbing":--%>
<%--                str = "specialization.plumbing";--%>
<%--                break;--%>
<%--            case "plastering":--%>
<%--                str = "specialization.plastering";--%>
<%--                break;--%>
<%--            case "laying_tiles":--%>
<%--                str = "specialization.laying_tiles";--%>
<%--                break;--%>
<%--            case "painting":--%>
<%--                str = "specialization.painting";--%>
<%--                break;--%>
<%--            case "wallpapering":--%>
<%--                str = "specialization.wallpapering";--%>
<%--                break;--%>
<%--            case "cement_floor":--%>
<%--                str = "specialization.cement_floor";--%>
<%--                break;--%>
<%--            case "floor_covering":--%>
<%--                str = "specialization.floor_covering";--%>
<%--                break;--%>
<%--            case "carpentry_work":--%>
<%--                str = "specialization.carpentry_work";--%>
<%--                break;--%>
<%--            case "turnkey_house":--%>
<%--                str = "specialization.turnkey_house";--%>
<%--                break;--%>
<%--            case "roof":--%>
<%--                str = "specialization.roof";--%>
<%--                break;--%>
<%--            case "monolite":--%>
<%--                str = "specialization.monolite";--%>
<%--                break;--%>
<%--            case "bricklaying":--%>
<%--                str = "specialization.bricklaying";--%>
<%--                break;--%>
<%--            case "fasad":--%>
<%--                str = "specialization.fasad";--%>
<%--                break;--%>
<%--            case "landscaping":--%>
<%--                str = "specialization.landscaping";--%>
<%--                break;--%>
<%--            default:--%>
<%--                alert( "Нет таких значений" );--%>
<%--        }--%>
<%--        return str;--%>
<%--    }--%>
<%--</script>--%>
<footer class="custom-footer">
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
