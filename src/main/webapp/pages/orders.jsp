<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="abs_path">${pageContext.request.contextPath}</c:set>
<%@ taglib prefix="mes" uri="custom tag message writer" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<fmt:message key="specialization.landscaping" var="text_specialization_landscaping"/>
<fmt:message key="order.search" var="text_order_search"/>
<fmt:message key="order.orders" var="text_order_orders"/>
<fmt:message key="order.specializations" var="text_order_specializations"/>
<fmt:message key="order.empty_list" var="text_order_empty_list"/>
<fmt:message key="project.orders" var="text_project_orders"/>
<fmt:message key="order.completion_date" var="text_order_completion_date"/>

<html>
<head>
    <title>${text_project_orders}</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/custom_button.css" rel="stylesheet" media="all"/>
    <link href="../css/custom_text.css" rel="stylesheet"/>
    <link href="../css/main.css" rel="stylesheet"/>
</head>
<body>
<header>
    <jsp:include page="header.jsp"/>
</header>

<div class="main-content">
    <h1 class="text-title">${text_order_orders}</h1>
    <div class="container">
        <div class="row">
            <div class="col">
                <div class="flex-column custom-card" style="width: 380px;">
                    <p class="fs-5 fw-semibold">${text_order_specializations}</p>
                    <form action="/controller" method="post"
                          class="list-group list-group-flush border-bottom scrollarea">
                        <c:forEach var="specialization" items="${applicationScope.specializationList}">
                            <c:if test="${specialization != null}">
                                <fmt:message key="${specialization.key}" var="text_specialization"/>
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" name="${specialization.id}"
                                           value="on"
                                           id="${specialization.id}" ${sessionScope.get(specialization.id) eq "on" ? 'checked' : null}>
                                    <label class="form-check-label" for="${specialization.id}">
                                            ${text_specialization}
                                    </label>
                                </div>
                            </c:if>
                        </c:forEach>
                        <div>
                            <input type="hidden" name="command" value="find_orders_by_specializations">
                            <input type="submit" class="custom-button-order-search" value="${text_order_search}">
                        </div>
                    </form>
                </div>
            </div>

            <div class="col">
                <c:choose>
                    <c:when test="${sessionScope.orderList.size() eq 0}">
                        <div class="container">
                            <div class="container payment_window pt-1 pb-1">
                                <div class="container">
                                    <h2 class="mt-1" style="width: 500px;"><mes:messageTag/></h2>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.orderList.size() gt 0}">
                        <div id="listElement" class="list-group">
                            <c:forEach var="order" items="${sessionScope.orderList}">
                                <c:if test="${order != null}">
                                    <a href="/controller?command=find_order_info_details&orderId=${order.orderId}"
                                       class="flex-column custom-card mb-2">
                                        <p class="date-to-format"
                                           style="text-align: right; font-size: 14px; margin-bottom: 0">${order.creationDate.time}</p>
                                        <div class="d-flex w-100 justify-content-between">
                                            <p class="mb-1 " style="font-weight: bold">${order.title}</p>
                                        </div>
                                        <p class="mb-1">${order.description}</p>
                                        <span>${text_order_completion_date} : <span
                                                class="date-to-format">${order.completionDate.time}</span></span>
                                        <input type="hidden" name="orderId" id="orderId" value="${order.orderId}">
                                    </a>
                                </c:if>
                            </c:forEach>
                        </div>
                    </c:when>
                </c:choose>
                <c:if test="${sessionScope.reason ne 'specialization'}">
                    <nav aria-label="Page navigation area">
                        <ul class="pagination justify-content-center">
                            <c:if test="${sessionScope.pagination.currentPage gt 1}">
                                <li class="page-item ${sessionScope.pagination.currentPage eq 1 ? 'disabled': ''}">
                                    <a class="page-link"
                                       href="${abs_path}/controller?command=all_orders&indexPage=${sessionScope.pagination.currentPage - 1}"
                                       tabindex="-1"><<</a>
                                </li>
                            </c:if>
                            <c:if test="${sessionScope.pagination.showFirstPage()}">
                                <li class="page-item ${sessionScope.pagination.currentPage eq 1 ? 'active': ''}">
                                    <a class="page-link"
                                       href="${abs_path}/controller?command=all_orders&indexPage=${1}">1</a>
                                </li>
                            </c:if>
                            <c:if test="${sessionScope.pagination.showLeftDivider()}">
                                <li class="page-item disabled}">
                                    <a class="page-link disabled" href="">...</a>
                                </li>
                            </c:if>
                            <c:forEach var="i" items="${sessionScope.pagination.listVisiblePage}">
                                <li class="page-item ${sessionScope.pagination.currentPage eq i ? 'active': ''}">
                                    <a class="page-link"
                                       href="${abs_path}/controller?command=all_orders&indexPage=${i}">${i}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${sessionScope.pagination.showRightDivider()}">
                                <li class="page-item disabled}">
                                    <a class="page-link disabled" href="">...</a>
                                </li>
                            </c:if>
                            <c:if test="${sessionScope.pagination.lastPage > 1}">
                                <li class="page-item ${sessionScope.pagination.currentPage eq sessionScope.pagination.lastPage ? 'active': ''}">
                                    <a class="page-link"
                                       href="${abs_path}/controller?command=all_orders&indexPage=${sessionScope.pagination.lastPage}">${sessionScope.pagination.lastPage}</a>
                                </li>
                            </c:if>
                            <c:if test="${sessionScope.pagination.currentPage < sessionScope.pagination.lastPage}">
                                <li class="page-item ${sessionScope.pagination.currentPage eq sessionScope.pagination.lastPage ? 'disabled': ''}">
                                    <a class="page-link"
                                       href="${abs_path}/controller?command=all_orders&indexPage=${sessionScope.pagination.currentPage + 1}"
                                       tabindex="-1">>></a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>
</div>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
