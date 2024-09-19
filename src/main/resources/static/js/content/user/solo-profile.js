document.addEventListener('DOMContentLoaded', function () {

  const coupleInvitationModal = document.getElementById(
      "couple-invitation-modal"
  );
  const coupleInvitionBtn = document.getElementById("couple-invitation-btn");
  const cancelInvitationBtn = document.getElementById("cancel-invitation-btn");

  const checkInvitationModal = document.getElementById(
      "check-invitation-modal");
  const checkInvitationBtn = document.getElementById("check-invitation-btn");
  const cancelCheckBtn = document.getElementById("cancel-check-btn");

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

})