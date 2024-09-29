<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/diary-read.css">

<security:authorize access="isAuthenticated()">
    <security:authentication property="principal" var="principal"/>
</security:authorize>

<meta name="_csrf" content="${_csrf.token}"/>

<script>
  let principalCoupleId = "${principal.couple.couple_id}";
  let principalCoupleName = "${principal.couple.coupleName}";
</script>

<div id="book-container">
    <!-- Book content section -->

    <!-- Book content section -->
    <div id="book-content" class="book-content">
        <div id="left-side" class="side">
            <div id="prevPage" onclick="prevPage()"></div>
            <!-- Left side content will be populated by JavaScript -->
        </div>
        <div id="right-side" class="side">
            <div id="nextPage" onclick="nextPage()"></div>
            <!-- Right side content will be populated by JavaScript -->
        </div>
    </div>
</div>
<a href="/user/couple/diary/write" class="diary-write-btn">
    <img src="${pageContext.request.contextPath}/image/content/diary/write.png" alt="Diary">
</a>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoApiKey}"></script>
<script> const imagePath = `${pageContext.request.contextPath}/image/content/diary/`;</script>
<script src="${pageContext.request.contextPath}/js/content/diary/diary-read.js"></script>
