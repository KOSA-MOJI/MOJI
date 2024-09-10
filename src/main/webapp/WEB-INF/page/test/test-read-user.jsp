<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <title>Title</title>
</head>
<body>
<h1>This is JSP / SPRING BOOT TestPage!</h1>
<h2>We can connect to Oracle DB with Mybatis!</h2>
<h3>Test IDs..</h3>
<c:forEach var="test" items="${tests}">
  <h4>${test.id}</h4>
</c:forEach>
</body>
</html>
