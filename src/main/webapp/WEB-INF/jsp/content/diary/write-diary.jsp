<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/write-diary.css">
<div class="book">
    <div class="editor-content-wrapper">
        <!-- First Page -->
        <label for="close-book" class="book__page book__page--1">
            <div class="editor-container">
                <div class="toolbar" id="toolbar">
                    <div class="toolbar-left">
                        <select id="fontSizeSelect">
                            <option value="10">10</option>
                            <option value="12">12</option>
                            <option value="15" selected>15</option>
                            <option value="18">18</option>
                            <option value="20">20</option>
                        </select>
                        <button id="alignLeft">
                            <img src="${pageContext.request.contextPath}/image/content/diary/left.png" alt="left" style="width: 25px; height: 16px;">
                        </button>
                        <button id="alignCenter">
                            <img src="${pageContext.request.contextPath}/image/content/diary/center.png" alt="center" style="width: 25px; height: 16px;">
                        </button>
                        <button id="alignRight">
                            <img src="${pageContext.request.contextPath}/image/content/diary/right.png" alt="right" style="width: 25px; height: 16px;">
                        </button>
                        <button id="templateButton">템플릿</button>
                    </div>
                    <div class="toolbar-right">
                        <input type="color" id="fontColor" value="#333333">
                        <button id="saveBtn">저장</button>
                    </div>
                </div>

                <!-- Template image list -->
                <div class="template" id="template" style="display: none;">
                    <div class="template-row">
                        <img src="${pageContext.request.contextPath}/image/content/diary/001.png" alt="Template 1" onclick="changeImage(this)">
                        <img src="${pageContext.request.contextPath}/image/content/diary/003.png" alt="Template 1" onclick="changeImage(this)">
                    </div>
                    <div class="template-row">
                        <img src="${pageContext.request.contextPath}/image/content/diary/004.png" alt="Template 1" onclick="changeImage(this)">
                        <img src="${pageContext.request.contextPath}/image/content/diary/005.png" alt="Template 2" onclick="changeImage(this)">
                    </div>
                    <div class="template-row">
                        <img src="${pageContext.request.contextPath}/image/content/diary/006.png" alt="Template 3" onclick="changeImage(this)">
                        <img src="${pageContext.request.contextPath}/image/content/diary/007.png" alt="Template 3" onclick="changeImage(this)">
                    </div>
                </div>

                <!-- First Page Content -->
                <div class="editor-content" id="editorContentPage1">
                    <img class="mainImage" src="${pageContext.request.contextPath}/image/content/diary/007.png" alt="Main Image" id="mainImage">
                    <div class="text-content" id="textContentPage1" contenteditable="true">일기 내용 입력</div>
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
  const fontSizeSelect = document.getElementById('fontSizeSelect');
  const fontColor = document.getElementById('fontColor');
  const textContent = document.getElementById('textContentPage1');
  const mainImage = document.getElementById('mainImage');
  const templateDiv = document.getElementById('template');
  const templateButton = document.getElementById('templateButton');

  // 템플릿 버튼 클릭 시 템플릿 목록 표시/숨기기
  templateButton.addEventListener('click', function() {
    templateDiv.style.display = templateDiv.style.display === 'none' ? 'block' : 'none';
  });

  // 이미지 변경 함수
  function changeImage(imgElement) {
    mainImage.src = imgElement.src;
    templateDiv.style.display = 'none';
  }

  fontSizeSelect.addEventListener('change', function () {
    textContent.style.fontSize = fontSizeSelect.value + 'px';
  });

  fontColor.addEventListener('input', function () {
    textContent.style.color = fontColor.value;
  });

  document.getElementById('alignLeft').addEventListener('click', function () {
    textContent.style.textAlign = 'left';
  });

  document.getElementById('alignCenter').addEventListener('click', function () {
    textContent.style.textAlign = 'center';
  });

  document.getElementById('alignRight').addEventListener('click', function () {
    textContent.style.textAlign = 'right';
  });

  // 저장 전
  textContent.addEventListener('focus', function () {
    if (textContent.innerHTML.trim() === '일기 내용 입력') {
      textContent.innerHTML = '';
      textContent.style.color = '#000';
    }
  });

  textContent.addEventListener('blur', function () {
    if (textContent.innerHTML.trim() === '') {
      textContent.innerHTML = '일기 내용 입력';
      textContent.style.color = '#888';
    }
  });

  document.getElementById("saveBtn").addEventListener("click", function() {
    window.location.href = "read-diary.jsp";
  });

</script>
