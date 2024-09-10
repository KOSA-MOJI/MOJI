<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <c:if test="${imageUrl != null}">
        <p>File uploaded successfully: <a href="${imageUrl}" target="_blank">${imageUrl}</a></p>
    </c:if>
    <c:if test="${message != null}">
        <p>${message}</p>
    </c:if>
</body>
</html>
