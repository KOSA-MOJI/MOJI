<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <form action="/test/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file">
    <button type="submit">Upload</button>
  </form>
</body>
</html>
