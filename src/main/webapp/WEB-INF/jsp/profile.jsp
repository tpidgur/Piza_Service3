<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>

</head>
<body>
    <h1>Customer Profile</h1>
    <div>
        <div>CustomerId:<c:out value="${customer.customerId}"/></div>
    </div>
    <div>
        <div>Name:<c:out value="${customer.name}"/></div>
    </div>
    <div>
        <div>Address:<c:out value="${customer.address.address}"/></div>
    </div>
    <div>
        <div>CardId:<c:out value="${customer.card.id}"/></div>
    </div>
    <div>
        <div>Card balance:<c:out value="${customer.card.balance}"/></div>
    </div>
</body>
</html>