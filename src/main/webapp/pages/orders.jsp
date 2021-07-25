<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="abs_path">${pageContext.request.contextPath}</c:set>

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

    <!-- Bootstrap core CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/custom_button.css" rel="stylesheet" media="all"/>
    <link href="../css/custom_text.css" rel="stylesheet"/>
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover;
display: flex;
flex-direction: column;">
<header>
    <jsp:include page="header.jsp"/>
</header>
<h1 class="text-title" >${text_order_orders}</h1>

<div class="container">
    <div class="row">
        <div class="col">
            <div class="flex-column custom-card" style="width: 380px;">
                <p class="fs-5 fw-semibold">${text_order_specializations}</p>

                <form action="/controller" method="post" class="list-group list-group-flush border-bottom scrollarea">

                    <c:forEach var="specialization" items="${sessionScope.specializationList}">
                        <c:if test="${specialization != null}">
                            <fmt:message key="${specialization.key}" var="text_specialization"/>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="${specialization.id}" value="on"
                                       id="${specialization.id}" ${sessionScope.get(specialization.id) eq "on" ? 'checked' : null}>
                                <label class="form-check-label" for="${specialization.id}">
                                        ${text_specialization}
                                </label>
                            </div>
                        </c:if>
                    </c:forEach>


<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec1" value="on" id="spec1">--%>
<%--                        <label class="form-check-label" for="spec1">--%>
<%--                            ${text_specialization_electrical}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec2" value="on" id="spec2">--%>
<%--                        <label class="form-check-label" for="spec2">--%>
<%--                            ${text_specialization_plumbing}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec3" value="on" id="spec3">--%>
<%--                        <label class="form-check-label" for="spec3">--%>
<%--                            ${text_specialization_plastering}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec4" value="on" id="spec4">--%>
<%--                        <label class="form-check-label" for="spec4">--%>
<%--                            ${text_specialization_laying_tiles}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec5" value="on" id="spec5">--%>
<%--                        <label class="form-check-label" for="spec5">--%>
<%--                            ${text_specialization_painting}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec6" value="on" id="spec6">--%>
<%--                        <label class="form-check-label" for="spec6">--%>
<%--                            ${text_specialization_wallpapering}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec7" value="on" id="spec7">--%>
<%--                        <label class="form-check-label" for="spec7">--%>
<%--                            ${text_specialization_cement_floor}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec8" value="on" id="spec8">--%>
<%--                        <label class="form-check-label" for="spec8">--%>
<%--                            ${text_specialization_floor_covering}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec9" value="on" id="spec9">--%>
<%--                        <label class="form-check-label" for="spec9">--%>
<%--                            ${text_specialization_carpentry_work}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec10" value="on" id="spec10">--%>
<%--                        <label class="form-check-label" for="spec10">--%>
<%--                            ${text_specialization_turkey_house}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec11" value="on" id="spec11">--%>
<%--                        <label class="form-check-label" for="spec11">--%>
<%--                            ${text_specialization_roof}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec12" value="on" id="spec12">--%>
<%--                        <label class="form-check-label" for="spec12">--%>
<%--                            ${text_specialization_monolite}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec13" value="on" id="spec13">--%>
<%--                        <label class="form-check-label" for="spec13">--%>
<%--                            ${text_specialization_bricklaying}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec14" value="on" id="spec14">--%>
<%--                        <label class="form-check-label" for="spec14">--%>
<%--                            ${text_specialization_fasad}--%>
<%--                        </label>--%>
<%--                    </div>--%>
<%--                    <div class="form-check">--%>
<%--                        <input class="form-check-input" type="checkbox" name="spec15" value="on" id="spec15">--%>
<%--                        <label class="form-check-label" for="spec15">--%>
<%--                            ${text_specialization_landscaping}--%>
<%--                        </label>--%>
<%--                    </div>--%>
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

                    <div class="container mt-5 ml-3 text-message">      <%--    //TODO add css library--%>
                        <h3>${text_order_empty_list}</h3>
                    </div>

                </c:when>
                <c:when test="${sessionScope.orderList.size() gt 0}">
                    <div class="list-group">
                        <c:forEach var="order" items="${sessionScope.orderList}">
                            <c:if test="${order != null}">
                                <a href="/controller?command=find_order_info_details&orderId=${order.orderId}"
                                   class="flex-column custom-card mb-2">
                                    <p class="date-to-format" style="text-align: right; font-size: 14px; margin-bottom: 0px">${order.creationDate.time}</p>
                                    <div class="d-flex w-100 justify-content-between">
                                        <p class="mb-1">${order.title}</p>
                                         <%--    //TODO data format--%>
                                    </div>
                                    <p class="mb-1">${order.description}</p>
                                    <span>${text_order_completion_date} : <span class="date-to-format">${order.completionDate.time}</span></span>
                                    <input type="hidden" name="orderId" id="orderId" value="${order.orderId}">
                                </a>
                            </c:if>
                        </c:forEach>
                    </div>
                </c:when>
            </c:choose>
        </div>


    </div>

</div>

<%--<script LANGUAGE="JavaScript">--%>
<%--    function f_date(temp_date){--%>
<%--        day = temp_date.getDate();--%>
<%--        month = temp_date.getMonth() + 1;--%>
<%--        year = temp_date.getFullYear();--%>
<%--        if (day < 10) {--%>
<%--            day = "0" + day;--%>
<%--        }--%>
<%--        if (month <10) {--%>
<%--            month = "0" + month;--%>
<%--        }--%>
<%--        document.getElementById('dateFolder').innerHTML = day + "." + month + "." + year;--%>
<%--    }--%>
<%--</SCRIPT>--%>

<footer class="custom-footer">

    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>
