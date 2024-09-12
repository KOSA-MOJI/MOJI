document.addEventListener('DOMContentLoaded',function (){

  const coupleInvitationModal = document.getElementById(
      "couple-invitation-modal"
  );
  const coupleInvitionBtn = document.getElementById("couple-invitation-btn");
  const cancelInvitationBtn = document.getElementById("cancel-invitation-btn");

  const checkInvitationModal = document.getElementById("check-invitation-modal");
  const checkInvitationBtn = document.getElementById("check-invitation-btn");
  const cancelCheckBtn = document.getElementById("cancel-check-btn");

  const cameraIcon = document.getElementsByClassName("bi-camera-fill")[0];
  const pfpContainer = document.getElementsByClassName("pfp-container")[0];

  const soloProfilePicture = document.getElementById("solo-pfp");
  const pfpInput = document.getElementById("solo-pfp-file-input");

  coupleInvitionBtn.onclick = function () {
    coupleInvitationModal.style.display = "block";
  };

  cancelInvitationBtn.onclick = function () {
    coupleInvitationModal.style.display = "none";
  };

  checkInvitationBtn.onclick = function () {
    checkInvitationModal.style.display = "block";
  };

  cancelCheckBtn.onclick = function () {
    checkInvitationModal.style.display = "none";
  };

  pfpContainer.addEventListener("mouseover", function () {
    cameraIcon.classList.remove("hidden");
  });

  pfpContainer.addEventListener("mouseleave", function () {
    cameraIcon.classList.add("hidden");
  });

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
})