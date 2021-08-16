<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mes" uri="custom tag message writer" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>
<fmt:message key="order.create_order" var="text_order_create_order"/>
<fmt:message key="order.my_order" var="text_order_my_order"/>
<fmt:message key="order.empty_list" var="text_order_empty_list"/>
<fmt:message key="order.status" var="text_order_status"/>
<fmt:message key="order.hide" var="text_order_hide"/>
<fmt:message key="order.activate" var="text_order_activate"/>
<fmt:message key="order.message_go_to_admin" var="text_order_message_go_to_admin"/>
<fmt:message key="order.edit" var="text_order_edit"/>

<html>
<head>
    <title>${text_order_my_order}</title>
    <link href="../../css/custom_styles.css" rel="stylesheet"/>
    <link href="../../css/space_style.css" rel="stylesheet"/>
    <link href="../../css/custom_button.css" rel="stylesheet"/>
    <link href="../../css/custom_card.css" rel="stylesheet"/>
</head>
<body style="background-image: url(../../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover;
height: 100%">
<header>
    <jsp:include page="../header.jsp"/>
</header>

<h1 class="text-title text-center">${text_order_my_order}</h1>

<script src="../../js/date_formatter.js"></script>

<div class="container">
    <div class="container payment_window pt-1 pb-1">
        <div class="container">
            <h2 class="mt-1"><mes:messageTag/></h2>
        </div>
    </div>
</div>
<c:if test="${sessionScope.activeUserRole eq 'CUSTOMER'}">
    <a class="btn  btn-success align-content-center text-lg-center mb-2 p-0"
       href="/controller?command=go_to_creation_order_page"
       role="button" style="width: 300px; height: 50px; font-size: 22px; margin-left: 300px; padding: 0"> <span
            style="font-size: 32px">+</span>${text_order_create_order}
    </a>
</c:if>
<div class="col">
    <div class="container" style="margin-bottom: 200px;">
        <c:choose>
            <c:when test="${sessionScope.orderList.size() == 0}">

                <div class="container py-2 mt-5 label_window">
                    <h3 class="mb-0 ml-3">${text_order_empty_list}</h3>
                </div>
            </c:when>
            <c:when test="${sessionScope.orderList.size() > 0}">
                <div class="list-group">
                    <c:forEach var="order" items="${sessionScope.orderList}">
                        <c:if test="${order != null}">
                            <div class="custom-card-my-order bg-white">
                                <div class="row">
                                    <div class="col" style="min-width: 600px">
                                        <a href="/controller?command=find_order_info_details&orderId=${order.orderId}"
                                           class="list-group-item list-group-item-action flex-column align-items-start mt-2 mb-2">
                                            <p class="date-to-format"
                                               style="text-align: right; font-size: 14px; margin-bottom: 0">${order.creationDate.time}</p>
                                            <div class="w-100 justify-content-between">
                                                <p class="mb-1" style="font-weight: bold ">${order.title}</p>
                                            </div>
                                            <p class="mb-1">${order.description}</p>
                                            <p class="date-to-format">${order.completionDate.time}</p>
                                            <fmt:message key="${order.status.key}" var="text_status"/>
                                            <small>${text_order_status} : ${text_status}</small>
                                            <input type="hidden" name="orderId" id="orderId" value="${order.orderId}">
                                        </a>
                                    </div>
                                    <c:if test="${order.status.name() ne 'IN_WORK'}">

                                        <div class="col wi m-1">
                                            <div class="container mt-4">

                                                <div class="list-group">
                                                    <c:if test="${(sessionScope.activeUserRole eq 'ADMIN') || (sessionScope.activeUserId eq order.userId)}">

                                                        <c:choose>
                                                            <c:when test="${order.status.name() eq 'OPEN'}">
                                                                <a class="btn btn-success custom-button-operation-my-order"
                                                                   href="/controller?command=prepare_activate_order&orderId=${order.orderId}&reason=edit"
                                                                   role="button">${text_order_edit}</a>
                                                                <a class="btn btn-danger custom-button-operation-my-order"
                                                                   href="/controller?command=archive_order&orderId=${order.orderId}&userId=${order.userId}"
                                                                   role="button">${text_order_hide}</a>
                                                            </c:when>
                                                            <c:when test="${order.status.name() == 'CLOSE'}">
                                                                <a class="btn btn-info custom-button-operation-my-order"
                                                                   href="/controller?command=prepare_activate_order&orderId=${order.orderId}&reason=activate"
                                                                   role="button">${text_order_activate}</a>
                                                            </c:when>
                                                            <c:when test="${order.status.name() == 'UNDER_CONSIDERATION'}">
                                                                <c:choose>
                                                                    <c:when test="${sessionScope.activeUserRole ne 'ADMIN'}">
                                                                        <p class="text-center ">${text_order_message_go_to_admin}</p>
                                                                    </c:when>
                                                                    <c:when test="${sessionScope.activeUserRole eq 'ADMIN'}">
                                                                        <a class="btn btn-info custom-button-operation-my-order"
                                                                           href="/controller?command=prepare_activate_order&orderId=${order.orderId}&reason=activate"
                                                                           role="button">${text_order_activate}</a>
                                                                    </c:when>

                                                                </c:choose>

                                                            </c:when>
                                                        </c:choose>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </div>

                                    </c:if>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </c:when>
        </c:choose>
    </div>
</div>

<footer>
    <jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>