<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="language" scope="session" value="${empty sessionScope.lang ? 'en_EN' : sessionScope.lang}" />
<fmt:setLocale value="${language}"/>
<html>
<head>
    <title>Ingredient</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="/resources/style.css" type="text/css">

</head>
<body>
<div class="container">
    <jsp:include page="elements/statelessHeader.jsp"/>
    <div class="jumbotron">
        <h2>${requestScope.ingredient.name}</h2>
        Weight: <b>${requestScope.ingredient.weight}</b>
        Calories: <b>${requestScope.ingredient.calories}</b>
        Price:<b> ${requestScope.ingredient.price}</b>
        Fresh: <b>${requestScope.ingredient.fresh}</b>
        Type: <b>${requestScope.ingredient.type.name().toLowerCase()}</b>
        <h6>${requestScope.ingredient.description}</h6>
        <div class="row">
            <div class="col-sm-11">
            </div>
            <div class="col-sm-1">
                <form action="/remove/ingredient" method="post">
                    <input hidden name="id" value="${requestScope.ingredient.id}">
                    <input type="submit" style="background-color: lightpink" class="btn btn-danger" value="Delete">
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
