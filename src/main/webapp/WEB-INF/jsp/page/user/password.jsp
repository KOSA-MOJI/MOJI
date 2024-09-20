<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/password-style.css"/>
    <title>비밀번호 찾기</title>
</head>
<body>

<div class="container">
    <h1>비밀번호 찾기</h1>
    <p>가입한 이메일주소를 입력해 주세요.<br>입시 비밀번호를 이메일로 보내드리겠습니다.</p>
    <input type="email" id="email" placeholder="example@naver.com" required>
    <button id="submit">임시 비밀번호 받기</button>
</div>
<script src="${pageContext.request.contextPath}/js/user/setting.js"></script>
</body>
</html>
