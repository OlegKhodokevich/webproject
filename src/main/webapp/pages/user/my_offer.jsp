<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<fmt:message key="logging.welcome_user" var="text_welcome_user"/>
<fmt:message key="contract.completion_status" var="text_contract_completion_status"/>
<fmt:message key="contract.concluded_status" var="text_contract_concluded_status"/>
<fmt:message key="contract.contract" var="text_contract_contract"/>
<fmt:message key="contract.empty_list" var="text_contract_empty_list"/>
<fmt:message key="order.title" var="text_order_title"/>
<fmt:message key="contract.executor" var="text_contract_executor"/>
<fmt:message key="contract.close_contract" var="text_contract_close_contract"/>
<fmt:message key="contract.my_offer" var="text_contract_my_offer"/>

<html>
<head>
    <title>${text_contract_my_contracts}</title>
    <link href="../../css/custom_styles.css" rel="stylesheet"/>
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
<div class="col">
    <div class="container" style="margin-bottom: 200px">
        <c:choose>
            <c:when test="${sessionScope.contractList.size() == 0}">

                <div class="container py-2 mt-5 label_window">
                    <h3 class="mb-0 ml-3">${text_contract_empty_list}</h3>
                </div>
            </c:when>
            <c:when test="${sessionScope.contractList.size() > 0}">
                <div class="list-group">
                    <c:forEach var="contract" items="${sessionScope.contractList}">
                        <c:if test="${contract != null}">
                            <div class="container custom-card-my-contracts">
                                <div class="row">
                                    <div class="col" style="min-width: 600px">

                                        <div class=" w-100 justify-content-between">
                                            <p class="mb-1" style="font-weight: bold ">${text_contract_contract}
                                                № ${contract.idContract}</p>
                                            <br/>
                                            <p>
                                                <a href="/controller?command=find_order_info_details&orderId=${contract.order.orderId}">
                                                        ${text_order_title} : ${contract.order.title}
                                                </a>
                                            </p>
                                            <p>
                                                <a href="/controller?command=find_executor_info_details&executorId=${contract.user.idUser}">
                                                        ${text_contract_executor}${contract.user.firstName} ${contract.user.lastName}
                                                </a>
                                            </p>
                                        </div>
                                        <fmt:message key="${contract.concludedContractStatus.key}"
                                                     var="text_contract_concludedContractStatus"/>
                                        <p class="mb-1">${text_contract_concluded_status}
                                            : ${text_contract_concludedContractStatus}</p>

                                        <fmt:message key="${contract.completionContractStatus.key}"
                                                     var="text_contract_completionContractStatus"/>
                                        <p class="mb-1">${text_contract_completion_status}
                                            : ${text_contract_completionContractStatus}</p>
                                    </div>
                                    <div class="col wi">
                                        <div class="container">
                                            <div class="list-group">
                                                <c:choose>
                                                    <c:when test="${contract.completionContractStatus eq 'NOT_COMPLETED'}">
                                                        <a class="btn btn-success custom-button-operation-my-order"
                                                           href="#"
                                                           role="button">${text_contract_close_contract}</a>
                                                    </c:when>

                                                    <%--                                                    <c:when test="${order.status.name() == 'CLOSE'}">--%>
                                                    <%--                                                        <a class="btn btn-info custom-button-operation-my-order"--%>
                                                    <%--                                                           href="/controller?command=prepare_activate_order&orderId=${order.orderId}&reason=activate"--%>
                                                    <%--                                                           role="button">${text_order_activate}</a>--%>
                                                    <%--                                                    </c:when>--%>
                                                </c:choose>
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


<footer>
    <jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>