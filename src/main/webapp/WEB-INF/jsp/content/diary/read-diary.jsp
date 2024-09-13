<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/read-diary.css">

<div class="book">
    <div class="editor-content-wrapper">
        <!-- First Page -->
        <%--@declare id="open-book"--%><<label for="open-book" class="book__page book__page--1">
            <div class="editor-container">
                <!-- 툴바가 숨겨짐 -->
                <div class="editor-content" id="editorContentPage1">
                    <!-- 일기 저장 영역 포함 - 일기 왼쪽 페이지 -->
                    <%@ include file="diary-read-page.jsp" %>
                    <!-- 수정 버튼 추가 -->
                    <button id="editBtn" class="edit-btn" style="position: absolute; top: 10px; right: 10px;">수정</button>
                    <!-- 삭제 버튼 추가 -->
                    <button id="deleteBtn" class="delete-btn" style="position: absolute; top: 10px; right: 10px;">삭제</button>
                </div>
            </div>
        </label>
        <!-- 다이어리 두번째 페이지 영역 포함 - 지도, 이미지 영역 -->
        <%@ include file="second-page.jsp" %>

    </div>
</div>

<!-- js -->
<script src="${pageContext.request.contextPath}/js/content/diary/read-diary.js"></script>
