<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- 아래의 common은 반드시 붙일것! -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common/common.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/page/user/login-style.css">
    <title>메인</title>
</head>
<body>
  <button id="openModalBtn">로그인</button>
  <%@include file="/WEB-INF/component/login-modal.jsp"%>
  <script src="${pageContext.request.contextPath}/js/page/user/mainPage.js"></script>
</body>
</html>
