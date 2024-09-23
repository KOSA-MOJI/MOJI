<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/diary-read.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/write-diary.css">
<div id="book-container">
    <!-- Book content section -->
    <div id="book-content" class="book-content">
        <div id="left-side" class="side">
            <div class="book">
                <!-- 툴바 포함 -->
                <%@ include file="toolbar.jsp" %>
                <!-- 템플릿 포함-->
                <%@ include file="template.jsp" %>

                <!-- 다이어리 내용 포함 -->
                <div class="editor-content" id="editorContentPage1">
                    <img class="mainImage" src="${pageContext.request.contextPath}/image/content/diary/007.png" alt="Main Image" id="mainImage">
                    <%@ include file="weather-result.jsp" %>
                    <div class="text-content" id="textContentPage1" contenteditable="true">일기 내용 입력</div>
                </div>
            </div>

            <div id = "prevPage" onclick="prevPage()"></div>

        </div>
        <div id="right-side" class="side">
            <div id = "nextPage" onclick="nextPage()"></div>
            <!-- 다이어리 두번째 페이지 영역 포함 - 지도, 이미지 영역 -->
            <%@ include file="write-diary-left-component.jsp" %>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/content/diary/diary-read.js"></script>
<script src="${pageContext.request.contextPath}/js/content/diary/diary-write.js"></script>
<script src="${pageContext.request.contextPath}/js/content/diary/write-diary.js"></script>
<script src="${pageContext.request.contextPath}/js/content/diary/write-diary-left-component.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoApiKey}&libraries=services"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>