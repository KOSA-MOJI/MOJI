<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/content/community/open-diary.css">
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/content/community/diary-gallery.css">

<div class="content-diary">
    <div class="diary-component">
        <div class="diary-entry">
            <h2 id="diaryTitle">2022년 8월 소풍</h2>
            <div id="diaryContent">선택된 이미지: 없음</div> <!-- 다이어리 내용 표시 부분 -->
        </div>
    </div>

    <div class="gallery-component">
        <button class="arrow left-arrow" id="leftButton" onclick="loadImages('left')">◀</button>
        <div class="images" id="imageContainer">
            <div class="image-item" data-idx="1" onclick="updateDiaryContent(this)">1</div>
            <div class="image-item" data-idx="2" onclick="updateDiaryContent(this)">2</div>
            <div class="image-item" data-idx="3" onclick="updateDiaryContent(this)">3</div>
            <div class="image-item" data-idx="4" onclick="updateDiaryContent(this)">4</div>
            <div class="image-item" data-idx="5" onclick="updateDiaryContent(this)">5</div>
        </div>
        <button class="arrow right-arrow" id="rightButton" onclick="loadImages('right')">▶</button>
    </div>
</div>

<!-- js -->
<script src="${pageContext.request.contextPath}/js/content/community/community-diary.js"></script>