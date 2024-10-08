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
              <li><a class="dropdown-item" href="#" id="logoutBtn">Logout</a></li>
              <li><a class="dropdown-item" href="#">Edit</a></li>
              <li><a class="dropdown-item" href="#">MyPage</a></li>
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
            <li><a href="/user/couple/diary"><img src="${pageContext.request.contextPath}/image/common/diary.png" alt="Diary"></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/image/common/community.png" alt="Community"></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/image/common/map.png" alt="Map"></a></li>
            <li><a href="#"><img src="${pageContext.request.contextPath}/image/common/About.png" alt="About Us"></a></li>
        </ul>
    </nav>
</div>


<script src="${pageContext.request.contextPath}/js/common/layout.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>
</body>
</html>
