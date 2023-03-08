<%--
  Created by IntelliJ IDEA.
  User: vantu
  Date: 3/8/2023
  Time: 11:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>List Product</title>
</head>
<body>
<table border="1">
    <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Status</th>
            <th>Action</th>
    </thead>
    <tbody>
        <c:forEach var="product" items="${listPro}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.status?'Active':'InActive'}</td>
                <td>
                    <a href="<%=request.getContextPath()%>/ProductServlet?action=GetById&&id=${product.id}">Detail</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<a href="views/addProduct.jsp">Add Product</a>
</body>
</html>
