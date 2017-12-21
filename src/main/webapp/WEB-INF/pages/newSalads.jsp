<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="ex" uri="/WEB-INF/custom.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="language" scope="session" value="${empty sessionScope.lang ? 'en_EN' : sessionScope.lang}"/>
<fmt:setLocale value="${language}"/>
<html>
<head>
    <title>Salads</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/resources/style.css" type="text/css">
</head>
<body>
<div class="container">
    <fmt:bundle basename="MessageBundle" prefix="header.">
        <div class=" row page-header">
            <div class="col-sm-3">
                <h1><fmt:message key="chef"/></h1>
            </div>
            <div class="col-sm-9">
                <ol class="breadcrumb" style="position: relative; margin-top: 25px; float: right">
                    <li><a href="<c:url value="/"/>"><fmt:message key="home"/></a></li>
                    <li class="active"><fmt:message key="order"/></li>
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
        </div>
    </fmt:bundle>
    <fmt:bundle basename="MessageBundle" prefix="salad.">
        <div class="row">
            <div class="col-sm-6">
                <h1><fmt:message key="title"/>:</h1>
            </div>
            <div class="col-sm-6">
                <h2><a href="/order/custom/salad"><fmt:message key="new"/></a></h2>
            </div>
        </div>
        <c:forEach items="${requestScope.dishes}" var="dish">
            <c:url var="salad" value="/salad?name=${dish.name}"/>
            <div class="jumbotron">
                <h1><a href="${salad}">${dish.name}</a></h1>
                <fmt:message key="weight"/>: <b>${dish.weight}</b>
                <fmt:message key="price"/>:<b> ${dish.price}</b>
                <fmt:message key="calories"/>: <b>${dish.calories}</b>
                <fmt:message key="vegan"/>: <b>${dish.isVegan()}</b>
                <h6>${dish.description}</h6>
                <p>
                    <c:forEach items="${dish.ingredients}" var="ingredient">
                        ${ingredient.name} / ${ingredient.weight} gr;
                    </c:forEach>
                </p>
                <div class="row">
                    <div class="col-sm-11">

                    </div>
                    <div class="col-sm-1">
                        <form action="/order/salad" method="get">
                            <input hidden name="name" value="${dish.name}">
                            <input type="submit" class="btn btn-default" value="Order">
                        </form>
                        <ex:hasRole role="CHEF">
                            <form action="<c:url value="/chef/remove/salad"/>" method="post">
                                <input hidden name="id" value="${dish.id}">
                                <input type="submit" style="background-color: lightpink" class="btn btn-danger"
                                       value="Delete">
                            </form>
                        </ex:hasRole>
                    </div>
                </div>
            </div>
        </c:forEach>
    </fmt:bundle>
</div>
</body>
</html>