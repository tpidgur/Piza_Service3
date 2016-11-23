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
<h1>Order list</h1>
<table>
    <tr>
        <th>Id</th>
        <th>Customer id</th>
        <th>Customer name</th>
        <th>Order status</th>
        <th>Date</th>
        <th>Delivery address</th>
        <th>List of pizzas</th>
        <th>Number of pizzas</th>
        <th>Edit</th>
    </tr>
    <c:forEach items="${orderList}" var="order">
        <tr>
            <td><c:out value="${order.orderId}"/></td>
            <td><c:out value="${order.customer.customerId}"/></td>
            <td><c:out value="${order.customer.name}"/></td>
            <td><c:out value="${order.status}"/></td>
            <td><c:out value="${order.date}"/></td>
            <td><c:out value="${order.address}"/></td>

            <td>
                <c:forEach items="${order.pizzas.keySet()}" var="pizza">
                    <ul>
                        <li><c:out value="${pizza}"/></li>
                    </ul>
                </c:forEach>
            </td>
            <td>
                <c:forEach items="${order.pizzas.values()}" var="amount">
                    <ul>
                        <li><c:out value="${amount}"/></li>
                    </ul>
                </c:forEach>
            </td>
            <td>
                <form action="./orders/${order.orderId}/edit" method="post">
                    <input hidden="true" value="${order.orderId}">
                    <input type="submit" value="edit">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<%--<form action="./orders/order/create" method="get">--%>
<%--<input type="submit" value="New">--%>
<%--</form>--%>

</body>
</html>