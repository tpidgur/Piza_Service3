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
        <th rowspan="2">Id</th>
        <th colspan="2">Customer</th>
        <th rowspan="2">Status</th>
        <th rowspan="2">Date</th>
        <th rowspan="2">Delivery address</th>

        <th  rowspan="2">Pizzas</th>
        <th rowspan="2">Edit</th>
    </tr>
    <tr>
        <th>
            Id
        </th>
        <th>
            Name
        </th>

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
                <table style="width:100%">
                    <tr>
                        <th>Type</th>
                        <th>Amount</th>
                    </tr>
                    <c:forEach items="${order.pizzas}" var="entry">
                        <tr>
                            <td><c:out value="${entry.key}"/></td>
                            <td><c:out value="${entry.value}"/></td>
                        </tr>
                    </c:forEach>
                </table>
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
<br/>
<form:form action="./orders/order/create" method="get">
    <input type="submit" value="New">
</form:form>

</body>
</html>