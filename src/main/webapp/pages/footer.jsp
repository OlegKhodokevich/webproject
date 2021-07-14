<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
        language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${locale}" scope="session"/>
<fmt:setBundle basename="text"/>

    <c:if test="${not empty sessionScope.locale}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:if>
    <fmt:setBundle basename="text"/>

    <fmt:message key="header.home" var="text_header_home"/>
    <fmt:message key="project.executors" var="text_project_executors"/>
    <fmt:message key="project.customers" var="text_project_customers"/>
    <fmt:message key="header.admin" var="text_header_admin"/>
    <fmt:message key="project.orders" var="text_project_orders"/>
    <fmt:message key="header.logging" var="text_header_logging"/>
    <fmt:message key="header.registration" var="text_header_registration"/>

    <!-- Bootstrap core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <link rel="stylesheet" href="../css/header.css">

<!-- Footer -->
<footer class="footer custom-footer">
    <!-- Grid container -->
    <div class="container p-4">

        <!-- Section: Text -->
        <section class="mb-4">
            <h1>
                ${text_header_home}
            </h1>
        </section>
        <!-- Section: Text -->

        <!-- Section: Links -->
        <section class="">
            <!--Grid row-->
            <div class="row">
                <!--Grid column-->
                <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                    <h5 class="text-uppercase">Links</h5>

                    <ul class="list-unstyled mb-0">
                        <li>
                            <a href="#!" class="text-white">Link 1</a>
                        </li>
                        <li>
                            <a href="#!" class="text-white">Link 2</a>
                        </li>
                        <li>
                            <a href="#!" class="text-white">Link 3</a>
                        </li>
                        <li>
                            <a href="#!" class="text-white">Link 4</a>
                        </li>
                    </ul>
                </div>
                <!--Grid column-->

                <!--Grid column-->
                <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                    <h5 class="text-uppercase">Links</h5>

                    <ul class="list-unstyled mb-0">
                        <li>
                            <a href="#!" class="text-white">Link 1</a>
                        </li>
                        <li>
                            <a href="#!" class="text-white">Link 2</a>
                        </li>
                        <li>
                            <a href="#!" class="text-white">Link 3</a>
                        </li>
                        <li>
                            <a href="#!" class="text-white">Link 4</a>
                        </li>
                    </ul>
                </div>
                <!--Grid column-->

                <!--Grid column-->
                <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                    <h5 class="text-uppercase">Links</h5>

                    <ul class="list-unstyled mb-0">
                        <li>
                            <a href="#!" class="text-white">Link 1</a>
                        </li>
                        <li>
                            <a href="#!" class="text-white">Link 2</a>
                        </li>
                        <li>
                            <a href="#!" class="text-white">Link 3</a>
                        </li>
                        <li>
                            <a href="#!" class="text-white">Link 4</a>
                        </li>
                    </ul>
                </div>
                <!--Grid column-->

                <!--Grid column-->
                <div class="col-lg-3 col-md-6 mb-4 mb-md-0">
                    <h5 class="text-uppercase">Links</h5>

                    <ul class="list-unstyled mb-0">
                        <li>
                            <a href="#!" class="text-white">Link 1</a>
                        </li>
                        <li>
                            <a href="#!" class="text-white">Link 2</a>
                        </li>
                        <li>
                            <a href="#!" class="text-white">Link 3</a>
                        </li>
                        <li>
                            <a href="#!" class="text-white">Link 4</a>
                        </li>
                    </ul>
                </div>
                <!--Grid column-->
            </div>
            <!--Grid row-->
        </section>
        <!-- Section: Links -->
    </div>
    <!-- Grid container -->

    <!-- Copyright -->
    <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
        © 2020 Copyright:
        <a class="text-white" href="https://mdbootstrap.com/">MDBootstrap.com</a>
    </div>
    <!-- Copyright -->
</footer>
<!-- Footer -->

<%--<body>--%>
<%--<nav class="navbar navbar-expand-lg navbar-light bg-light">--%>
<%--    <div class="container-fluid">--%>
<%--        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo01"--%>
<%--                aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">--%>
<%--            <span class="navbar-toggler-icon"></span>--%>
<%--        </button>--%>
<%--        <div class="collapse navbar-collapse" id="navbarTogglerDemo01">--%>
<%--            <a class="navbar-brand" href="/controller?command=go_to_main">${text_header_home}</a>--%>
            <%--// TODO Admin--%>
            <%--            <ul class="navbar-nav me-auto mb-2 mb-lg-0">--%>
            <%--                <li class="nav-item">--%>
            <%--                    <a class="nav-link active" aria-current="page" href="#">Home</a>--%>
            <%--                </li>--%>
            <%--                <li class="nav-item">--%>
            <%--                    <a class="nav-link" href="#">Link</a>--%>
            <%--                </li>--%>
<%--            </ul>--%>
<%--            <li class="nav-link active border-right">--%>
<%--                <a class="nav-link" href="/controller?command=go_to_error404"--%>
<%--                   style="color: black ">${text_project_orders}</a>--%>
<%--            </li>--%>
<%--            <li class="nav-link active border-right">--%>
<%--                <a class="nav-link" href="/controller?command=go_to_error404"--%>
<%--                   style="color: black ">${text_project_executors}</a>--%>
<%--            </li>--%>
<%--            <form class="d-flex border-right">--%>
<%--                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">--%>
<%--                <button class="btn btn-outline-success" type="submit">Search</button>--%>
<%--            </form>--%>
<%--            <li class="nav-link active border-right py-5">--%>
<%--                <a class="nav-link" href="/controller?command=go_to_log_on"--%>
<%--                   style="color: black">${text_header_logging}</a>--%>
<%--            </li>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</nav>--%>

<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"--%>
<%--        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--</body>--%>

