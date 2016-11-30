<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<h1>Edit Order </h1>
<form:form action="../save" method="post" modelAttribute="orderHolder">
    <td><input name="orderId" type="hidden" value="${order.orderId}"></td>
    <table>
        <tr>
            <td>Customer</td>

        <select  name="customerId">
            <c:forEach items="${customers}" var="customer" varStatus="status">
            <option value="${customer.customerId}"><c:out value="${customer}"/></option>
            </c:forEach>
        </select>
        </tr>
        <tr>
            <td>Date</td>
            <td><input name="date" type="text" value="${order.date}"></td>
        </tr>
        <tr>
        <td>Address</td>
        <td><input name="address" type="text" value="${order.address.address}"></td>
    </tr>
        <tr>
            <td>Status</td>
            <td><input name="status" type="text" value="${order.status}"/></td>
        </tr>
        <c:forEach items="${order.pizzas}" var="pizzas" varStatus="status">
            <tr>
                <td>${pizzas.key}</td>
                <td><input name="pizzas['${pizzas.key.pizzaId}']" value="${pizzas.value}"/></td>
            </tr>

        </c:forEach>
    </table>
    <input type="submit" value="Save"/>
</form:form>
</body>
</html>
