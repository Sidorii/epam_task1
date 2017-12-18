<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ingredient</title>
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
