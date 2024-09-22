<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/diary-read.css">
<div id="book-container">
  <!-- Book content section -->
  <div id="book-content" class="book-content">
    <div id="left-side" class="side">
      <div id = "prevPage" onclick="prevPage()"></div>
      <!-- Left side content will be populated by JavaScript -->
    </div>
    <div id="right-side" class="side">
      <div id = "nextPage" onclick="nextPage()"></div>
      <!-- Right side content will be populated by JavaScript -->
    </div>
  </div>
</div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoApiKey}"></script>
<script src="${pageContext.request.contextPath}/js/content/diary/diary-read.js"></script>
