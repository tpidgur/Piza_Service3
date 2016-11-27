<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%--
  Created by IntelliJ IDEA.
  User: Tetiana
  Date: 27.11.2016
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Map</title>
</head>
<body>
<h2>Spring 3 MVC HashMap Form</h2>
<form:form method="post" action="../savemap" modelAttribute="mymap">
<table>
    <tr>
        <th>Key</th>
        <th>Value</th>
    </tr>
    <c:forEach items="${contactForm.contactMap}" var="contactMap" varStatus="status">
        <tr>
            <td>${contactMap.key}</td>
            <td><input name="contactMap['${contactMap.key}']" value="${contactMap.value}"/></td>
        </tr>
    </c:forEach>
    <%--<c:forEach items="${pizzaMap}" var="entry" varStatus="status">--%>
        <%--<tr>--%>
            <%--<td>${entry.key.pizzaId}</td>--%>
            <%--<td><input name="entry['${entry.key.pizzaId}']" value="${entry.value}"/></td>--%>
                <%--<td>${entry.key}</td>--%>
                <%--<td><input name="entry['${entry.key}']" value="${entry.value}"/></td>--%>
        <%--</tr>--%>
    <%--</c:forEach>--%>
</table>
    <br/>

    <input type="submit" value="Save" />
</form:form>
</body>
</html>
