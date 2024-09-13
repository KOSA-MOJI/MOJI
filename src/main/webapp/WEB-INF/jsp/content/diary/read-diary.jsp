<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/read-diary.css">

<div class="book">
    <div class="editor-content-wrapper">
        <!-- First Page -->
        <label for="close-book" class="book__page book__page--1">
            <div class="editor-container">
                <!-- 툴바가 숨겨짐 -->
                <div class="editor-content" id="editorContentPage1">
                    <img class="mainImage" src="${pageContext.request.contextPath}/image/content/diary/007.png" alt="Main Image" id="mainImage">
                    <!-- contenteditable가 false로 설정되어 텍스트 수정 불가 -->
                    <div class="text-content" id="textContentPage1" contenteditable="false">저장된 일기 내용</div>

                    <!-- 수정 버튼 추가 -->
                    <button id="editBtn" class="edit-btn" style="position: absolute; top: 10px; right: 10px;">수정</button>
                </div>
            </div>
        </label>

        <!-- Second Page -->
        <label for="close-book" class="book__page book__page--2">
            <div class="date-course">
                <div class="editor-content"></div>
            </div>
        </label>
    </div>
</div>

<script>
  document.getElementById('saveBtn').addEventListener('click', function () {
    // 툴바 숨기기
    document.getElementById('toolbar').style.display = 'none';

    // 텍스트 편집 비활성화
    const textContent = document.getElementById('textContentPage1');
    textContent.contentEditable = 'false';

    // 저장 완료 메시지 표시
    alert('일기가 저장되었습니다');
  });

  // 수정 버튼 클릭 시 edit-diary.jsp로 이동
  document.getElementById('editBtn').addEventListener('click', function () {
    window.location.href = 'edit-diary.jsp';
  });
</script>
