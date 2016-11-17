<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h1>Pizzas list</h1>
<table>
    <tbody>
    <tr>
        <th>pizzaId</th>
        <th>Name</th>
        <th>price</th>
    </tr>
    <c:forEach items="${pizzasList}" var="pizza">
        <tr>
            <td><c:out value="${pizza.pizzaId}"/></td>
            <td><c:out value="${pizza.name}"/></td>
            <td><c:out value="${pizza.price}"/></td>
        </tr>
    </c:forEach>
    <a href="newpizza">create new pizza</a>
    </tbody>
</table>
</body>
</html>