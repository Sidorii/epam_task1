<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Salads</title>
</head>
<body>
<form>
    <table>
        <c:forEach items="${requestScope.dishes}" var="dish">
            <tr>
                <td><b>Name: ${dish.name}</b></td>
                <td><b>Price: ${dish.price}</b></td>
                <td><b>Calories: ${dish.calories}</b></td>
                <td><b>Weight: ${dish.weight}</b></td>
            </tr>
            <tr>
            <tr>
                <td>Ingredients:</td>
            </tr>
            <c:forEach items="${dish.ingredients}" var="ingredient">
                <tr>
                    <td>Name: <input type="text" readonly name="name" value="${ingredient.name}"></td>
                    <td>Weight: <input type="text" readonly value="${ingredient.weight}"></td>
                    <td>Price: <input type="text" readonly value="${ingredient.price}"></td>
                    <td>Calories: <input type="text" readonly value="${ingredient.calories}"></td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</form>
</body>
</html>
