<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Create Recipe</title>
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

        .dlk-radio input[type="radio"],
        .dlk-radio input[type="checkbox"] {
            margin-left: -99999px;
            display: none;
        }

        .dlk-radio input[type="radio"] + .fa,
        .dlk-radio input[type="checkbox"] + .fa {
            opacity: 0.15
        }

        .dlk-radio input[type="radio"]:checked + .fa,
        .dlk-radio input[type="checkbox"]:checked + .fa {
            opacity: 1
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
                <ol class="breadcrumb" style="margin-top: 25px">
                    <li><a href="/">${requestScope.bundle.getString("header.home")}</a></li>
                    <li><a href="/salads">${requestScope.bundle.getString("header.order")}</a></li>
                    <li><a href="/ingredients">${requestScope.bundle.getString("header.ingredients")}</a></li>
                    <li><a href="/create/ingredient">${requestScope.bundle.getString("header.ingredient")}</a></li>
                    <li class="active">${requestScope.bundle.getString("header.recipe")}</li>
                </ol>
            </ol>
        </div>
    </div>
    <c:if test="${not empty requestScope.exception}">
        <div style="color: red">
            <h4>${requestScope.exception}</h4>
        </div>
    </c:if>
    <h2 class="col-sm-offset-4">${requestScope.title}</h2>
    <form action="${requestScope.action}" class="form-horizontal" method="POST">
        <div class="form-group">
            <label class="control-label col-sm-2" for="email">Name:</label>
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
                <button type="submit" class="btn btn-default">Create</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
