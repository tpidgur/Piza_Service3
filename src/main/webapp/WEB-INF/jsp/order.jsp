<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit Order <c:out value="${order.orderId}"/></title>
</head>
<body>
<form action="../save" method="post">
    <input name="orderId" type="hidden" value="${order.orderId}"/>

    <table>
        <tr>
            <td>Customer name</td>
            <td><input name="name" type="text" value="${customer.name}"/><br/></td>
        </tr>
        <tr>
            <td>Order status</td>
            <td>
                <form:hidden path="customer.address.id"/>
                <input name="address" type="text" value="${customer.address.address}"/>
            </td>
        </tr>
        <tr>
            <td>Date</td>
            <td>
                <form:hidden path="customer.card.id"/>
                <input name="balance" type="text" value="${customer.card.balance}"/><br/>
            </td>
        </tr>
        <tr>
            <td>Delivery address</td>
            <td>???????</td>
        </tr>
        <tr>
            <td>List of pizzas</td>
            <td>???????</td>
        </tr>

    </table>
    <input type="submit" value="Save"/>
</form>
</body>
</html>
