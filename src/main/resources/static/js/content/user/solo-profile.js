document.addEventListener('DOMContentLoaded', function () {
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
      'content');
  const coupleInvitationModal = document.getElementById(
      "couple-invitation-modal"
  );
  const coupleInvitionBtn = document.getElementById("couple-invitation-btn");
  const cancelInvitationBtn = document.getElementById("cancel-invitation-btn");

  const checkInvitationModal = document.getElementById(
      "check-invitation-modal");
  const checkInvitationBtn = document.getElementById("check-invitation-btn");
  const cancelCheckBtn = document.getElementById("cancel-check-btn");

  const emailInput = document.getElementById("email-input")

  const requestConfirmBtn = document.getElementById("request-confirm-btn")
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
  requestConfirmBtn.onclick = function () {
    const formData = new FormData();

    formData.append('receiverEmail', emailInput.value);

    fetch('/api/user/solo/request', {
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
    .then(response => {
      console.log(response);
      alert(response.message);
    })
    .catch(error => {
      console.error("Error: ", error);
      alert("Error: " + error.message);
    });
  }
})