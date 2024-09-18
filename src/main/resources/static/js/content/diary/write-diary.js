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
// 이미지 변경함수
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