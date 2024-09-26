<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/diary-read.css">

  <a href="/user/couple/diary/write" class="diary-write-btn">
    <img src="${pageContext.request.contextPath}/image/content/diary/write.png" alt="Diary">
  </a>
<div id="book-container">
  <!-- Book content section -->

  <div id="book-content" class="book-content">
    <div id="left-side" class="side">
      <div id = "prevPage" onclick="prevPage()"></div>
      <!-- Left side content will be populated by JavaScript -->
    </div>
    <div id="right-side" class="side">
      </div>
      <div id="nextPage" onclick="nextPage()"></div>
    </div>
  </div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoApiKey}"></script>
<script> const imagePath = `${pageContext.request.contextPath}/image/content/diary/`;</script>
<script src="${pageContext.request.contextPath}/js/content/diary/diary-read.js"></script>