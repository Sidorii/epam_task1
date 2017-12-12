<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Salads</title>
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
    <div class="row">
        <div class="col-sm-6">
            <h1>Salads to order:</h1>
        </div>
        <div class="col-sm-6">
            <h2><a href="/order/salad/custom/">Make custom salad for yourself</a></h2>
        </div>
    </div>
    <c:forEach items="${requestScope.dishes}" var="dish">
        <c:url var="salad" value="/salad?name=${dish.name}"/>
        <div class="jumbotron">
            <h1><a href="${salad}">${dish.name}</a></h1>
            Weight: <b>${dish.weight}</b>
            Price:<b> ${dish.price}</b>
            Calories: <b>${dish.calories}</b>
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
                    <form action="/remove/salad" method="post">
                        <input hidden name="id" value="${dish.id}">
                        <input type="submit" style="background-color: lightpink" class="btn btn-danger" value="Delete">
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>