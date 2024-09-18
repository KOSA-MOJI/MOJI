document.getElementById('saveBtn').addEventListener('click', function () {
  // 툴바 숨기기
  document.getElementById('toolbar').style.display = 'none';

  // 텍스트 편집 비활성화
  const textContent = document.getElementById('textContentPage1');
  textContent.contentEditable = 'false';

  // 저장 완료 메시지 표시
  alert('일기가 저장');
});

// 수정 버튼 클릭 시 edit-diary.jsp로 이동
document.getElementById('editBtn').addEventListener('click', function () {
  window.location.href = 'edit-diary.jsp';
});