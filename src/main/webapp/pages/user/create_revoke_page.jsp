<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mes" uri="custom tag message writer" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<fmt:message key="revoke.you_revoke" var="text_revoke_you_revoke"/>
<fmt:message key="revoke.post_revoke" var="revoke_post_revoke"/>
<fmt:message key="revoke.revoke" var="text_revoke_revoke"/>
<fmt:message key="revoke.enter_revoke" var="text_revoke_enter_revoke"/>

<html>
<head>
    <title>${text_revoke_you_revoke}</title>
    <link href="../../css/custom_styles.css" rel="stylesheet"/>
    <link href="../../css/creation_revoke_page.css" rel="stylesheet"/>
    <link href="../../css/revoke_style.css" rel="stylesheet"/>
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

<form action="${pageContext.request.contextPath}/controller" method="post" class="creation_revoke_page">
    <h1 style="text-align: center">${text_revoke_you_revoke}</h1>

    <div class="rating-area" style="margin-left: 200px;">
        <input type="radio" id="star-1" name="rating" value="1">
        <label for="star-1" title="Оценка «1»"></label>
        <input type="radio" id="star-2" name="rating" value="2">
        <label for="star-2" title="Оценка «2»"></label>
        <input type="radio" id="star-3" name="rating" value="3">
        <label for="star-3" title="Оценка «3»"></label>
        <input type="radio" id="star-4" name="rating" value="4">
        <label for="star-4" title="Оценка «4»"></label>
        <input type="radio" id="star-5" name="rating" value="5">
        <label for="star-5" title="Оценка «5»"></label>
    </div>
    <div>
        <p><b>${text_revoke_enter_revoke}</b></p>
        <p><label>
            <textarea rows="5" cols="100" name="descriptionRevoke"></textarea>
        </label></p>
    </div>

    <div>
        <input type="hidden" name="contractId" value="${requestScope.contractId}">
        <input type="hidden" name="command" value="create_revoke">
        <input type="submit" value="${revoke_post_revoke}">
    </div>
</form>
<footer>
    <jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>
