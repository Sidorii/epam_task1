<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.requestScope.salad.name}</title>
</head>
<body>
<table>
    <tr>
        <td><b>Name: ${requestScope.salad.name}</b></td>
        <td><b>Price: ${requestScope.salad.price}</b></td>
        <td><b>Calories: ${requestScope.salad.calories}</b></td>
        <td><b>Weight: ${requestScope.salad.weight}</b></td>
    </tr>
    <tr>
    <tr>
        <td>Ingredients:</td>
    </tr>
    <c:forEach items="${requestScope.salad.ingredients}" var="ingredient">
        <tr>
            <td>Name: <input type="text" readonly name="name" value="${ingredient.name}"></td>
            <td>Weight: <input type="text" readonly value="${ingredient.weight}"></td>
            <td>Price: <input type="text" readonly value="${ingredient.price}"></td>
            <td>Calories: <input type="text" readonly value="${ingredient.calories}"></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
