<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.requestScope.salad.name}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <style>
        @media (min-width: 1200px) {
            .container {
                max-width: 970px;
            }
        }

        @media (min-width: 1200px) {
            .jumbotron {
                padding-top: 1px;
                margin-top: 10px;
                padding-bottom: 1px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class=" row page-header">
        <div class="col-sm-4">
            <h1>${requestScope.bundle.getString("home.chef")}</h1>
        </div>
        <div class="col-sm-8">
            <ol class="breadcrumb" style="margin-top: 25px">
                <li><a href="/">${requestScope.bundle.getString("header.home")}</a></li>
                <li><a href="/salads">${requestScope.bundle.getString("header.order")}</a></li>
                <li><a href="/ingredients">${requestScope.bundle.getString("header.ingredients")}</a></li>
                <li><a href="/create/ingredient">${requestScope.bundle.getString("header.ingredient")}</a></li>
                <li><a href="/create/salad">${requestScope.bundle.getString("header.recipe")}</a></li>
            </ol>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-6">
            <h1>Salad:</h1>
        </div>
        <div class="col-sm-6">
            <h2><a href="/order/custom/salad">Make custom salad for yourself</a></h2>
        </div>
    </div>
    <div class="jumbotron">
        <h1>${requestScope.salad.name}</h1>
        Weight: <b>${requestScope.salad.weight}</b>
        Price:<b> ${requestScope.salad.price}</b>
        Calories: <b>${requestScope.salad.calories}</b>
        Is vegan: <b>${requestScope.salad.isVegan()}</b>
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
</div>
</body>
</html>
