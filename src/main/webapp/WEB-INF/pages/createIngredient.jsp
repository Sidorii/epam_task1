<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="language" scope="session" value="${empty sessionScope.lang ? 'en_EN' : sessionScope.lang}" />
<fmt:setLocale value="${language}"/>
<html>
<head>
    <title>Create ingredient</title>
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
                    <li>
                        <a href="<c:url value="/ingredients"/>"><fmt:message key="ingredients"/></a>
                    </li>
                    <c:if test="${not empty sessionScope.auth}">
                        <li class="active">
                            <fmt:message key="ingredient"/>
                        </li>
                        <li><a href="<c:url value="/chef/create/salad"/>"><fmt:message key="recipe"/></a>
                        </li>
                    </c:if>
                    <jsp:include page="elements/dropdownHeader.jsp"/>
                </ol>
            </div>
        </div>
    </fmt:bundle>
    <fmt:bundle basename="MessageBundle" prefix="ingredient.">
        <c:if test="${not empty requestScope.invalid}">
            <div style="color: red">
                <h5>${requestScope.invalid}</h5>
            </div>
        </c:if>
        <h2 class="col-sm-offset-4"><fmt:message key="caption"/>:</h2>
        <form action="/create/ingredient/" method="POST">
            <div class="form-group" style="padding-bottom: 30px">
                <label class="control-label col-sm-2"><fmt:message key="name"/>:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" placeholder="Enter name" name="name">
                </div>
            </div>
            <div class="form-group" style="padding-bottom: 30px">
                <label class="control-label col-sm-2"><fmt:message key="weight"/>:</label>
                <div class="col-sm-10">
                    <input type="number" min="10" value="1000" class="form-control" placeholder="Enter weight"
                           name="weight">
                </div>
            </div>
            <div class="form-group" style="padding-bottom: 30px">
                <label class="control-label col-sm-2"><fmt:message key="calories"/>:</label>
                <div class="col-sm-10">
                    <input type="number" min="0" value="1000" class="form-control" placeholder="Enter calories"
                           name="calories">
                </div>
            </div>
            <div class="form-group" style="padding-bottom: 30px">
                <label class="control-label col-sm-2"><fmt:message key="price"/>:</label>
                <div class="col-sm-10">
                    <input type="number" min="0" value="1000" class="form-control" placeholder="Enter price"
                           name="price">
                </div>
            </div>
            <div class="form-group" style="padding-bottom: 30px">
                <label class="control-label col-sm-2 radio-inline"><fmt:message key="fresh"/>:</label>
                <div class="col-sm-10">
                    <input type="radio" checked value="true" name="fresh"><fmt:message key="true"/>
                    <input type="radio" value="false" name="fresh"><fmt:message key="false"/>
                </div>
            </div>
            <div class="form-group" style="padding-bottom: 30px">
                <label class="control-label col-sm-2"><fmt:message key="description"/>:</label>
                <div class="col-sm-10">
                    <textarea rows="4" class="form-control" placeholder="Enter description"
                              name="description"></textarea>
                </div>
            </div>
            <div class="form-group" style="padding-bottom: 30px">
                <label class="control-label col-sm-2 radio-inline"><fmt:message key="type"/>:</label>
                <div class="col-sm-10">
                    <c:forEach var="type" items="${requestScope.types}">
                        <input type="radio" checked="checked" value="${type.name()}"
                               name="type">${type.name().toLowerCase()}
                    </c:forEach>
                </div>
            </div>
            <div class="form-group" style="padding-bottom: 30px">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default"><fmt:message key="create"/></button>
                </div>
            </div>
        </form>
    </fmt:bundle>
</div>
</body>
</html>
