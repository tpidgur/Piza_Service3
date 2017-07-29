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
<h1>Customer list</h1>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Address</th>
        <th>Card id</th>
        <th>Card balance</th>
        <th>Edit</th>
    </tr>
    <c:forEach items="${customerList}" var="customer">
        <tr>
            <td><c:out value="${customer.customerId}"/></td>
            <td><c:out value="${customer.name}"/></td>
            <td><c:out value="${customer.address.address}"/></td>
            <td><c:out value="${customer.card.id}"/></td>
            <td><c:out value="${customer.card.balance}"/></td>
            <td>
                <form:form action="${customer.customerId}" method="get">
                    <input hidden="true" value="${customer.customerId}">
            <input type="submit" value="edit">
            </form:form>
            </td>
        </tr>
    </c:forEach>
</table>

<form:form action="create" method="get">
    <input type="submit" value="New">
</form:form>

</body>
</html>