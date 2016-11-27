<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit Order <c:out value="${order.orderId}"/></title>
</head>
<body>
<form:form action="../save" method="post" modelAttribute="myorder">
    <input name="orderId" type="hidden" value="${order.orderId}"/>

    <table>

        <tr>
            <td>Customer</td>
            <td><input name="customerId" type="text" value="${order.customer.customerId}"/><br/></td>
            <td><input name="name" type="hidden" value="${order.customer.name}"/><br/></td>
            <td><input name="addressId" type="hidden" value="${order.customer.address.addressId}"/><br/></td>
            <td><input name="address" type="hidden" value="${order.customer.address.address}"/><br/></td>
            <td><input name="balance" type="hidden" value="${order.customer.card.balance}"/><br/></td>
            <td><input name="id" type="hidden" value="${order.customer.card.id}"/><br/></td>
        </tr>
        <tr>
            <td>Order status</td>
            <td>
                <input name="status" type="text" value="${order.status}"/>
            </td>
        </tr>
        <tr>
            <td>Date</td>
            <td>
                <input name="date" type="text" value="${order.date}"/><br/>
            </td>
        </tr>
        <tr>
            <td>Delivery address</td>
            <td><input name="address" type="text" value="${order.address.address}"/><br/></td>
        </tr>

        <c:forEach items="${order.pizzas}" var="entry" varStatus="status">
            <tr>
                <td>${entry.key}</td>
                <td><input name="entry['${entry.key}']" value="${entry.value}"/></td>
            </tr>
        </c:forEach>


    </table>
    <input type="submit" value="Save"/>
</form:form>
</body>
</html>
