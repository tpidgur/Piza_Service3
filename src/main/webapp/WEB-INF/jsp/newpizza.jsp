<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>

</head>
<body>
<h1>Add New Pizza</h1>
<form:form method="post" action="create">
    <table>
        <tr>
            <td>pizzaId : </td>
            <td><form:input path="pizzaId"  /></td>
        </tr>
        <tr>
            <td>name :</td>
            <td><form:input path="name" /></td>
        </tr>
        <tr>
            <td>price :</td>
            <td><form:input path="price" /></td>
        </tr>
        <tr>
            <td>price :</td>
            <td><form:input path="type" /></td>
        </tr>
        <tr>
            <td> </td>
            <td><input type="submit" value="Save" /></td>
        </tr>
    </table>
</form:form>
</body>
</html>