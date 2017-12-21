<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="language" scope="session" value="${empty sessionScope.lang ? 'en_EN' : sessionScope.lang}"/>
<fmt:setLocale value="${language}"/>/>
<html>
<head>
    <title>Duplicated Entry!</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/resources/style.css" type="text/css">

</head>
<body>
<div class="container">
    <jsp:include page="elements/statelessHeader.jsp"/>
    <fmt:bundle basename="MessageBundle" prefix="exceptions.">
        <h1><fmt:message key="duplicated"/></h1>
        <c:if test="${not empty requestScope.entity}">
            <p><fmt:message key="duplicated.name"/>: ${requestScope.entity.name}</p>
        </c:if>
        <c:if test="${not empty requestScope.message}">
            <p>${requestScope.message}</p>
        </c:if>
    </fmt:bundle>
</div>
</body>
</html>
