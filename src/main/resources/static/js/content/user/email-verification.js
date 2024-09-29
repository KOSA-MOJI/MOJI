document.addEventListener('DOMContentLoaded', function () {
  const emailInput = document.getElementById('email');
  const checkEmailButton = document.getElementById('check-email');
  const emailResult = document.getElementById('emailResult');
  const signupForm = document.getElementById('signupForm');
  let isEmailChecked = false; // 이메일 중복 체크 여부 플래그
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  let lastCheckedEmail = ''; // 마지막으로 중복 확인한 이메일을 저장

  // 서버 에러가 있으면 이메일 중복 확인 상태 유지
  const serverErrorMessage = document.querySelector('.error-msg');
  if (emailResult.textContent === '사용 가능한 이메일입니다.') {
    isEmailChecked = true; // 이메일 중복 체크 상태 유지
    lastCheckedEmail = emailInput.value; // 마지막 확인 이메일 저장
  }

  // 이메일 변경 시 중복 체크 상태 초기화
  emailInput.addEventListener('input', function () {
    // 이메일이 변경된 경우에만 중복 체크 상태를 초기화
    if (emailInput.value !== lastCheckedEmail) {
      isEmailChecked = false;
      emailResult.textContent = ''; // 이메일 변경 시 중복 확인 메시지 초기화
      emailResult.style.color = 'black'; // 기본 텍스트 색으로 변경
    }
  });

  // 이메일 중복 체크
  checkEmailButton.addEventListener('click', function () {
    const email = emailInput.value;
    if (!email) {
      emailResult.textContent = '이메일을 입력해주세요.';
      emailResult.style.color = 'red';
      return;
    }

    fetch('/api/check-email', {
      method: 'POST',
      headers: {
        'X-CSRF-TOKEN': csrfToken,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email: email })
    })
    .then(response => response.json())
    .then(data => {
      if (data.available) {
        emailResult.textContent = '사용 가능한 이메일입니다.';
        emailResult.style.color = 'green';
        isEmailChecked = true; // 이메일 중복 체크 완료
        lastCheckedEmail = email; // 마지막으로 확인한 이메일 저장
      } else {
        emailResult.textContent = '이미 사용 중인 이메일입니다.';
        emailResult.style.color = 'red';
        isEmailChecked = false;
      }
    })
    .catch(error => {
      console.error('Error:', error);
      emailResult.textContent = '오류가 발생했습니다. 다시 시도해주세요.';
      emailResult.style.color = 'red';
      isEmailChecked = false; // 오류 발생 시 중복 체크 실패로 처리
    });
  });

  // 폼 제출 시 이메일 중복 확인 여부 체크
  signupForm.addEventListener('submit', function (event) {
    // 중복 확인이 완료되지 않았거나, 이메일이 변경된 경우 경고창 표시
    if (!isEmailChecked || emailInput.value !== lastCheckedEmail) {
      event.preventDefault(); // 폼 제출 중단
      alert('이메일 중복 확인을 해주세요.');
    }
  });
});
