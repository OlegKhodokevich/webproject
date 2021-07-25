<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>

<fmt:setBundle basename="text"/>


<%--<fmt:message key="specialization.electrical" var="text_specialization_electrical"/>--%>
<%--<fmt:message key="specialization.plumbing" var="text_specialization_plumbing"/>--%>
<%--<fmt:message key="specialization.plastering" var="text_specialization_plastering"/>--%>
<%--<fmt:message key="specialization.laying_tiles" var="text_specialization_laying_tiles"/>--%>
<%--<fmt:message key="specialization.painting" var="text_specialization_painting"/>--%>
<%--<fmt:message key="specialization.wallpapering" var="text_specialization_wallpapering"/>--%>
<%--<fmt:message key="specialization.cement_floor" var="text_specialization_cement_floor"/>--%>
<%--<fmt:message key="specialization.floor_covering" var="text_specialization_floor_covering"/>--%>
<%--<fmt:message key="specialization.carpentry_work" var="text_specialization_carpentry_work"/>--%>
<%--<fmt:message key="specialization.turkey_house" var="text_specialization_turkey_house"/>--%>
<%--<fmt:message key="specialization.roof" var="text_specialization_roof"/>--%>
<%--<fmt:message key="specialization.monolite" var="text_specialization_monolite"/>--%>
<%--<fmt:message key="specialization.bricklaying" var="text_specialization_bricklaying"/>--%>
<%--<fmt:message key="specialization.fasad" var="text_specialization_fasad"/>--%>
<%--<fmt:message key="specialization.landscaping" var="text_specialization_landscaping"/>--%>

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

<html>
<head>
    <title>${text_order_create_order}</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/styles_create_order_page.css" rel="stylesheet"/>
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>

<form action="${pageContext.request.contextPath}/controller" method="post" class="creation_order_page">

    <h1 style="text-align: center">${text_order_create_order}</h1>
    <div>
        <input type="text" placeholder="${text_order_example_title}" id="Title" name="title" maxlength="100"
               required value="${sessionScope.title}">
        <label for="Title">${text_order_title}</label>
    </div>
    <div>
        <input type="text" placeholder="${text_order_example_description}" id="Description" name="description" maxlength="400"
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
            <c:forEach var="spec" items="${sessionScope.specializationList}">
                <c:if test="${spec != null}">
                    <fmt:message key="${spec.key}" var="text_specialization"/>
                    <option value="${spec.name()}" ${spec eq sessionScope.specialization ? 'selected' : null} >${text_specialization}</option>
                </c:if>
            </c:forEach>
<%--            <option value="ELECTRICAL">${text_specialization_electrical}</option>--%>
<%--            <option value="PLUMBING">${text_specialization_plastering}</option>--%>
<%--            <option value="PLASTERING">${text_specialization_plumbing}</option>--%>
<%--            <option value="LAYING_TILES">${text_specialization_laying_tiles}</option>--%>
<%--            <option value="PAINTING">${text_specialization_painting}</option>--%>
<%--            <option value="WALLPAPERING">${text_specialization_wallpapering}</option>--%>
<%--            <option value="CEMENT_FLOOR">${text_specialization_cement_floor}</option>--%>
<%--            <option value="FLOOR_COVERING">${text_specialization_floor_covering}</option>--%>
<%--            <option value="CARPENTRY_WORK">${text_specialization_carpentry_work}</option>--%>
<%--            <option value="TURNKEY_HOUSE">${text_specialization_turkey_house}</option>--%>
<%--            <option value="ROOF">${text_specialization_roof}</option>--%>
<%--            <option value="MONOLITE">${text_specialization_monolite}</option>--%>
<%--            <option value="BRICKLAYING">${text_specialization_bricklaying}</option>--%>
<%--            <option value="FASAD">${text_specialization_fasad}</option>--%>
<%--            <option value="LANDSCAPING">${text_specialization_landscaping}</option>--%>
        </select>
    </div>

    <div>
        <input type="hidden" name="command" value="create_order" style="background-color: #1e7e34">
        <input type="submit" value="${text_order_create}">
    </div>
</form>
<footer class="custom-footer">
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
