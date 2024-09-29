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

  saveBtn.addEventListener("click", function (event) {
    event.preventDefault();
    const formData = new FormData();

    if (pfpInput.files[0]) {
      formData.append('file', pfpInput.files[0]);
    }

    formData.append('email', document.getElementById("email").innerText);

    fetch('/api/user/solo/update-profile', {
      method: 'POST',
      headers: {
        'X-CSRF-TOKEN': csrfToken
      },
      body: formData
    })
    .then(response => {
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then(resp => {
      alert("회원수정이 완료되었습니다.");
      window.location.href = "/user/solo/";
    })
    .catch(error => {
      console.error("Error: ", error);
      alert("Error: " + error.message);
    });
  });
});