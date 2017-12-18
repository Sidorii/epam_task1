<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create ingredient</title>
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
                <li><a href="/">${requestScope.bundle.getString("header.home")}</a></li>
                <li><a href="/salads">${requestScope.bundle.getString("header.order")}</a></li>
                <li><a href="/ingredients">${requestScope.bundle.getString("header.ingredients")}</a></li>
                <li class="active">${requestScope.bundle.getString("header.ingredient")}</li>
                <li><a href="/create/salad">${requestScope.bundle.getString("header.recipe")}</a></li>
            </ol>
        </div>
    </div>
    <c:if test="${not empty requestScope.exception}">
        <div style="color: red">
            <h5>${requestScope.exception}</h5>
        </div>
    </c:if>
    <h2 class="col-sm-offset-4">Create new ingredient:</h2>
    <form action="/create/ingredient/" method="POST">
        <div class="form-group" style="padding-bottom: 30px">
            <label class="control-label col-sm-2">Name:</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" placeholder="Enter name" name="name">
            </div>
        </div>
        <div class="form-group" style="padding-bottom: 30px">
            <label class="control-label col-sm-2">Weight:</label>
            <div class="col-sm-10">
                <input type="number" min="10" value="1000" class="form-control" placeholder="Enter weight"
                       name="weight">
            </div>
        </div>
        <div class="form-group" style="padding-bottom: 30px">
            <label class="control-label col-sm-2">Calories:</label>
            <div class="col-sm-10">
                <input type="number" min="0" value="1000" class="form-control" placeholder="Enter calories"
                       name="calories">
            </div>
        </div>
        <div class="form-group" style="padding-bottom: 30px">
            <label class="control-label col-sm-2">Price:</label>
            <div class="col-sm-10">
                <input type="number" min="0" value="1000" class="form-control" placeholder="Enter price" name="price">
            </div>
        </div>
        <div class="form-group" style="padding-bottom: 30px">
            <label class="control-label col-sm-2 radio-inline">Fresh:</label>
            <div class="col-sm-10">
                <input type="radio" checked value="true" name="fresh">True
                <input type="radio" value="false" name="fresh">False
            </div>
        </div>
        <div class="form-group" style="padding-bottom: 30px">
            <label class="control-label col-sm-2">Description:</label>
            <div class="col-sm-10">
                <textarea rows="4" class="form-control" placeholder="Enter description" name="description"></textarea>
            </div>
        </div>
        <div class="form-group" style="padding-bottom: 30px">
            <label class="control-label col-sm-2 radio-inline">Type:</label>
            <div class="col-sm-10">
                <c:forEach var="type" items="${requestScope.types}">
                    <input type="radio" checked="checked" value="${type.name()}"
                           name="type">${type.name().toLowerCase()}
                </c:forEach>
            </div>
        </div>
        <div class="form-group" style="padding-bottom: 30px">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">Create</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
