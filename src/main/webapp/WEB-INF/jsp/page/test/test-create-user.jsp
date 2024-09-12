<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <h1>Create User</h1>
  <h3>Insert User Info!</h3>
  <form action="<c:url value="/test/create" />" method="post">
      <div>
          <label>ID</label>
          <input name = "id" type="text">
      </div>
      <div>
          <label>PW</label>
          <input name = "password" type="text">
      </div>
      <button type="submit">submit</button>
  </form>
</body>
</html>
