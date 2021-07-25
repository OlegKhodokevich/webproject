<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mes" uri="custom tag message writer" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>
<fmt:message key="order.create_order" var="text_order_create_order"/>
<fmt:message key="order.my_order" var="text_order_my_order"/>
<fmt:message key="order.empty_list" var="text_order_empty_list"/>
<fmt:message key="order.status" var="text_order_status"/>

<html>
<head>
    <title>${text_order_my_order}</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/space_style.css" rel="stylesheet"/>
    <link href="../css/custom_button.css" rel="stylesheet"/>
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover;
height: 100%">
<header>
    <jsp:include page="header.jsp"/>
</header>
<a class="btn  btn-success align-content-center text-lg-center mb-2 p-0" href="/controller?command=go_to_creation_order_page"
   role="button" style="width: 300px; height: 50px; font-size: 22px; margin-left: 250px"> <span style="font-size: 32px">+</span>${text_order_create_order}</a>
<div class="col">
<div class="container" style="margin-bottom: 200px">
        <c:choose>
            <c:when test="${sessionScope.orderList.size() == 0}">

                <div class="container py-2 mt-5 label_window">      <%--    //TODO add css library--%>
                    <h3 class="mb-0 ml-3">${text_order_empty_list}</h3>
                </div>

            </c:when>
            <c:when test="${sessionScope.orderList.size() > 0}">
                <div class="list-group">
                    <c:forEach var="order" items="${sessionScope.orderList}">
                        <c:if test="${order != null}">
                            <div class="container custom-card-my-order" style="background-color: white; margin-left: unset; min-width: 700px">
                                <div class="row">
                                    <div class="col" style="min-width: 600px">
                                        <a href="/controller?command=find_order_info_details&orderId=${order.orderId}"
                                           class="list-group-item list-group-item-action flex-column align-items-start mt-2">
                                            <span class="date-to-format">${order.creationDate.time}</span>
                                            <div class="d-flex w-100 justify-content-between">
                                                <p class="mb-1">${order.title}</p>
                                                 <%--    //TODO data format--%>
                                            </div>
                                            <p class="mb-1">${order.description}</p>
                                            <small>${order.completionDate}</small>
                                            <small>${text_order_status} : ${order.status}</small>
                                            <input type="hidden" name="orderId" id="orderId" value="${order.orderId}">
                                        </a>
                                    </div>
                                    <div class="col wi">
                                        <div class="container">
                                            <div class="list-group">
                                                <a class="btn btn-success custom-button-operation-my-order" href="#"
                                                   role="button">${text_order_create_order}редактировать<a>
                                                    <input type="hidden" name="orderId" id="orderIdForSetStatus" value="${order.orderId}">
                                                </a>
                                                <a class="btn btn-success custom-button-operation-my-order" href="#"
                                                   role="button">${text_order_create_order}скрыть<a>
                                                    <input type="hidden" name="orderId" id="orderIdForEditing" value="${order.orderId}">
                                                </a>
                                                <a class="btn btn-success custom-button-operation-my-order" href="#"
                                                   role="button">${text_order_create_order}активировать<a>
                                                    <input type="hidden" name="orderId" id="orderIdForActivate" value="${order.orderId}">
<%--                                                    btn  btn-success align-content-center text-lg-center--%>
                                                </a>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </c:when>
        </c:choose>
    </div>

</div>
<footer class="container custom-footer">
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
