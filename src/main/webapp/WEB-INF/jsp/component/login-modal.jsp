<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/css/component/login-modal.css">
<div id="loginModal" class="modal">
    <div class="modal-content">
        <div class="auth-form">
            <span id="closeModal" class="close">&times;</span>
            <h1>로그인</h1>
            <form>
                <div class="form-group">
                    <label for="email">이메일</label>
                    <input type="email" class="form-control" id="email" placeholder="이메일을 입력하세요"
                           required>
                </div>

                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" class="form-control" id="password"
                           placeholder="비밀번호를 입력하세요" required>
                </div>

                <button type="submit" class="btn-base btn-primary btn-block">로그인</button>

                <div class="auth-links">
                    <a href="password.html" class="text-primary">비밀번호 찾기</a>
                    <a href="register.html" class="text-primary">회원가입</a>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/component/login-modal.js"></script>
