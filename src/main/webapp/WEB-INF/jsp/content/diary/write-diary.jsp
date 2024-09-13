<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/write-diary.css">
<div class="book">
    <div class="editor-content-wrapper">
        <!-- First Page -->
        <label for="open-book" class="book__page book__page--1">
            <div class="editor-container">
                <!-- 툴바 포함 -->
                <%@ include file="toolbar.jsp" %>

                <!-- 템플릿 포함 -->
                <%@ include file="template.jsp" %>

                <!-- 다이어리 내용 포함 -->
                <%@ include file="first-page.jsp" %>
            </div>
        </label>


            <!-- 다이어리 두번째 페이지 영역 포함 - 지도, 이미지 영역 -->
            <%@ include file="second-page.jsp" %>
    </div>
</div>

<!-- js -->
<script src="${pageContext.request.contextPath}/js/content/diary/write-diary.js"></script>