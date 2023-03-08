<%--
  Created by IntelliJ IDEA.
  User: vantu
  Date: 3/9/2023
  Time: 2:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Detail</title>
</head>
<body>
<div>
    <img src="<%=request.getContextPath()%>/images/${pro.image}">
</div>
<div>
    <c:forEach items="${pro.listImage}" var="imageLink">
        <img src="<%=request.getContextPath()%>/images/${imageLink}">
    </c:forEach>
</div>
<h3>${pro.name}</h3>
</body>
</html>
