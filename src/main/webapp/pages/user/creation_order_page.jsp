<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mes" uri="custom tag message writer" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<fmt:message key="order.specializations" var="text_order_specializations"/>
<fmt:message key="order.empty_list" var="text_order_empty_list"/>
<fmt:message key="order.orders" var="text_order_orders"/>
<fmt:message key="order.specializations" var="text_order_specializations"/>
<fmt:message key="order.order_number" var="text_order_order_number"/>
<fmt:message key="order.title" var="text_order_title"/>
<fmt:message key="order.description" var="text_order_description"/>
<fmt:message key="order.completion_date" var="text_order_completion_date"/>
<fmt:message key="order.address" var="text_order_address"/>
<fmt:message key="order.status" var="text_order_status"/>
<fmt:message key="order.customer" var="text_order_customer"/>
<fmt:message key="order.order_info" var="text_order_order_info"/>
<fmt:message key="order.create_order" var="text_order_create_order"/>
<fmt:message key="order.example_title" var="text_order_example_title"/>
<fmt:message key="order.example_description" var="text_order_example_description"/>
<fmt:message key="order.example_address" var="text_order_example_address"/>
<fmt:message key="order.create" var="text_order_create"/>
<fmt:message key="order.confirm" var="text_order_confirm"/>
<fmt:message key="order.edit_order" var="text_order_edit_order"/>

<html>
<head>
    <c:choose>
        <c:when test="${sessionScope.reason eq 'create'}"><title>${text_order_create_order}</title></c:when>
        <c:when test="${sessionScope.reason eq 'activate' or sessionScope.reason eq 'edit' }">
            <title>${text_order_edit_order}</title></c:when>
    </c:choose>

    <link href="../../css/custom_styles.css" rel="stylesheet"/>
    <link href="../../css/styles_create_order_page.css" rel="stylesheet"/>
</head>
<body style="background-image: url(../../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="../header.jsp"/>
</header>

<div class="container">
    <div class="container payment_window pt-1 pb-1">
        <div class="container">
            <h2 class="mt-1"><mes:messageTag/></h2>
        </div>
    </div>
</div>

<form action="${pageContext.request.contextPath}/controller" method="post" class="creation_order_page">
    <c:choose>
        <c:when test="${sessionScope.reason eq 'create'}"><h1
                style="text-align: center">${text_order_create_order}</h1></c:when>
        <c:when test="${sessionScope.reason eq 'activate' or sessionScope.reason eq 'edit' }"><h1
                style="text-align: center">${text_order_edit_order}</h1></c:when>
    </c:choose>
    <div>
        <input type="text" placeholder="${text_order_example_title}" id="Title" name="title" maxlength="100"
               required value="${sessionScope.title}">
        <label for="Title">${text_order_title}</label>
    </div>
    <div>
        <input type="text" placeholder="${text_order_example_description}" id="Description" name="description"
               maxlength="400"
               required value="${sessionScope.description}">
        <label for="Description">${text_order_description}</label>
    </div>
    <div>
        <input type="text" placeholder="${text_order_example_address}" id="Address" name="address" maxlength="100"
               required value="${sessionScope.address}">
        <label for="Address">${text_order_address}</label>
    </div>
    <div>
        <input type="date" id="CompletionDate" name="completionDate" required value="${sessionScope.completionDate}">
        <label for="CompletionDate">${text_order_completion_date}</label>
    </div>

    <div>
        <label for="Specialization">${text_order_specializations}</label>
        <select name="specialization" id="Specialization" required>
            <c:forEach var="spec" items="${applicationScope.specializationList}">
                <c:if test="${spec != null}">
                    <fmt:message key="${spec.key}" var="text_specialization"/>
                    <option value="${spec.name()}" ${spec eq sessionScope.specialization ? 'selected' : null} >${text_specialization}</option>
                </c:if>
            </c:forEach>
        </select>
    </div>

    <c:choose>
        <c:when test="${sessionScope.reason eq 'create'}">
            <div>
                <input type="hidden" name="command" value="create_order">
                <input type="submit" value="${text_order_create}">
            </div>
        </c:when>
        <c:when test="${sessionScope.reason eq 'activate'}">
            <div>
                <input type="hidden" name="orderId" value="${sessionScope.orderId}">
                <input type="hidden" name="command" value="activate_order">
<%--                <input type="hidden" name="userId" value="${sessionScope.userId}" >--%>
                <input type="submit" value="${text_order_confirm}">
            </div>
        </c:when>
        <c:when test="${sessionScope.reason eq 'edit'}">
            <div>
                <input type="hidden" name="orderId" value="${sessionScope.orderId}" style="background-color: #1e7e34">
                <input type="hidden" name="command" value="edit_order" >
<%--                <input type="hidden" name="userId" value="${sessionScope.userId}" >--%>
                <input type="submit" value="${text_order_confirm}">
            </div>
        </c:when>
    </c:choose>
</form>
<footer>
    <jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>
