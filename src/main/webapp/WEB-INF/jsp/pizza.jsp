<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

</head>
<body>
<h1>Edit Pizza</h1>
<form action="pizzas/save" method="post">
    <input id="pizzaId" name="pizzaId" type="hidden"  value="${pizza.pizzaId}"/>
    <table>
        <tr>
            <td>Pizza name</td>
            <td><input id="pizza_name" type="text" name="name" value="${pizza.name}"/><br/></td>
        </tr>
        <tr>
            <td>Pizza type</td>
            <td>
                <select name="type">
                    <c:forEach items="${types}" var="type">
                        <option value="${type}">${type}</option>
                    </c:forEach>
                </select>
                <br/></td>
        </tr>
        <tr>
            <td>Pizza price</td>
            <td><input id="pizza_price" type="text" name="price" value="${pizza.price}"/><br/></td>
        </tr>
    </table>
    <input type="submit" value="Save"/>
</form>
</body>
</html>