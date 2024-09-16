(function($) {

  //로그인 모달창
  $(document).ready(function() {
    var modal = $('#loginModal');
    var btn = $('#openModalBtn');
    var span = $('.close');

    btn.on('click', function() {
        modal.show();
    });

    span.on('click', function() {
        modal.hide();
    });

    $(window).on('click', function(event) {
        if ($(event.target).is(modal)) {
            modal.hide();
        }
    });
});



  //비밀번호 유효성 확인
  function validatePassword() {
    const password = document.getElementById('password').value;
    const validationMessage = document.getElementById('passwordValidation');
    
    const regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[\W_]).{8,15}$/;

    if (regex.test(password)) {
        validationMessage.style.display = 'none';
    } else {
        validationMessage.style.display = 'block';
    }
  }

  $('#password').on('input', validatePassword);


  // 회원가입 - 비밀번호 재확인 
  function checkPasswordMatch() {
    const password = $('#password').val(); // 비밀번호 필드
    const confirmPassword = $('#confirmPassword').val(); // 비밀번호 확인 필드
    const passwordHelp = $('#passwordHelp');

    if (password !== confirmPassword) {
      passwordHelp.show(); // 비밀번호 불일치 시 경고 메시지 표시
    } else {
      passwordHelp.hide(); // 비밀번호 일치 시 경고 메시지 숨김
    }
  }

  // 비밀번호 입력 시 확인
  $('#confirmPassword').on('input', checkPasswordMatch); // 두 필드에 이벤트 등록
})(jQuery);

