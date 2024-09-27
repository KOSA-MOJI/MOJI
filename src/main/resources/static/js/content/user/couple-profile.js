document.addEventListener('DOMContentLoaded', function () {
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
      'content');
  const breakUpAnchor = document.getElementById("break-up");
  const breakUpModal = document.getElementById("break-up-modal");
  const cancelInvitationBtn = document.getElementById("cancel-break-up-btn");
  const breakupBtn = document.getElementById("breakup-btn");
  const email = document.getElementById("email").value

  breakUpAnchor.onclick = function (event) {
    event.preventDefault();
    breakUpModal.style.display = "block";
  };

  cancelInvitationBtn.onclick = function () {
    breakUpModal.style.display = "none";
  };
  breakupBtn.onclick = function (e) {
    e.preventDefault();

    fetch(
        "/api/user/couple/breakup", {
          method: 'DELETE',
          headers: {
            'X-CSRF-TOKEN': csrfToken
          },
          body: JSON.stringify({
            receiverEmail: email
          })
        }
    ).then(response => response.json())
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
});
