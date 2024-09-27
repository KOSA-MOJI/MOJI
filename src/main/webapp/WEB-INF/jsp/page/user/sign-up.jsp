<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="_csrf" content="${_csrf.token}"/>
    <title>회원가입</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/user/register-style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="container">
    <h2>회원가입</h2>
    <form action="signup-process" method="post" id="signupForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <div class="form-group">
            <label for="email">이메일</label>
            <div class="email-container">
                <input type="email" id="email" placeholder="example@naver.com" name="email">
                <button type="button" class="btn btn-success" id="check-email">이메일 중복 확인
                </button>
            </div>
            <span id="emailResult"></span>
        </div>
        <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" placeholder="********"
                   oninput="validatePassword()" name="password">
            <small id="passwordValidation" class="form-text text-danger" style="display: none;">비밀번호는
                8~15자, 영문, 숫자, 특수문자를 포함해야 합니다.</small>
        </div>
        <div class="form-group">
            <label for="confirmPassword">비밀번호 확인</label>
            <input type="password" id="confirmPassword" placeholder="********"
                   oninput="checkPasswordMatch()">
            <small id="passwordHelp" class="form-text text-danger" style="display: none;">비밀번호가 일치하지
                않습니다.</small>
        </div>

        <div class="form-group">
            <label for="username">이름</label>
            <input type="text" id="username" placeholder="홍길동" name="userName">
        </div>

        <div class="form-group">
            <label for="birthdate">생년월일</label>
            <input type="date" id="birthdate" class="date-selec" name="birthday">
        </div>


        <!-- 성별 -->
        <div class="form-group">
            <label for="genderSelect">성별</label>
            <select id="genderSelect" class="gender-select" name="gender">
                <option value="F">여자</option>
                <option value="M">남자</option>
            </select>
        </div>


        <button type="submit" class="btn btn-success">회원가입</button>
    </form>
</div>

<script src="${pageContext.request.contextPath}/js/user/setting.js"></script>
<script src="${pageContext.request.contextPath}/js/content/user/email-verification.js"></script>

</body>
</html>


