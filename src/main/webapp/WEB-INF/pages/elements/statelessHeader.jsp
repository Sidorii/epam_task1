<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>

<c:set var="language" scope="session" value="${empty sessionScope.lang ? 'en_EN' : sessionScope.lang}"/>
<fmt:setLocale value="${language}"/>
<fmt:bundle basename="MessageBundle" prefix="header.">
    <div class=" row page-header">
        <div class="col-sm-3">
            <h1><fmt:message key="chef"/></h1>
        </div>
        <div class="col-sm-9">
            <ol class="breadcrumb" style="position: relative; margin-top: 25px; float: right">
                <li><a href="<c:url value="/"/>"><fmt:message key="home"/></a></li>
                <li><a href="<c:url value="/salads"/>"><fmt:message key="order"/></a></li>
                <li>
                    <a href="<c:url value="/ingredients"/>"><fmt:message key="ingredients"/></a>
                </li>
                <c:if test="${not empty sessionScope.auth}">
                    <li>
                        <a href="<c:url value="/storekeeper/create/ingredient"/>"><fmt:message
                                key="ingredient"/></a>
                    </li>
                    <li><a href="<c:url value="/chef/create/salad"/>"><fmt:message key="recipe"/></a>
                    </li>
                </c:if>
                <jsp:include page="dropdownHeader.jsp"/>
            </ol>
        </div>
    </div>
</fmt:bundle>
