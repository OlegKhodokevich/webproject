<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mes" uri="custom tag message writer" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<fmt:message key="revoke.title" var="text_revoke_title"/>
<fmt:message key="revoke.executor_revoke" var="text_revoke_executor_revoke"/>
<fmt:message key="revoke.empty_list" var="text_revoke_empty_list"/>

<html>
<head>
    <title>${text_revoke_title}</title>
    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/custom_card.css" rel="stylesheet"/>
    <link href="../css/revoke_style.css" rel="stylesheet"/>
    <link href="../css/main.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>
<div class="main-content">

    <h1 class="text-title text-center">${text_revoke_title}</h1>

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
                <c:when test="${sessionScope.revokeList.size() == 0}">

                    <div class="container py-2 mt-5 label_window">
                        <h3 class="mb-0 ml-3">${text_revoke_empty_list}</h3>
                    </div>
                </c:when>
                <c:when test="${sessionScope.revokeList.size() > 0}">
                    <div class="list-group">
                        <c:forEach var="revoke" items="${sessionScope.revokeList}">
                            <c:if test="${revoke != null}">
                                <div class="container custom-card-my-contracts">
                                    <div class="row">
                                        <div class="col" style="min-width: 600px">

                                            <div class=" w-100 justify-content-between">
                                                <c:forEach items="${applicationScope.markList}" varStatus="loop">
                                                    <c:choose>
                                                        <c:when test="${(loop.index + 1) <= revoke.mark}">
                                                            <span class="fa fa-star checked"></span>
                                                        </c:when>
                                                        <c:when test="${(loop.index + 1) > revoke.mark}">
                                                            <span class="fa fa-star"></span>
                                                        </c:when>
                                                    </c:choose>
                                                </c:forEach>

                                                <p class="mb-1">${revoke.description}</p>
                                                <br/>
                                                <span class="date-to-format">${revoke.creationDate.time}</span>
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
</div>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>