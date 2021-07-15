<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>
<fmt:message key="order.create_order" var="text_order_create_order"/>
<fmt:message key="order.my_order" var="text_order_my_order"/>





<li class="nav-link active text-md-center">
    <a class="nav-link" href="/controller?command=set_locale&locale=en_US" style="color: black">EN</a>
</li>
<html>
<head>
    <title>${text_order_my_order}</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>

<div class="container">
    <div class="container payment_window mb-5 pt-3 pb-5">
        <div class="container mt-5">
            <h2 class="mt-5"><ctg:welcome firstName="${sessionScope.activeUser.getfirstName()}"
                                          lastName="${sessionScope.activeUser.getlastName()}"
                                          role="${sessionScope.activeUser.role}"/></h2>
        </div>
    </div>
</div>


<a class="btn  btn-success" href="/controller?command=go_to_creation_order_page"
   role="button">${text_order_create_order}</a>
<div class="col">
    <c:choose>
        <c:when test="${requestScope.empty_list.booleanValue() == true}">

            <div class="container py-2 mt-5 label_window">      <%--    //TODO add css library--%>
                <h3 class="mb-0 ml-3" style="color: black">${text_order_empty_list}</h3>
            </div>

        </c:when>
        <c:when test="${sessionScope.orderList != null}">
            <div class="list-group">
                <c:forEach var="order" items="${sessionScope.orderList}">
                    <c:if test="${order != null}">
                        <a href="/controller?command=go_to_order_info&orderId=${order.orderId}"
                           class="list-group-item list-group-item-action flex-column align-items-start active border-dark mt-2">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">${order.getTitle()}</h5>
                                <small>${order.creationDate}</small> <%--    //TODO data format--%>
                            </div>
                            <p class="mb-1">${order.description}</p>
                            <small>${order.completionDate}</small>
                            <input type="hidden" name="orderId" id="orderId" value="${order.orderId}">
                        </a>
                    </c:if>
                </c:forEach>
            </div>
        </c:when>
    </c:choose>
</div>

<footer class="custom-footer">
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
