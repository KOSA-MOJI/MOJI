document.addEventListener('DOMContentLoaded', function () {

  const breakUpAnchor = document.getElementById("break-up");
  const changeCoupleProfileAnchor = document.getElementById(
      "change-couple-profile")
  const breakUpModal = document.getElementById("break-up-modal");
  const cancelInvitationBtn = document.getElementById("cancel-break-up-btn");

  const cameraIcons = document.getElementsByClassName("bi-camera-fill");
  const pfpContainers = document.getElementsByClassName("pfp-container");

  const profilePictures = document.getElementsByClassName("pfp");
  const pfpInputs = document.getElementsByClassName("pfp-file-input");

  breakUpAnchor.onclick = function (event) {
    event.preventDefault();
    breakUpModal.style.display = "block";
  };

  cancelInvitationBtn.onclick = function () {
    breakUpModal.style.display = "none";
  };
// change-couple-profile
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
    });
  }
})
