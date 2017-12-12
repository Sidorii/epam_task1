<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Available Ingredients</title>
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
            <div class="row">
                <div class="col-sm-11">
                </div>
                <div class="col-sm-1">
                    <form action="/remove/ingredient" method="post">
                        <input hidden name="id" value="${ingredient.id}">
                        <input type="submit" style="background-color: lightpink" class="btn btn-danger" value="Delete">
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
