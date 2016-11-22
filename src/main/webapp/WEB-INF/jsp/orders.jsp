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
                <c:forEach items="${order.pizzas}" var="item">
                    <ul>
                        <li><c:out value="${item}"/></li>
                    </ul>
                </c:forEach>
            </td>
            <td>
                edit
                <%--<form action="./customers/${customer.customerId}/edit" method="post">--%>
                    <%--<input hidden="true" value="${customer.customerId}">--%>
            <%--<input type="submit" value="edit">--%>
            <%--</form>--%>
            </td>
        </tr>
    </c:forEach>
</table>

<%--<form action="./customers/0/create" method="get">--%>
    <%--<input type="submit" value="New">--%>
<%--</form>--%>

</body>
</html>