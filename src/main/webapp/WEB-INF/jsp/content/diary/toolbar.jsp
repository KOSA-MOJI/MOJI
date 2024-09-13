<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- toolbar.jsp -->
<div class="toolbar" id="toolbar">
    <div class="toolbar-left">
        <label for="fontSizeSelect"></label><select id="fontSizeSelect">
            <option value="10">10</option>
            <option value="12">12</option>
            <option value="15" selected>15</option>
            <option value="18">18</option>
            <option value="20">20</option>
        </select>
        <button id="alignLeft">
            <img src="${pageContext.request.contextPath}/image/content/diary/left.png" alt="left" style="width: 25px; height: 16px;">
        </button>
        <button id="alignCenter">
            <img src="${pageContext.request.contextPath}/image/content/diary/center.png" alt="center" style="width: 25px; height: 16px;">
        </button>
        <button id="alignRight">
            <img src="${pageContext.request.contextPath}/image/content/diary/right.png" alt="right" style="width: 25px; height: 16px;">
        </button>
        <button id="templateButton">템플릿</button>
    </div>
    <div class="toolbar-right">
        <label for="fontColor"></label><input type="color" id="fontColor" value="#333333">
        <button id="saveBtn">저장</button>
    </div>
</div>

