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
        background-color: #fff9db;
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

      button {
        padding: 10px 20px;
        font-size: 16px;
        color: #fff;
        background-color: #f1c40f;
        border: none;
        border-radius: 5px;
        cursor: pointer;
      }

      button:hover {
        background-color: #d4ac0d;
      }
    </style>
</head>
<body>
<div class="container">
    <h1>접근 권한이 없습니다</h1>
    <p>이 페이지에 접근할 수 있는 권한이 없습니다. 다른 페이지를 시도해 주세요.</p>
    <div class="btn-container">
        <button onclick="redirectToProfile()">프로필로 이동</button>
        <button onclick="redirectCommunity()">커뮤니티 탭으로 이동</button>
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