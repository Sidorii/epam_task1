<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="language" scope="session" value="${empty sessionScope.lang ? 'en_EN' : sessionScope.lang}"/>
<fmt:setLocale value="${language}"/>
<html>
<head>
    <title>${requestScope.requestScope.salad.name}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/resources/style.css" type="text/css">

</head>
<body>
<div class="container">
    <jsp:include page="elements/statelessHeader.jsp"/>
    <fmt:bundle basename="MessageBundle" prefix="salad.">
        <div class="row">
            <div class="col-sm-6">
                <h1>Salad:</h1>
            </div>
            <div class="col-sm-6">
                <h2><a href="/order/custom/salad"><fmt:message key="new"/></a></h2>
            </div>
        </div>
        <div class="jumbotron">
            <h1>${requestScope.salad.name}</h1>
            <fmt:message key="weight"/>: <b>${requestScope.salad.weight}</b>
            <fmt:message key="price"/>:<b> ${requestScope.salad.price}</b>
            <fmt:message key="calories"/>: <b>${requestScope.salad.calories}</b>
            <fmt:message key="vegan"/>: <b>${requestScope.salad.isVegan()}</b>
            <h6>${requestScope.salad.description}</h6>
            <p>
                <c:forEach items="${requestScope.salad.ingredients}" var="ingredient">
                    ${ingredient.name} / ${ingredient.weight} gr;
                </c:forEach>
            </p>
            <div class="row">
                <div class="col-sm-11"></div>
                <div class="col-sm-1">
                    <form action="/order/salad" method="get">
                        <input hidden name="name" value="${requestScope.salad.name}">
                        <input type="submit" class="btn btn-default" value="Order">
                    </form>
                </div>
            </div>
        </div>
    </fmt:bundle>
</div>
</body>
</html>
