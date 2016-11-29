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

    <table>
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
