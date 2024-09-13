<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/cover-diary.css">

<div class="book-cover">
    <input type="radio" id="close-book" name="page" hidden />
    <input type="radio" id="open-book" name="page" hidden />

    <div class="cover">
        <div class="book">
            <!-- Front cover of the book -->
            <label for="open-book" class="book__page book__page--cover">
                <div class="page__cover"></div>
                <h1 class="page__content-title">Foundation</h1>
                <p>Cover</p>
                <div class="page__number">1</div>
            </label>
        </div>
    </div>

    <!-- Toolbar Right -->
    <div class="toolbar-right">
        <button id="writeBtn">일기 쓰기✏️</button>
    </div>
</div>

<script>
  document.getElementById("writeBtn").addEventListener("click", function() {
    window.location.href = "write-diary.jsp";
  });
</script>