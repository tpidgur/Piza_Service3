<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 5px;
            text-align: left;
        }
    </style>
</head>
<body>

<table style="width:100%">
    <caption><h1>Order list</h1></caption>
    <tr>
        <th>Id</th>
        <th colspan="2">Customer</th>
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
            <td><c:out value="${order.address.address}"/></td>

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
                <form:form action="./orders/${order.orderId}/edit" method="post">
                    <input hidden="true" value="${order.orderId}">
                    <input type="submit" value="edit">
                </form:form>
            </td>
        </tr>
    </c:forEach>
</table>

<form:form action="./orders/order/create" method="get">
    <input type="submit" value="New">
</form:form>

</body>
</html>