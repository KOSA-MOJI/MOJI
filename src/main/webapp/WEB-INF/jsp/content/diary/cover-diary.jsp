<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/cover-diary.css">

<div class="book">
    <div class="editor-content-wrapper">
        <!-- First Page -->
        <label for="close-book" class="book__page book__page--1">
            <div class="editor-container">
                <div class="editor-content" id="editorContentPage1">
                    <!-- 여기에 새로운 HTML 추가 -->
                    <div class="container">
                        <div class="diary">
                            <div class="spiral"></div> <!-- 나선형 이미지 삽입 -->
                            <h2> 커플 대표명❤️ </h2>
                            <div class="image-area" id="imageArea">
                                <img id="diaryImage" src="" alt="이곳에 이미지가 표시됩니다.">
                            </div>
                        </div>
                        <div class="upload-container">
                            <div>
                                <div class="create-diary-button">
                                    <img src="${pageContext.request.contextPath}/image/content/diary/create-diary.png"
                                         alt="새 글 작성" class="create-diary-button" id="createDiaryButton"
                                         style="width: 100px;height: 100px;"
                                    >
                                </div>
                            </div>

                            <div>
                                <div class="upload-button">
                                    <input type="file" id="imageUpload" style="display: none;" accept="image/*">
                                    <img src="${pageContext.request.contextPath}/image/content/diary/cover-upload.png"
                                         alt="이미지 선택하기" class="upload-button" id="uploadButton"
                                         style="width: 100px;height: 100px;"
                                    >
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </label>
    </div>
</div>

<script>
  const imageUpload = document.getElementById('imageUpload');
  const diaryImage = document.getElementById('diaryImage');
  const uploadButton = document.getElementById('uploadButton');

  // 이미지 선택 버튼 클릭 시 파일 선택
  uploadButton.addEventListener('click', () => {
    imageUpload.click();
  });

  imageUpload.addEventListener('change', function() {
    const file = this.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function(event) {
        diaryImage.src = event.target.result;
        diaryImage.style.display = 'block'; // 이미지 표시
      }
      reader.readAsDataURL(file);
    }
  });
</script>
