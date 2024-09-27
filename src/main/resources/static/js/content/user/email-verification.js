document.addEventListener('DOMContentLoaded', function () {
  const emailInput = document.getElementById('email');
  const checkEmailButton = document.getElementById('check-email');
  const emailResult = document.getElementById('emailResult');
  const signupForm = document.getElementById('signupForm');
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
      'content');
  checkEmailButton.addEventListener('click', function () {
    const email = emailInput.value;
    if (!email) {
      emailResult.textContent = '이메일을 입력해주세요.';
      return;
    }

    fetch('/api/check-email', {
      method: 'POST',
      headers: {
        'X-CSRF-TOKEN': csrfToken,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({email: email})
    })
    .then(response => response.json())
    .then(data => {
      if (data.available) {
        emailResult.textContent = '사용 가능한 이메일입니다.';
        emailResult.style.color = 'green';
      } else {
        emailResult.textContent = '이미 사용 중인 이메일입니다.';
        emailResult.style.color = 'red';
      }
    })
    .catch(error => {
      console.error('Error:', error);
      emailResult.textContent = '오류가 발생했습니다. 다시 시도해주세요.';
    });
  });

  signupForm.addEventListener('submit', function (event) {
    if (emailResult.textContent !== '사용 가능한 이메일입니다.') {
      event.preventDefault();
      alert('이메일 중복 확인을 해주세요.');
    }
  });
});