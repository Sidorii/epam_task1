<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Not found!</title>
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
            <h1>Salad chef</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-offset-3">
            <h1>Welcome to salad chef! </h1>
        </div>
    </div>
    <div class="col-sm-offset-2 col-sm-8">
        <ol class="breadcrumb" style="margin-top: 25px">
            <li class="active">Home</li>
            <li><a href="/salads">Order salad</a></li>
            <li><a href="/ingredients">Ingredients we use</a></li>
            <li><a href="/create/ingredient">Create ingredient</a></li>
            <li><a href="/create/salad">Create recipe</a></li>
        </ol>
    </div>
</div>
</body>
</html>

