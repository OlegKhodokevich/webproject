<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<fmt:message key="order.search" var="text_order_search"/>
<fmt:message key="order.orders" var="text_order_orders"/>
<fmt:message key="order.specializations" var="text_order_specializations"/>
<fmt:message key="order.empty_list" var="text_order_empty_list"/>
<fmt:message key="order.order_number" var="text_order_order_number"/>
<fmt:message key="order.title" var="text_order_title"/>
<fmt:message key="order.description" var="text_order_description"/>
<fmt:message key="order.create_date" var="text_order_create_date"/>
<fmt:message key="order.completion_date" var="text_order_completion_date"/>
<fmt:message key="order.address" var="text_order_address"/>
<fmt:message key="order.status" var="text_order_status"/>
<fmt:message key="order.customer" var="text_order_customer"/>
<fmt:message key="order.order_info" var="text_order_order_info"/>

<fmt:message key="registration.firstname" var="text_registration_firstname"/>
<fmt:message key="registration.lastname" var="text_registration_lastname"/>
<fmt:message key="registration.phone" var="text_registration_phone"/>

<fmt:message key="contract.make_offer" var="text_contract_make_offer"/>

<html>
<head>
    <title>${text_order_order_info}</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/main.css" rel="stylesheet"/>
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>

<main>
    <div class="main-content">
        <form action="${pageContext.request.contextPath}/controller" method="post" class="creation_order_page">
            <div class="row row-cols-1 row-cols-md-3 mb-3 text-left  mt-5">

                <div class="col-md-5 offset-md-1">
                    <div class="card mb-4 rounded-3 shadow-sm">
                        <div class="card-header py-3">
                            <h4 class="my-0 fw-normal">${text_order_order_number} : ${sessionScope.order.orderId}</h4>
                        </div>
                        <div class="card-body">
                            <h1 class="card-title">${sessionScope.order.title}</h1>
                            <p class="date-to-format"
                               style="text-align: right; font-size: 14px; margin-bottom: 0">${sessionScope.order.creationDate.time}</p>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item">${text_order_description}
                                    : ${sessionScope.order.description}</li>
                                <li class="list-group-item">${text_order_address} : ${sessionScope.order.address}</li>
                                <li class="list-group-item">${text_order_completion_date} : <span
                                        class="date-to-format">${sessionScope.order.completionDate.time}</span></li>
                                <li class="list-group-item">${text_order_customer}
                                    : ${sessionScope.firstName} ${sessionScope.lastName}</li>
                                <li class="list-group-item">${text_registration_phone} : ${sessionScope.phone}</li>
                                <li class="list-group-item">e-mail : ${sessionScope.eMail}</li>

                            </ul>
                        </div>
                        <c:if test="${sessionScope.activeUserRole eq 'EXECUTOR'}">
                            <input type="hidden" name="orderId" value="${sessionScope.order.orderId}"
                                   style="background-color: #1e7e34">
                            <input type="hidden" name="command" value="create_offer">
                            <%--                <input type="hidden" name="userId" value="${sessionScope.userId}" >--%>
                            <input type="submit" class="btn-success" style="width: 250px;align-self: center;margin-bottom: 20px;" value="${text_contract_make_offer}">

                        </c:if>
                    </div>
                </div>
            </div>

        </form>
        <div style="min-height: 270px">
        </div>
    </div>
</main>

<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>