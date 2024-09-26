document.addEventListener('DOMContentLoaded', function () {
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
      'content');
  const pfpContainers = document.getElementsByClassName("pfp-container");
  const pfpInputs = document.getElementsByClassName("pfp-file-input");

  const profilePictures = document.getElementsByClassName("pfp");
  const saveBtn = document.getElementById("save-profile-update-btn");

  for (let i = 0; i < 3; i++) {
    pfpContainers[i].addEventListener("click", function () {
      pfpInputs[i].click();
    });

    pfpInputs[i].addEventListener("change", function (changeEvent) {
      const file = changeEvent.target.files[0];

      if (file) {
        const reader = new FileReader();
        reader.onload = function (loadEvent) {
          profilePictures[i].src = loadEvent.target.result;
        };
        reader.readAsDataURL(file);
      }

      saveBtn.addEventListener("click", function (event) {
        event.preventDefault();
        const formData = new FormData();

        if (pfpInputs[i].files[0]) {
          formData.append('file', pfpInput.files[0]);
        }
        formData.append('email', document.getElementById("email").innerText);
        console.log(formData);

        fetch('/api/user/solo/update-profile', {
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
          window.location.href = "/user/solo/";
        })
        .catch(error => {
          console.error("Error: ", error);
          alert("Error: " + error.message);
        });
      });

    });
  }

});