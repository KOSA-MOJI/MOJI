document.addEventListener('DOMContentLoaded', function () {
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
      'content');
  const pfpContainer = document.getElementsByClassName("pfp-container")[0];

  const soloProfilePicture = document.getElementById("solo-pfp");
  const pfpInput = document.getElementById("solo-pfp-file-input");
  const saveBtn = document.getElementById("save-profile-update-btn");

  pfpContainer.addEventListener("click", function () {
    pfpInput.click();
  });

  pfpInput.addEventListener("change", function (changeEvent) {
    const file = changeEvent.target.files[0];

    if (file) {
      const reader = new FileReader();
      reader.onload = function (loadEvent) {
        soloProfilePicture.src = loadEvent.target.result;
      };
      reader.readAsDataURL(file);
    }
  });

  saveBtn.onclick = function (event) {
    event.preventDefault();
    console.log("click");
    const data = {
      profileImageUrl: soloProfilePicture.src,
      email: document.getElementById("email").innerText
    }

    fetch('/api/user/solo/update-profile', {
      method: 'PUT',
      headers: {
        'X-CSRF-TOKEN': csrfToken,
        'Content-Type': 'application/json; charset=utf-8',
      },
      body: JSON.stringify(data)
    })
    .then(response => {
      console.log(response);  // 응답 전체를 콘솔에 출력해서 상태 코드와 내용을 확인
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();  // 응답이 JSON 형식인지 확인
    })
    .then(resp => {
      console.log(resp);  // 응답 데이터를 콘솔에 출력
      alert("회원수정이 완료되었습니다.");
      window.location.href = "/user/solo/";
    })
    .catch(error => {
      console.error("Error: ", error);  // 에러 메시지를 콘솔에 출력
      alert("Error: " + error.message);
    });
  }

})