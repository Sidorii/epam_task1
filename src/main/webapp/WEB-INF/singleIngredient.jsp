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
        ul.dropper li a {
            color: #0275d8;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="container">
    <div class=" row page-header">
        <div class="col-sm-3">
            <h1>${requestScope.bundle.getString("home.chef")}</h1>
        </div>
        <div class="col-sm-9">
            <ol class="breadcrumb" style="margin-top: 25px">
                <li><a href="/">${requestScope.bundle.getString("header.home")}</a></li>
                <li><a href="/salads">${requestScope.bundle.getString("header.order")}</a></li>
                <li><a href="/ingredients">${requestScope.bundle.getString("header.ingredients")}</a></li>
                <li><a href="/create/ingredient">${requestScope.bundle.getString("header.ingredient")}</a></li>
                <li><a href="/create/salad">${requestScope.bundle.getString("header.recipe")}</a></li>
                <li>
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span
                            class="glyphicon glyphicon-menu-hamburger"></span></a>
                    <ul class="dropper dropdown-menu dropdown-menu-sw" role="menu">
                        <c:choose>
                            <c:when test="${not empty sessionScope.auth}">
                                <li style="text-align: center">You, <b>${sessionScope.auth.name}</b>!</li>
                                <li class="divider"></li>
                                <li style="text-align: center"><a href="<c:url value="/logout"/>">Logout</a></li>
                            </c:when>
                            <c:otherwise>
                                <li style="text-align: center"><a href="<c:url value="/login"/>">Login</a></li>
                                <li style="text-align: center"><a href="<c:url value="/registration"/>">Registration</a></li>
                            </c:otherwise>
                        </c:choose>
                        <li class="divider"></li>
                        <li style="text-align: center"><b>Select language</b></li>
                        <li class="divider"></li>
                        <li style="text-align: center"><a href="/locale?land=en_EN">English</a></li>
                        <li style="text-align: center"><a href="/locale?land=ru_UA">Ukrainian</a></li>
                        <li style="text-align: center"><a href="/locale?land=ru_RU">Russian</a></li>
                    </ul>
                </li>
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
