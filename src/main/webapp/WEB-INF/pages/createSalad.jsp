<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="language" scope="session" value="${empty sessionScope.lang ? 'en_EN' : sessionScope.lang}"/>
<fmt:setLocale value="${language}"/>
<html>
<head>
    <title>Create Recipe</title>
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
                        <li>
                            <a href="<c:url value="/storekeeper/create/ingredient"/>"><fmt:message
                                    key="ingredient"/></a>
                        </li>
                        <li class="active"><fmt:message key="recipe"/>
                        </li>
                    </c:if>
                    <jsp:include page="elements/dropdownHeader.jsp"/>
                </ol>
            </div>
        </div>
    </fmt:bundle>
    <fmt:bundle basename="MessageBundle" prefix="salad.">
        <c:if test="${not empty requestScope.invalid}">
            <div style="color: red">
                <h4>${requestScope.invalid}</h4>
            </div>
        </c:if>
        <h2 class="col-sm-offset-4"><fmt:message key="${requestScope.title}"/>:</h2>
        <form action="${requestScope.action}" class="form-horizontal" method="POST">
            <div class="form-group">
                <label class="control-label col-sm-2" for="email"><fmt:message key="name"/>:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="email" placeholder="Enter name" name="name">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:forEach items="${requestScope.ingredients}" var="ingredient">
                        <div class="dlk-radio btn-group" style="margin-bottom: 10px; margin-right: 5px">
                            <label class="btn btn-success">
                                <input name="id" class="form-control" type="checkbox" value="${ingredient.id}">
                                <i class="fa fa-check glyphicon glyphicon-ok"></i>
                                    ${ingredient.name}
                            </label>
                            <input class="form-control" style="max-width: 100px" type="number"
                                   name="${ingredient.id}_weight" min="1" value="50">
                        </div>
                    </c:forEach>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-default"><fmt:message key="create"/></button>
                </div>
            </div>
        </form>
    </fmt:bundle>
</div>
</body>
</html>
