<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="language" scope="session" value="${empty sessionScope.lang ? 'en_EN' : sessionScope.lang}" />
<fmt:setLocale value="${language}"/>
<html>
<head>
    <title>${requestScope.bundle.getString("home.title")}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/resources/style.css">
</head>
<body>
<div class="container">
    <fmt:bundle basename="MessageBundle" prefix="home.">
        <div class=" row page-header">
            <div class="col-sm-4">
                <h1><fmt:message key="chef"/></h1>
            </div>
        </div>
        <div class="row">
            <div class="col" style="text-align: center">
                <h1><fmt:message key="welcome"/></h1>
            </div>
        </div>
    </fmt:bundle>
    <fmt:bundle basename="MessageBundle" prefix="header.">
        <div class="col-sm-offset-1 col-sm-10">
            <ol class="breadcrumb" style="margin-top: 25px;position: relative; text-align: center;">
                <li class="active"><fmt:message key="home"/></li>
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
                <jsp:include page="elements/dropdownHeader.jsp"/>
            </ol>
        </div>
    </fmt:bundle>
</div>
</body>
</html>