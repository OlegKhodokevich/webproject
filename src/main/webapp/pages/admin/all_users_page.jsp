<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="text"/>

<fmt:message key="registration.city" var="text_registration_city"/>
<fmt:message key="registration.firstname" var="text_registration_firstname"/>
<fmt:message key="registration.lastname" var="text_registration_lastname"/>
<fmt:message key="registration.phone" var="text_registration_phone"/>
<fmt:message key="registration.region" var="text_registration_region"/>
<fmt:message key="registration.city" var="text_registration_city"/>


<fmt:message key="user.admin_all_user_title" var="text_user_admin_all_user_title"/>
<fmt:message key="main.title" var="text_main_title"/>
<fmt:message key="user.role" var="text_user_role"/>
<fmt:message key="user.status" var="text_user_status"/>
<fmt:message key="user.detail" var="text_user_detail"/>


<fmt:message key="order.orders" var="text_order_orders"/>
<fmt:message key="contract.contracts" var="text_contract_contracts"/>

<html>
<head>
    <title>${text_user_admin_all_user_title}</title>
    <link href="../../css/users_style.css">
</head>
<body>
<header>
    <jsp:include page="../header.jsp"/>
</header>
<table class="table table-striped table-bordered table-sm">
    <thead>
    <tr>
        <th scope="col">№</th>
        <th scope="col">ID</th>
        <th scope="col">${text_registration_firstname}</th>
        <th scope="col">${text_registration_lastname}</th>
        <th scope="col">e-mail</th>
        <th scope="col">${text_registration_phone}</th>
        <th scope="col">${text_registration_region}</th>
        <th scope="col">${text_registration_city}</th>
        <th scope="col">${text_user_role}</th>
        <th scope="col">${text_user_status}</th>
        <th scope="col">${text_user_detail}</th>
        <th scope="col">${text_order_orders}</th>
        <th scope="col">${text_contract_contracts}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${sessionScope.userList}" varStatus="loop">
        <tr>
            <th scope="row">${sessionScope.pagination.lastIndexBeforeFirstItemOnPage + 1 + loop.index}</th>
            <td>${user.idUser}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.EMail}</td>
            <td>${user.phone}</td>
            <fmt:message key="${applicationScope.regionMap.get(user.region)}" var="text_region"/>
            <td>${text_region}</td>
            <td>${user.city}</td>
            <td>${user.role}</td>
            <td>

                <div class="nav-item dropdown navbar-nav mb-2 mb-lg-0">
                    <p class="nav-link dropdown-toggle" href="#" id="profileExecutor" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                            ${user.status}
                    </p>
                    <ul class="dropdown-menu" aria-labelledby="profileExecutor">
                        <c:forEach var="status" items="${applicationScope.statusList}">
                            <c:if test="${status ne user.status}">
                                <li><a class="dropdown-item"
                                       href="/controller?command=set_user_status&statusCommand=${status}&userId=${user.idUser}&index=${loop.index}">${status}</a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>

            </td>
            <td>
                <c:choose>
                    <c:when test="${user.role eq 'EXECUTOR'}">
                        <a class="btn btn-info"
                           href="/controller?command=find_executor_info_details&executorId=${user.idUser}">${text_user_detail}</a>
                    </c:when>
                    <c:when test="${user.role eq 'CUSTOMER'}">
                        <a class="btn btn-info"
                           href="/controller?command=find_user_info_details&userId=${user.idUser}">${text_user_detail}</a>
                    </c:when>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${user.role eq 'CUSTOMER'}">
                        <a class="btn btn-info"
                           href="/controller?command=find_user_orders&userId=${user.idUser}">${text_order_orders}</a>
                    </c:when>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${user.role eq 'CUSTOMER'}">
                        <a class="btn btn-info"
                           href="/controller?command=find_contract_by_customer_id&userId=${user.idUser}">${text_contract_contracts}</a>
                    </c:when>
                    <c:when test="${user.role eq 'EXECUTOR'}">
                        <a class="btn btn-info"
                           href="/controller?command=find_contract_by_executor_id&executorId=${user.idUser}">${text_contract_contracts}</a>
                    </c:when>
                </c:choose>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>

