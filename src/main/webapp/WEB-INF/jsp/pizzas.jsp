<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
<div align="center">
    <h1>Pizzas list</h1>
    <table>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Type</th>
            <th>Price</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${pizzasList}" var="pizza">
            <tr>
                <td align="center"><c:out value="${pizza.pizzaId}"/></td>
                <td align="center"><c:out value="${pizza.name}"/></td>
                <td align="center"><c:out value="${pizza.type}"/></td>
                <td align="center"><c:out value="${pizza.price}"/></td>
                <form:form action="./pizzas/${pizza.pizzaId}/edit" method="post">
                    <input id="id" name="id" hidden="true" value="${pizza.pizzaId}">
                    <td align="center"><input type="submit" value="edit"></td>
                </form:form>
                <form:form action="./pizzas/confirm" method="get">
                    <input id="id" name="id" hidden="true" value="${pizza.pizzaId}">
                <td align="center"><input type="submit" value="delete"></td>
                </form:form>
                <%--<form:form action="./pizzas/${pizza.pizzaId}/delete" method="post">--%>
                    <%--<input id="id" name="id" hidden="true" value="${pizza.pizzaId}">--%>
                    <%--<td align="center"><input type="submit" value="delete"></td>--%>
                <%--</form:form>--%>
            </tr>
        </c:forEach>

    </table>
    <br/>
    <form:form action="./pizzas/pizza/create" method="get">
        <input type="submit" value="New">
    </form:form>
</div>
</body>
</html>