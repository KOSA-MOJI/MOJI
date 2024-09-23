<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/login-style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>로그인</title>

</head>
<body>

<button id="openModalBtn">로그인</button> <!-- 로그인 버튼 추가 -->

<div id="loginModal" class="modal">
    <div class="modal-content">


        <div class="auth-form">
            <span class="close">&times;</span>
            <h1>로그인</h1>
            <form action="/login-process" method="post">
                <input name="${_csrf.parameterName}" value="${_csrf.token}">
                <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email"
                           class="form-control"
                           id="email"
                           name="email"
                           placeholder="이메일을 입력하세요"
                           required>
                </div>

                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password"
                           class="form-control"
                           id="password"
                           name="password"
                           placeholder="비밀번호를 입력하세요" required>
                </div>

                <button type="submit" class="btn btn-primary btn-block">로그인</button>

                <div class="auth-links">
                    <a href="password.jsp" class="text-primary">비밀번호 찾기</a>
                    <a href="signup" class="text-primary">회원가입</a>
                </div>
            </form>
        </div>
    </div>
</div>


<script src="${pageContext.request.contextPath}/js/user/setting.js"></script>
<script src="${pageContext.request.contextPath}/js/user/test.js"></script>

</body>
</html>
