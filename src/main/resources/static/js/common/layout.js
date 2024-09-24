// 로그아웃 버튼 클릭 시 동작
document.getElementById('logoutBtn').addEventListener('click', function() {
    document.getElementById('Btn').style.display = 'none';  // 드롭다운 버튼 숨기기
    document.getElementById('userInfo').style.display = 'none';  // 프로필과 이름 숨기기
    document.getElementById('authOptions').style.display = 'flex';  // 회원가입/로그인 버튼 표시
});

document.addEventListener("DOMContentLoaded", function() {
    const iconLinks = document.querySelectorAll('.sidebar nav ul li a');

    iconLinks.forEach(function(link) {
        link.addEventListener('click', function() {
            // 모든 아이콘에서 active 클래스 제거
            iconLinks.forEach(function(otherLink) {
                otherLink.classList.remove('active');
            });

            // 클릭된 아이콘에 active 클래스 추가
            link.classList.add('active');
        });
    });
});