<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit Order
        <%--<c:out value="${order.orderId}"/>--%>
    </title>
</head>
<body>
<form:form action="../save" method="post" modelAttribute="myorder">
    <%--<input name="orderId" type="hidden" value="${order.orderId}"/>--%>

    <table>
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