<nav aria-label="Page navigation area">
    <ul class="pagination justify-content-center">
        <c:if test="${sessionScope.pagination.currentPage > 1}">
            <li class="page-item ${sessionScope.pagination.currentPage eq 1 ? 'disabled': ''}">
                <a class="page-link"
                   href="/controller?command=all_users&indexPage=${sessionScope.pagination.currentPage - 1}"
                   tabindex="-1"><<</a>
            </li>
        </c:if>
        <li class="page-item ${sessionScope.pagination.currentPage eq 1 ? 'active': ''}">
            <a class="page-link" href="/controller?command=all_users&indexPage=${1}">1</a>
        </li>
        <c:if test="${sessionScope.pagination.showLeftDivider()}">
            <li class="page-item disabled}">
                <a class="page-link disabled" href="">...</a>
            </li>
        </c:if>
        <c:forEach var="i" items="${sessionScope.pagination.listVisiblePage}">
            <li class="page-item ${sessionScope.pagination.currentPage eq i ? 'active': ''}">
                <a class="page-link"
                   href="/controller?command=all_users&indexPage=${i}">${i}</a>
            </li>
        </c:forEach>
        <c:if test="${sessionScope.pagination.showRightDivider()}">
            <li class="page-item disabled}">
                <a class="page-link disabled" href="">...</a>
            </li>
        </c:if>
        <c:if test="${sessionScope.pagination.lastPage > 1}">
            <li class="page-item ${sessionScope.pagination.currentPage eq sessionScope.pagination.lastPage ? 'active': ''}">
                <a class="page-link"
                   href="/controller?command=all_users&indexPage=${sessionScope.pagination.lastPage}">${sessionScope.pagination.lastPage}</a>
            </li>
        </c:if>
        <c:if test="${sessionScope.pagination.currentPage < sessionScope.pagination.lastPage}">
            <li class="page-item ${sessionScope.pagination.currentPage eq sessionScope.pagination.lastPage ? 'disabled': ''}">
                <a class="page-link"
                   href="/controller?command=all_users&indexPage=${sessionScope.pagination.currentPage + 1}"
                   tabindex="-1">>></a>
            </li>
        </c:if>
    </ul>
</nav>

<%--<script>--%>
<%--    let users = document.querySelector()${sessionScope.userList};--%>
<%--    let table = document.querySelector('#table');--%>
<%--    let pagination = document.querySelector('#pagination');--%>

<%--    let notesOnPage = 3;--%>
<%--    let showPage = (function showPage(item) {--%>
<%--        let active;--%>
<%--        return function (item) {--%>

<%--            if (active) {--%>
<%--                active.classList.remove('active');--%>
<%--            }--%>
<%--            active = item;--%>
<%--            item.classList.add('active');--%>
<%--            let pageNum = +item.innerHTML;--%>
<%--            /*--%>

<%--             */--%>

<%--            let startIndex = notesOnPage * (pageNum - 1);--%>
<%--            let endIndex = startIndex + notesOnPage;--%>
<%--            let notes = users.slice(startIndex, endIndex);--%>
<%--            table.innerHTML ='';--%>
<%--            for (let note of notes) {--%>
<%--                let tr = document.createElement('tr');--%>
<%--                table.appendChild(tr);--%>

<%--                createCell(note.get)--%>
<%--            }--%>
<%--        };--%>
<%--    }());--%>
<%--    let countOfItems = Math.ceil(users.length / notesOnPage);--%>
<%--    let items = [];--%>
<%--    for (let i = 1; i <= countOfItems; i++) {--%>
<%--        let li = document.createElement('li');--%>
<%--        li.innerHTML = i;--%>
<%--        pagination.appendChild(li);--%>
<%--        items.push(li);--%>
<%--    }--%>
<%--    showPage(items[0]);--%>

<%--    for (let item of items) {--%>
<%--        item.addEventListener('click', function () {--%>
<%--            showPage(this)--%>
<%--        });--%>
<%--    }--%>


<%--    function createCell(text, tr) {--%>
<%--        let td = document.createElement('div')--%>
<%--    }--%>
<%--</script>--%>

<footer>
    <jsp:include page="../footer.jsp"/>
</footer>
</body>
</html>
