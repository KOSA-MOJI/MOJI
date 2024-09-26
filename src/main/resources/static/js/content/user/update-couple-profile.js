document.addEventListener('DOMContentLoaded', function () {
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
      'content');

  const couplePfpContainer = document.getElementById("couple-pfp-container");
  const pfpInput = document.getElementById("couple-pfp-file-input");

  const profilePicture = document.getElementById("couple-pfp");
  const saveBtn = document.getElementById("save-profile-update-btn");

  couplePfpContainer.addEventListener("click", function () {
    pfpInput.click();

    pfpInput.addEventListener("change", function (changeEvent) {
      const file = changeEvent.target.files[0];

      if (file) {
        const reader = new FileReader();
        reader.onload = function (loadEvent) {
          profilePicture.src = loadEvent.target.result;
        };
        reader.readAsDataURL(file);
      }
    })
  });

  saveBtn.addEventListener("click", function (event) {
    event.preventDefault();
    const formData = new FormData();

    if (pfpInput.files[0]) {
      formData.append('file', pfpInput.files[0]);
    }

    fetch('/api/user/couple/update-profile', {
      method: 'POST',
      headers: {
        'X-CSRF-TOKEN': csrfToken
      },
      body: formData
    })
    .then(response => {
      console.log(response);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      return response.json();
    })
    .then(resp => {
      console.log(resp);
      alert("회원수정이 완료되었습니다.");
      window.location.href = "/user/couple/";
    })
    .catch(error => {
      console.error("Error: ", error);
      alert("Error: " + error.message);
    });
  });
});