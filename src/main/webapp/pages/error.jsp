<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 27.06.2021
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ taglib prefix="c" uri="http://jakarta.apache.org/taglibs/standard/permittedTaglibs" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="fmt" uri="http://jakarta.apache.org/taglibs/standard/permittedTaglibs" %>--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty sessionScope.locale}">
    <fmt:setLocale value="${sessionScope.locale}"/>
</c:if>

<fmt:setBundle basename="text"/>

<fmt:message key="error.tech_problems" var="text_error_tech_problems"/>
<fmt:message key="error.tech_come_later" var="text_error_tech_come_later"/>


<html>
<head>
<%--    <title>${locale_error_head}!</title>--%>
</head>
<body>
<%--<jsp:include page="header.jsp"/>                --%>
<div class="container bg-dark py-2 mt-5 label_window">      <%--    //TODO add css library--%>
    <h3 class="mb-0 ml-3" style="color: white">${locale_error_error}!</h3>
</div>
<div class="container payment_window mb-5 pt-3 pb-5">
    <div class="container mt-5">
        <h2 class="mt-5">${text_error_tech_problems}</h2>
        <h2 class="mt-1">${text_error_tech_come_later}</h2>
    </div>
</div>

<%--<jsp:include page="footer.jsp"/>                --%>
</body>
</html>