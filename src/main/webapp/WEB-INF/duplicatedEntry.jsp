<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Duplicated Entry!</title>
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
    <h1>Duplicated entity</h1>
    <c:if test="${not empty requestScope.entity}">
        <p> Entity name: ${requestScope.entity.name}</p>
    </c:if>
    <c:if test="${not empty requestScope.message}">
        <p>${requestScope.message}</p>
    </c:if>
</div>
</body>
</html>
