<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="text"/>

<fmt:message key="header.home" var="text_header_home"/>
<fmt:message key="project.executors" var="text_project_executors"/>
<fmt:message key="project.customers" var="text_project_customers"/>
<fmt:message key="header.admin" var="text_header_admin"/>
<fmt:message key="project.orders" var="text_project_orders"/>
<fmt:message key="profile.my_profile" var="text_profile_my_profile"/>
<fmt:message key="profile.my_orders" var="text_profile_my_orders"/>
<fmt:message key="profile.my_contracts" var="text_profile_my_contracts"/>
<fmt:message key="profile.offer" var="text_profile_offer"/>
<fmt:message key="profile.my_offer" var="text_profile_my_offer"/>
<fmt:message key="profile.configuration" var="text_profile_configuration"/>
<fmt:message key="profile.log_out" var="text_profile_log_out"/>

<fmt:message key="profile.my_revokes" var="text_profile_my_revokes"/>
<fmt:message key="header.logging" var="text_header_logging"/>
<fmt:message key="header.registration" var="text_header_registration"/>
<fmt:message key="user.admin_all_user_title" var="text_user_admin_all_user_title"/>



<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="../css/custom_card.css" rel="stylesheet" type="text/css"/>
</head>
<body style="height: 100px">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<nav class="navbar navbar-expand-lg navbar-light bg-light" style="height: 100px;">
    <div class="container-fluid bg-light">
        <a class="navbar-brand" href="/controller?command=go_to_main">${text_header_home}</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent" style="justify-content: space-between;">
            <ul class="navbar-nav border-right ml-5">

                <li class="nav-link active text-md-center">
                    <a class="nav-link" href="/controller?command=set_locale&locale=ru_RU" style="color: black">RU</a>
                </li>
                <li class="nav-link active text-md-center">
                    <a class="nav-link" href="/controller?command=set_locale&locale=en_US" style="color: black">EN</a>
                </li>
            </ul>
            <ul class="navbar-nav border-right">
                <li class="nav-link active">
                    <a class="nav-link" href="/controller?command=all_orders"
                       style="color: black;">${text_project_orders}</a>
                </li>
                <li class="nav-link active">
                    <a class="nav-link" href="/controller?command=all_executors"
                       style="color: black">${text_project_executors}</a>
                </li>
            </ul>

            <c:choose>
                <c:when test="${sessionScope.activeUserRole eq 'GUEST'}">
                    <li class="nav-link active border-right text-center">
                        <a class="nav-link" href="/controller?command=go_to_sign_in"
                           style="color: black">${text_header_logging}</a>
                    </li>
                </c:when>
                <c:when test="${sessionScope.activeUserRole eq 'CUSTOMER'}">
                    <li class="nav-item dropdown navbar-nav mb-2 mb-lg-0">
                        <a class="nav-link dropdown-toggle" href="#" id="profileCustomer" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                                ${text_profile_my_profile}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="profileCustomer">
                            <li><a class="dropdown-item"
                                   href="/controller?command=find_user_info_details&userId=${sessionScope.activeUser.idUser}">${text_profile_configuration}</a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item"
                                   href="/controller?command=find_my_orders">${text_profile_my_orders}</a></li>
                            <li><a class="dropdown-item" href="/controller?command=find_offer_for_user&userId=${sessionScope.activeUserId}">${text_profile_offer}</a></li>

                            <li><a class="dropdown-item" href="/controller?command=find_contract_by_userid&userId=${sessionScope.activeUserId}">${text_profile_my_contracts}</a></li>

                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="/controller?command=log_out">${text_profile_log_out}</a>
                            </li>
                        </ul>
                    </li>

                </c:when>
                <c:when test="${sessionScope.activeUserRole eq 'EXECUTOR'}">
                    <li class="nav-item dropdown navbar-nav mb-2 mb-lg-0">
                        <a class="nav-link dropdown-toggle" href="#" id="profileExecutor" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                                ${text_profile_my_profile}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="profileExecutor">
                            <li><a class="dropdown-item"
                                   href="/controller?command=find_executor_info_details&executorId=${sessionScope.activeUser.idUser}">${text_profile_configuration}</a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>

                            <li><a class="dropdown-item" href="/controller?command=find_my_offer&executorId=${sessionScope.activeUserId}">${text_profile_my_offer}</a></li>

                            <li><a class="dropdown-item" href="/controller?command=find_contract_by_userid&executorId=${sessionScope.activeUserId}">${text_profile_my_contracts}</a></li>

                            <li><a class="dropdown-item" href="#">${text_profile_my_revokes}</a></li>

                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="/controller?command=log_out">${text_profile_log_out}</a>
                            </li>
                        </ul>
                    </li>
                </c:when>

                <c:when test="${sessionScope.activeUserRole eq 'ADMIN'}">
                    <li class="nav-item dropdown navbar-nav mb-2 mb-lg-0">
                        <a class="nav-link dropdown-toggle" href="#" id="profileAdmin" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                                ${text_profile_my_profile}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="profileAdmin">
                            <li><a class="dropdown-item" href="/controller?command=find_user_info_details&userId=${sessionScope.activeUser.idUser}">${text_profile_configuration}</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="/controller?command=all_users">${text_user_admin_all_user_title}</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="nav-link" href="/controller?command=log_out">${text_profile_log_out}</a></li>

                        </ul>
                    </li>
                </c:when>
            </c:choose>
        </div>
    </div>
</nav>
</body>
</html>