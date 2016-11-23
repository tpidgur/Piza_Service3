<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
<h1>Pizzas list</h1>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Type</th>
        <th>Price</th>
    </tr>
    <c:forEach items="${pizzasList}" var="pizza">
        <tr>
            <td><c:out value="${pizza.pizzaId}"/></td>
            <td><c:out value="${pizza.name}"/></td>
            <td><c:out value="${pizza.type}"/></td>
            <td><c:out value="${pizza.price}"/></td>
            <form action="./pizzas/${pizza.pizzaId}/edit" method="post">
                <input id="id" name="id" hidden="true" value="${pizza.pizzaId}">
                <td><input type="submit" value="edit"></td>
            </form>
        </tr>
    </c:forEach>

</table>
<form action="./pizzas/pizza/create" method="get">
    <input type="submit" value="New">
</form>

</body>
</html>