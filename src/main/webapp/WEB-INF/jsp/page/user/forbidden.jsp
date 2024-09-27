<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Access Denied</title>
    <style>
      body {
        margin: 0;
        padding: 0;
        font-family: Arial, sans-serif;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        background-color: #fffae7;
      }

      .container {
        text-align: center;
        padding: 30px;
        border-radius: 10px;
        background-color: #fff;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      }

      .btn-container {
        display: flex;
        flex-wrap: wrap;
        align-content: center;
        gap: 20%;
      }

      h1 {
        color: #333;
        margin-bottom: 20px;
      }

      p {
        color: #666;
        margin-bottom: 30px;
      }

      .btn-success {
        background-color: #ffe4f8;
        width: 156px;
        color: black;
        border: 0px;
        font-size: 1rem;
        text-align: center;
      }

      .btn-success {
        display: inline-block;
      }

      .btn-success:hover {
        background-color: #d5a3fd;
        box-shadow: 0 0 10px #cc83fb !important;
      }

      .btn-success:active,
      .btn-success:focus {
        background-color: #cc83fb;
      }

      .container {
        background-color: #fffae7; /* 폼 배경색 */
        border-radius: 8px;
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
        padding: 40px;
        width: 400px; /* 폼 너비 */
      }
    </style>
</head>
<body>
<div class="container">
    <h1>접근 권한이 없습니다</h1>
    <p>이 페이지에 접근할 수 있는 권한이 없습니다. 다른 페이지를 시도해 주세요.</p>
    <div class="btn-container">
        <button onclick="redirectToProfile()" class="btn-success">프로필로 이동</button>
        <button onclick="redirectCommunity()" class="btn-success">커뮤니티 탭으로 이동</button>
    </div>
</div>

<script>
  function redirectToProfile() {
    window.location.href = '/user/solo';
  }

  function redirectCommunity() {
    window.location.href = '/user/community';
  }
</script>
</body>
</html>