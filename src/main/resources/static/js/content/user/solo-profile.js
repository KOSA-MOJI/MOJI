document.addEventListener('DOMContentLoaded', function () {
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
      'content');

  const coupleRequestModal = document.getElementById(
      "couple-request-modal"
  );
  const checkRequestModal = document.getElementById(
      "check-request-modal");

  const coupleRequestBtn = document.getElementById("couple-request-btn");
  const checkRequestBtn = document.getElementById("check-request-btn");
  const sendRequestBtn = document.getElementById("send-request-btn");
  const acceptRequestBtn = document.getElementById("accept-request-btn");
  const denyRequestBtn = document.getElementById("deny-request-btn");
  const cancelModalBtn = document.getElementById("cancel-modal-btn"); // 커플신청 모달창 닫기
  const cancelCheckBtn = document.getElementById("cancel-check-btn"); // 커플신청확인 모
  const cancelRequestBtn = document.getElementById("cancel-request-btn");

  const emailInput = document.getElementById("email-input");
  const requestUserEmail = document.getElementById("request-user-email").value;

  // 커플 신청 모달창 토글하기
  coupleRequestBtn.onclick = function () {
    coupleRequestModal.style.display = "block";
  };

  cancelModalBtn.onclick = function () {
    coupleRequestModal.style.display = "none";
  };

  // 커플 신청 확인 모달창 토글 및 확인하기
  checkRequestBtn.onclick = function () {
    fetch('/user/solo/api/request/check', {
      method: "GET",
      headers: {}
    })
    .then(response => response.json())
    .then(response => {
      if (response.alert) {
        alert(response.message);
      } else {
        checkRequestModal.style.display = "block";
      }
    }).catch(error => {
      console.error("Error: ", error);
      alert("Error: " + error.message);
    })
  };

  // 커플신청확인 모달창 숨기기
  cancelCheckBtn.onclick = function () {
    checkRequestModal.style.display = "none";
  };

  sendRequestBtn.onclick = function () {
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

  acceptRequestBtn.onclick = function () {
    const formData = new FormData();
    formData.append('requestUserEmail', requestUserEmail);
    console.log(
        'Preparing to send fetch request to /user/solo/api/request/accept');
    console.log('Request User Email:', requestUserEmail);
    console.log('CSRF Token:', csrfToken);

    fetch('http://localhost:8090/user/solo/api/request/accept', {
      method: 'POST',
      headers: {
        'X-CSRF-TOKEN': csrfToken
      },
      body: formData
    })

    .then(response => {
      console.log(response);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`).stack;
      }
      return response.json();
    })

    .then(response => {
      console.log(response);
      alert(response.message);
    })

    .catch(error => {
      console.error("Error: ", error);
      alert("Error: " + error.name + ' ' + error.message + ' ' + error.stack);

    });

  };

  denyRequestBtn.onclick = function () {

    fetch('http://localhost:8090/user/solo/api/request/deny', {
      method: 'DELETE',
      headers: {
        'X-CSRF-TOKEN': csrfToken
      }
    })

    .then(response => {
      console.log(response);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`).stack;
      }
      return response.json();
    })

    .then(response => {
      console.log(response);
      alert(response.message);
    })

    .catch(error => {
      console.error("Error: ", error);
      alert("Error: " + error.name + ' ' + error.message + ' ' + error.stack);

    });
  }

  cancelRequestBtn.onclick = function () {
    fetch('http://localhost:8090/user/solo/api/request/cancel', {
      method: 'DELETE',
      headers: {
        'X-CSRF-TOKEN': csrfToken
      }
    })

    .then(response => {
      console.log(response);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`).stack;
      }
      return response.json();
    })

    .then(response => {
      console.log(response);
      alert(response.message);
    })

    .catch(error => {
      console.error("Error: ", error);
      alert("Error: " + error.name + ' ' + error.message + ' ' + error.stack);

    });
  }
})