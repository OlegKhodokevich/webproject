<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="abs_path">${pageContext.request.contextPath}</c:set>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<fmt:message key="specialization.electrical" var="text_specialization_electrical"/>
<fmt:message key="specialization.plumbing" var="text_specialization_plumbing"/>
<fmt:message key="specialization.plastering" var="text_specialization_plastering"/>
<fmt:message key="specialization.laying_tiles" var="text_specialization_laying_tiles"/>
<fmt:message key="specialization.painting" var="text_specialization_painting"/>
<fmt:message key="specialization.wallpapering" var="text_specialization_wallpapering"/>
<fmt:message key="specialization.cement_floor" var="text_specialization_cement_floor"/>
<fmt:message key="specialization.floor_covering" var="text_specialization_floor_covering"/>
<fmt:message key="specialization.carpentry_work" var="text_specialization_carpentry_work"/>
<fmt:message key="specialization.turkey_house" var="text_specialization_turkey_house"/>
<fmt:message key="specialization.roof" var="text_specialization_roof"/>
<fmt:message key="specialization.monolite" var="text_specialization_monolite"/>
<fmt:message key="specialization.bricklaying" var="text_specialization_bricklaying"/>
<fmt:message key="specialization.fasad" var="text_specialization_fasad"/>
<fmt:message key="specialization.landscaping" var="text_specialization_landscaping"/>

<fmt:message key="executor.search" var="text_executor_search"/>
<fmt:message key="executor.executors" var="text_executor_executors"/>
<fmt:message key="executor.specializations" var="text_executor_specializations"/>
<fmt:message key="executor.empty_list" var="text_executor_empty_list"/>
<fmt:message key="project.executors" var="text_project_executors"/>

<html>
<head>
    <title>${text_project_executors}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <link href="../css/custom_styles.css" rel="stylesheet"/>
    <link href="../css/custom_button.css" rel="stylesheet" media="all"/>
    <link href="../css/custom_text.css" rel="stylesheet"/>
</head>
<body style="background-image: url(../image/building_3_c1.jpg);
background-repeat: no-repeat;
background-position: center center;
background-size: cover">
<header>
    <jsp:include page="header.jsp"/>
</header>

<h1 class="text-title">${text_executor_executors}</h1>
<div class="container">
    <div class="row">
        <div class="col" style="max-width: 300px">
            <div class="flex-column custom-card" style="width: 300px;">
                <p class="fs-5 fw-semibold">${text_executor_specializations}</p>
                <form action="/controller" method="post" class="list-group list-group-flush border-bottom scrollarea">
                    <c:forEach var="specialization" items="${applicationScope.specializationList}">
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
                    <div>
                        <input type="hidden" name="command" value="find_executors_by_specializations">
                        <input type="submit" value="${text_executor_search}"
                               style="background-color: #71dd8a; width: 150px">
                    </div>
                </form>
            </div>
        </div>

        <div class="col">
            <c:choose>
                <c:when test="${sessionScope.executorList.size() eq 0}">

                    <div class="container py-2 mt-5 label_window">
                        <h3 class="mb-0 ml-3" style="color: black">${text_executor_empty_list}</h3>
                    </div>

                </c:when>
                <c:when test="${sessionScope.executorList.size() gt 0}">
                    <div class="list-group">
                        <c:forEach var="executor" items="${sessionScope.executorList}">
                            <c:if test="${executor != null}">
                                <div class="container custom-card mb-2">
                                    <div class="row">
                                        <div class="col custom-card-image"
                                             style="--background: url('${executor.executorOption.urlPersonalFoto}')"></div>
                                        <a class="col custom-card-link"
                                           href="/controller?command=find_executor_info_details&executorId=${executor.idUser}">
                                            <div>
                                                <h5>${executor.firstName} ${executor.lastName}</h5>
                                            </div>
                                            <p>${executor.executorOption.description}</p>
                                            <input type="hidden" name="executorId" id="ExecutorId"
                                                   value="${executor.idUser}">
                                        </a>
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
