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

  const emailInput = document.getElementById("email-input");
  const requestUserEmail = document.getElementById("request-user-email").value;

  const requestConfirmBtn = document.getElementById("request-confirm-btn");

  const acceptInvitationBtn = document.getElementById("accept-invitation-btn");
  coupleInvitionBtn.onclick = function () {
    coupleInvitationModal.style.display = "block";
  };

  cancelInvitationBtn.onclick = function () {
    coupleInvitationModal.style.display = "none";
  };

  checkInvitationBtn.onclick = function () {
    fetch('/user/solo/api/request/check', {
      method: "GET",
      headers: {}
    })
    .then(response => response.json())
    .then(response => {
      if (response.alert) {
        alert(response.message);
      } else {
        checkInvitationModal.style.display = "block";
      }
    }).catch(error => {
      console.error("Error: ", error);
      alert("Error: " + error.message);
    })
  };

  cancelCheckBtn.onclick = function () {
    checkInvitationModal.style.display = "none";
  };

  requestConfirmBtn.onclick = function () {
    const formData = new FormData();

    formData.append('receiverEmail', emailInput.value);

    fetch('/user/solo/api/request', {
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
  };
  acceptInvitationBtn.onclick = function () {
    const formData = new FormData();
    formData.append('requestUserEmail', requestUserEmail);
    console.log(
        'Preparing to send fetch request to /user/solo/api/request/accept');
    console.log('Request User Email:', requestUserEmail);
    console.log('CSRF Token:', csrfToken);

    fetch('/user/solo/api/request/accept', {
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
      alert("Error: " + error.message + error.name + error.filename
          + error.lineNumber + error.stack);

    });

  }
})