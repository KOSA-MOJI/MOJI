function toggleSidebar() {
    const sidebar = document.getElementById("sidebar");
    const mainContent = document.getElementById("mainContent");
    sidebar.classList.toggle("collapsed");
    mainContent.classList.toggle("shifted");
}


// 로그아웃 버튼 클릭 시 동작
document.getElementById('logoutBtn').addEventListener('click', function() {
    document.getElementById('Btn').style.display = 'none';  // 드롭다운 버튼 숨기기
    document.getElementById('userInfo').style.display = 'none';  // 프로필과 이름 숨기기
    document.getElementById('authOptions').style.display = 'flex';  // 회원가입/로그인 버튼 표시
});
