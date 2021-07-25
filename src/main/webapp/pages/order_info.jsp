
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${not empty sessionScope.locale}">
  <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

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

<html>
<head>
  <title>${text_order_order_info}</title>
  <link href="../css/custom_styles.css" rel="stylesheet"/>
</head>
<body  style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
  <jsp:include page="header.jsp"/>
</header>
<main>
  <div class="row row-cols-1 row-cols-md-3 mb-3 text-left  mt-5">

    <div class="col-md-5 offset-md-1">
      <div class="card mb-4 rounded-3 shadow-sm">
        <div class="card-header py-3">
          <h4 class="my-0 fw-normal">${text_order_order_number} : ${sessionScope.order.orderId}</h4>
        </div>
        <div class="card-body">
          <h1 class="card-title">${sessionScope.order.title}</h1>
          <p class="date-to-format" style="text-align: right; font-size: 14px; margin-bottom: 0px">${sessionScope.order.creationDate}</p>
          <ul class="list-group list-group-flush">
            <li class="list-group-item">${text_order_description} : ${sessionScope.order.description}</li>
            <li class="list-group-item">${text_order_address} : ${sessionScope.order.address}</li>
            <li class="list-group-item">${text_order_completion_date} : <span class="date-to-format">${sessionScope.order.completionDate}</span></li>
            <li class="list-group-item">${text_order_customer} : ${sessionScope.firstName} ${sessionScope.lastName}</li>
            <li class="list-group-item">${text_registration_phone} : ${sessionScope.phone}</li>
          </ul>
<%--          <button type="button" class="w-100 btn btn-lg btn-primary">Get started</button>--%>
        </div>
      </div>
    </div>
    <div class="col offset-md-1">


<%--        <div class="container mt-2 w-100">--%>
<%--          <button type="button" class="w-100 btn btn-md">Contact us</button>--%>
<%--          <h1> </h1>--%>
<%--          <button type="button" class="w-100 btn btn-md">Delete</button>--%>
<%--          <h1> </h1>--%>
<%--        </div>--%>

    </div>
  </div>

</main>

<footer class="custom-footer">
  <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>