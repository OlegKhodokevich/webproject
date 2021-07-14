<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 03.07.2021
  Time: 9:33
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="text"/>

<html>
<head>
    <c:if test="${not empty sessionScope.locale}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:if>
    <fmt:setBundle basename="text"/>

    <fmt:message key="header.home" var="text_header_home"/>
    <fmt:message key="project.executors" var="text_project_executors"/>
    <fmt:message key="project.customers" var="text_project_customers"/>
    <fmt:message key="header.admin" var="text_header_admin"/>
    <fmt:message key="project.orders" var="text_project_orders"/>
    <fmt:message key="profile.my_profile" var="text_profile_my_profile"/>
    <fmt:message key="profile.my_orders" var="text_profile_my_orders"/>
    <fmt:message key="profile.my_contracts" var="text_profile_my_contracts"/>
    <fmt:message key="profile.configuration" var="text_profile_configuration"/>
    <fmt:message key="profile.log_out" var="text_profile_log_out"/>

    <fmt:message key="profile.my_revokes" var="text_profile_my_revokes"/>
    <fmt:message key="header.logging" var="text_header_logging"/>
    <fmt:message key="header.registration" var="text_header_registration"/>


    <!-- Bootstrap core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">


</head>
<body style="height: 100px">


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<nav class="navbar navbar-expand-lg navbar-light bg-light" style="height: 100px">
    <div class="container-fluid">
        <a class="navbar-brand" href="/controller?command=go_to_main">${text_header_home}</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Link</a>
                </li>


                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        Dropdown
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">Action</a></li>
                        <li><a class="dropdown-item" href="#">Another action</a></li>
                        <li>
                            <hr class="dropdown-divider">
                        </li>
                        <li><a class="dropdown-item" href="#">Something else here</a></li>
                    </ul>
                </li>


                <li class="nav-item">
                    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                </li>


            </ul>
            <ul class="navbar-nav border-right">

                <li class="nav-link active text-md-center">
                    <a class="nav-link" href="/controller?command=set_locale&locale=ru_RU" style="color: black">RU</a>
                </li>
                <li class="nav-link active text-md-center">
                    <a class="nav-link" href="/controller?command=set_locale&locale=en_US" style="color: black">EN</a>
                </li>
            </ul>
            <li class="nav border-right">
                <a class="nav" style="width: 100px" aria-disabled="true"></a>
            </li>
            <ul class="navbar-nav border-right">
                <li class="nav-link active">
                    <a class="nav-link" href="/controller?command=all_orders"
                       style="color: black;">${text_project_orders}</a>
                </li>
                <li class="nav-link active">
                    <a class="nav-link" href="/controller?command=go_to_error404"
                       style="color: black">${text_project_executors}</a>
                </li>
            </ul>
            <form class="d-flex align-items-md-center">
                <input class="form-control me-2 align-items-md-center float-md-none" type="search" placeholder="Search"
                       aria-label="Search">
                <button class="btn btn-outline-success align-bottom" type="submit">Search</button>
            </form>

            <c:choose>
                <c:when test="${empty sessionScope.activeUser}">
                    <li class="nav-link active border-right text-center">
                        <a class="nav-link" href="/controller?command=go_to_sign_in"
                           style="color: black">${text_header_logging}</a>
                    </li>
                </c:when>
                <c:when test="${sessionScope.activeUserRole == 'CUSTOMER'}">
                    <li class="nav-item dropdown navbar-nav me-auto mb-2 mb-lg-0">
                        <a class="nav-link dropdown-toggle" href="#" id="profileCustomer" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                                ${text_profile_my_profile}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="profileCustomer">
                            <li><a class="dropdown-item" href="#">${text_profile_configuration}</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="/controller?command=find_my_orders">${text_profile_my_orders}</a></li>

                            <li><a class="dropdown-item" href="#">${text_profile_my_contracts}</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="/controller?command=log_out">${text_profile_log_out}</a></li>
                        </ul>
                    </li>

                </c:when>
                <c:when test="${sessionScope.activeUserRole == 'EXECUTOR'}">
                    <li class="nav-item dropdown navbar-nav me-auto mb-2 mb-lg-0">
                        <a class="nav-link dropdown-toggle" href="#" id="profileExecutor" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                                ${text_profile_my_profile}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="profileExecutor">
                            <li><a class="dropdown-item" href="#">${text_profile_configuration}</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#">${text_profile_my_contracts}</a></li>

                            <li><a class="dropdown-item" href="#">${text_profile_my_revokes}</a></li>

                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#">${text_profile_log_out}</a></li>
                        </ul>
                    </li>
                </c:when>

                <c:when test="${sessionScope.activeUserRole == 'ADMIN'}">
                    <li class="nav-item dropdown navbar-nav me-auto mb-2 mb-lg-0">
                        <a class="nav-link dropdown-toggle" href="#" id="profileAdmin" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                                ${text_profile_my_profile}
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="profileAdmin">
                            <li><a class="dropdown-item" href="#">${text_profile_configuration}</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#">${text_profile_my_contracts}</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="nav-link" href=#>${text_profile_log_out}</a></li>

                        </ul>
                    </li>
                </c:when>
            </c:choose>
        </div>
    </div>
</nav>

</body>
</html>