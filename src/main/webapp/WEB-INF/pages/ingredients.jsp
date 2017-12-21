<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "ex" uri = "/WEB-INF/custom.tld"%>
<c:set var="language" scope="session" value="${empty sessionScope.lang ? 'en_EN' : sessionScope.lang}" />
<fmt:setLocale value="${language}"/>
<html>
<head>
    <title>Available Ingredients</title>
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
                    <li><a href="<c:url value="/salads"/>"><fmt:message key="order"/></a></li>
                    <li class="active">
                        <fmt:message key="ingredients"/>
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
    <h1>Available ingredients :</h1>
    <c:forEach var="ingredient" items="${requestScope.ingredients}">
        <div class="jumbotron">
            <h2>${ingredient.name}</h2>
            Weight: <b>${ingredient.weight}</b>
            Calories: <b>${ingredient.calories}</b>
            Price:<b> ${ingredient.price}</b>
            Fresh: <b>${ingredient.fresh}</b>
            Type: <b>${ingredient.type.name().toLowerCase()}</b>
            <h6>${ingredient.description}</h6>
            <ex:hasRole role="STOREKEEPER">
                <div class="row">
                    <div class="col-sm-11">
                    </div>
                    <div class="col-sm-1">
                        <form action="<c:url value="/storekeeper/remove/ingredient"/>" method="post">
                            <input hidden name="id" value="${ingredient.id}">
                            <input type="submit" style="background-color: lightpink" class="btn btn-danger"
                                   value="Delete">
                        </form>
                    </div>
                </div>
            </ex:hasRole>
        </div>
    </c:forEach>
</div>
</body>
</html>
