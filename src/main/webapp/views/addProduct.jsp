<%@ page import="ra.ProductServlet" %><%--
  Created by IntelliJ IDEA.
  User: vantu
  Date: 3/8/2023
  Time: 11:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/ProductServlet" method="post" enctype="multipart/form-data">
    <table>
      <tr>
          <td>Name</td>
          <td><input type="text" name="name"></td>
      </tr>
        <tr>
            <td>Image</td>
            <td><input type="file" name="image"></td>
        </tr>
        <tr>
            <td>SubImage</td>
            <td><input type="file" name="subImages" multiple></td>
        </tr>
      <tr>
        <td>Status</td>
        <td><input type="text" name="status" multiple></td>
      </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Create" name="action"></td>
        </tr>
    </table>
</form>
</body>
</html>
