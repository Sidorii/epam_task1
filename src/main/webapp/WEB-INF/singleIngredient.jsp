<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ingredient</title>
</head>
<body>
<table>
    <tr>
        <td>Name: ${requestScope.ingredient.name}</td>
        <td>Weight: ${requestScope.ingredient.weight}</td>
        <td>Calories: ${requestScope.ingredient.calories}</td>
        <td>Price: ${requestScope.ingredient.price}</td>
        <td>Fresh: ${requestScope.ingredient.fresh}</td>
    </tr>
    <tr>
        <td>Description:</td>
        <td colspan="3"><textarea readonly cols="40">${requestScope.ingredient.description}</textarea></td>
    </tr>
    <tr>
        <td>Type: ${requestScope.ingredient.type.name().toLowerCase()}</td>
    </tr>
</table>
</body>
</html>
