<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<security:authorize access="isAuthenticated()">
    <security:authentication property="principal" var="principal"/>
</security:authorize>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Moji</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/common/common.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/css/common/layout.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
          crossorigin="anonymous">
    <style>
      /* 공통 스타일 */
      body {
        font-weight: normal;
        font-style: normal;
        display: flex;
        min-height: 100vh;
        background-color: #f8e5e5;
      }

      /* 헤더 스타일 */
      .header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px;
        height: 70px;
        box-shadow: 0 4px 2px -2px gray;
        background-image: linear-gradient(to top, #fbfcdb 100%, #f4d4d4 0%);
      }

      /* 헤더 왼쪽 스타일 */
      .header-left {
        display: flex;
        align-items: center;
      }

      .header-left h1 {
        margin: 0;
        color: #2d2d2b;
        font-size: 24px;
        font-weight: bold;
        margin-left: 10px;
        letter-spacing: 2px;
      }

      .header-left img {
        width: 65px;
        height: 65px
      }

      /* 헤더 오른쪽 스타일 */
      .header-right {
        display: flex;
        align-items: flex-end;
        gap: 15px;
      }

      /* 사용자 정보 및 알림 스타일 */
      .user-info {
        display: flex;
        align-items: center;
      }

      .profile-img {
        width: 40px;
        height: 40px;
        margin-right: 10px;
      }

      .dropdown {
        display: inline-block;
        margin-bottom: -8px;
      }

      /* 드롭다운 버튼 스타일 */
      .custom-dropdown {
        background-color: transparent;
        border: none;
        padding: 0;
        box-shadow: none;
      }

      .custom-dropdown:focus {
        box-shadow: none;
      }

      /* 알림 아이콘 스타일 */
      .notification {
        cursor: pointer;
        font-size: 24px;
        color: #888;
        margin-left: 10px;
        margin-bottom: -2px;
        transition: color 0.3s ease;
      }

      .notification:hover {
        color: #d5a3fd;
      }

      /* 로그인/회원가입 스타일 */
      .auth-options {
        display: none;
        margin-right: 10px;
      }

      /* 사이드바 스타일 */
      .sidebar {
        width: 100px;
        /*background-image: linear-gradient(to top, #a18cd1 0%, #ffe4f8 100%);*/
        /*background-image: linear-gradient(to top, #d299c2 0%, #fef9d7 100%);*/
        background-image: linear-gradient(-20deg, #e9defa 0%, #fbfcdb 100%);
        display: flex;
        flex-direction: column;
        align-items: center;
        padding-top: 20px;
        position: fixed;
        top: 0;
        left: 0;
        height: 100vh;
      }

      .sidebar .profile {
        text-align: center;
        margin-bottom: 20px;
      }

      .sidebar .profile img {
        width: 70px;
        height: 70px;
        border-radius: 50%;
      }

      .sidebar nav ul {
        list-style: none;
        padding: 0;
      }

      .sidebar nav ul li {
        margin-bottom: 40px;
        display: flex;
        justify-content: center;
      }

      .sidebar nav ul li a {
        display: flex;
        justify-content: center;
        align-items: center;
        width: 60px;
        height: 60px;
        background-color: #fff;
        border-radius: 50%;
        position: relative;
        transition: background-color 0.3s ease, transform 0.3s ease;
      }

      .sidebar nav ul li a img {
        width: 30px;
        height: 30px;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        transition: transform 0.3s ease;
      }

      .sidebar nav ul li a:hover {
        background-color: #d5a3fd;
        transform: scale(1.1);
      }

      .sidebar nav ul li a:hover img {
        transform: translate(-50%, -50%) scale(1.2);
      }

      .sidebar nav ul li a.active {
        background-color: #cc83fb;
        transform: scale(1.1);
      }

      .sidebar nav ul li a.active img {
        transform: translate(-50%, -50%) scale(1.2);
      }

      /* 메인 콘텐츠 스타일 */
      .main-content {
        flex-grow: 1;
        background-color: #f8e5e5;
        margin-left: 100px;
      }

      .content {
        margin-top: 10px;
        padding: 10px;
        background-color: #f8e5e5;
        width: 100%;
        min-height: calc(100vh - 15%);
        overflow-y: auto;
      }

      /* 푸터 스타일 */
      .footer {
        bottom: 0;
        left: 0;
        width: 100%;
        text-align: center;
      }

    </style>
</head>
<body>
<input type="hidden" value="${principal.couple.couple_id}" id="log-in-couple-id"/>
<div class="main-content" id="mainContent">
    <header style="display: flex; justify-content: space-between; align-items: center;">
      <span class="header-left" style="display: flex; align-items: center;">
        <span class="menu-bar" style="margin-right: 10px;">
          <img src="${pageContext.request.contextPath}/image/common/menubar.png" class="menu-bar"
               alt="Menu Bar" width="40" height="40" onclick="toggleSidebar()">
        </span>
        <h1>Moji</h1>
      </span>
        <span class="header-right" style="display: flex; align-items: center;">
        <!-- 프로필 영역 -->
        <p class="user-info" id="userInfo" style="margin-right: 10px;">
          <img src="${principal.profileImageUrl}" class="menu-bar"
               alt="Profile Image" width="30" height="30">
          <span>${principal.userName}</span>
          <div class="dropdown">
            <button class="btn dropdown-toggle custom-dropdown" type="button" id="Btn"
                    data-bs-toggle="dropdown" aria-expanded="false"></button>
            <ul class="dropdown-menu">
            <li security:authorize="isAuthenticated()">
                <a class="dropdown-item" href="/user/logout">로그아웃</a>
            </li>
              <li><a class="dropdown-item" href="/user/solo">MyPage</a></li>
            </ul>
          </div>
            </p>

            <p class="auth-options" id="authOptions" style="display: none; margin-right: 10px;">
          <a href="#" id="signupBtn" class="auth-link">Signup</a> |
          <a href="#" id="loginBtn" class="auth-link">Login</a>
        </p>

        <p class="notification">🔔</p>
      </span>
    </header>


    <div class="content">
        <jsp:include page="${contentURL}"/>
    </div>

    <footer id="footer" class="footer">
        <div>
            <span>Copyright © 2024 <a href="" target="_blank">Moji</a>. All rights reserved.</span>
        </div>
    </footer>

</div>

<div class="sidebar" id="sidebar">
    <div class="profile">
        <img src="${principal.profileImageUrl}" alt="Profile"
             width="70" height="70">
        <h3>${principal.userName}</h3>
        <p>${principal.couple.coupleName}</p>
    </div>
    <nav>
        <ul>
            <li><a href="/user/couple/diary"><img
                    src="${pageContext.request.contextPath}/image/common/diary.png"
                    alt="Diary" width="30" height="30"><span> Diary</span></a></li>
            <li><a href="/user/community"><img
                    src="${pageContext.request.contextPath}/image/common/community.png"
                    alt="Community" width="30" height="30"><span> Community</span></a>
            </li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/image/common/map.png"
                                 alt="Map" width="30" height="30"><span> Map</span></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/image/common/About.png"
                                 alt="About Us" width="30" height="35"><span> About Us</span></a>
            </li>
        </ul>
    </nav>
</div>


<script src="${pageContext.request.contextPath}/js/common/layout.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>

<script>

  // 로그아웃 버튼 클릭 시 동작
  document.getElementById('logoutBtn').addEventListener('click', function () {
    document.getElementById('Btn').style.display = 'none';  // 드롭다운 버튼 숨기기
    document.getElementById('userInfo').style.display = 'none';  // 프로필과 이름 숨기기
    document.getElementById('authOptions').style.display = 'flex';  // 회원가입/로그인 버튼 표시
  });

  document.addEventListener("DOMContentLoaded", function () {
    const iconLinks = document.querySelectorAll('.sidebar nav ul li a');

    iconLinks.forEach(function (link) {
      link.addEventListener('click', function () {
        // 모든 아이콘에서 active 클래스 제거
        iconLinks.forEach(function (otherLink) {
          otherLink.classList.remove('active');
        });

        // 클릭된 아이콘에 active 클래스 추가
        link.classList.add('active');
      });
    });
  });
</script>
</body>
</html>
