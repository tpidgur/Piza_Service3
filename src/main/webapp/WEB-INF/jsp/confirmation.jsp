<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Tetiana
  Date: 26.11.2016
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>confirmation</title>
</head>
<body>
<form:form action="./delete" method="get">
    <div align="center">
        <h1>Are you sure you want to delete the item?</h1>
        <form:form>
            <input type="radio" name="isDelete" value="true" checked>yes<br>
            <input id="id" name="pizzaId" hidden="true" value="${id}">
            <input type="radio" name="isDelete" value="false" checked>no<br>
            <td align="center"><input type="submit" value="submit"></td>
        </form:form>
    </div>
</form:form>
</body>
</html>
