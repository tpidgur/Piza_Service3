<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Tetiana
  Date: 26.11.2016
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>index</title>
</head>
<body>
<div align="center">
    <h2> Welcome to the pizza menu!</h2>
    <table cellspacing="8">
        <tr>
            <td align="center"><p><a href="../pizzas">Pizzas form</a></p></td>
        </tr>
        <tr>
            <td align="center"><p><a href="../customers">Customers form</a></p></td>
        </tr>
        <tr>
            <td align="center"><p><a href="../orders">Orders form</a></p></td>
        </tr>
    </table>
</div>
<sec:csrfInput/>
</body>
</html>
