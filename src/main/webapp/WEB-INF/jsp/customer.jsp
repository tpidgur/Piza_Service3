<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>

</head>
<body>
<h1>Edit Customer</h1>
<form action="../save" method="post">
    <input name="customerId" type="hidden" value="${customer.customerId}"/>
    <%--<input  name="card" type="hidden"  value="${customer.card}"/>--%>

    <%--<input  name="id" type="hidden"  value="${customer.address.id}"/>--%>
    <table>
        <tr>
            <td>Customer name</td>
            <td><input name="name" type="text" value="${customer.name}"/><br/></td>
        </tr>
        <tr>
            <td>Address</td>
            <td><form:hidden path="customer.address.id"/>
                <input name="address" type="text" value="${customer.address.address}"/></td>
        </tr>
        <tr>
            <td>Card balance</td>
            <td><form:hidden path="customer.card.id"/>
                <input name="balance" type="text" value="${customer.card.balance}"/><br/></td>
        </tr>
    </table>
    <input type="submit" value="Save"/>
</form>
</body>
</html>