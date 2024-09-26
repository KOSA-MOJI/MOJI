<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/content/community/open-diary.css">
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/css/component/login-modal.css">


<security:authorize access="isAuthenticated()">
    <security:authentication property="principal" var="principal"/>
</security:authorize>

<meta name="_csrf" content="${_csrf.token}"/>

<%--이메일 js에 보내주기--%>
<script>
  var userEmail = "<%= request.getUserPrincipal().getName()%>";
  console.log(userEmail);
</script>

<div class="community-content">
    <div class="filter-div" onclick="showModal()">
        <img src="${pageContext.request.contextPath}/image/content/community/filter-icon.png"
             alt="필터 아이콘"
             class="filter-icon">
        <span>필터</span>
    </div>
    <div id="filterModal" style="display: none">
        <div class="modal-content" style="z-index: 100">
            <div class="modal-header">
                <p class="header-title">필터 </p>
                <span id="closeModal" class="close" onclick="toggleFilter()">&times;</span>
            </div>
            <div class="filter-modal-frame">
                <div class="filter-modal-hd">
                    <div class="filter-modal-title">거리 범위</div>
                    <%-- 버튼--%>
                    <div class="filter-radius">
                        <label>적용된 거리</label>
                        <input id="selectedDistance" value="5">
                    </div>
                </div>
                <%-- 거리 슬라이더 --%>
                <div class="slider-container" id="filterContainer">
                    <input type="range" id="distanceRange" min="5" max="50" value="5" step="5"
                    >
                    <div class="km-labels">
                        <span>5km</span>
                        <span>25km</span>
                        <span>50km</span>
                    </div>
                </div>
                <button class="btn-base btn-primary btn-block " style="margin-top : 35px"
                        onclick="applyFilter()">적용
                </button>
            </div>
        </div>
        <div class="modal" onclick="closeModal()"></div>
    </div>


    <div class="content-middle">
        <div class="content-diary">
            <div id="book-container">
                <div id="book-content" class="book-content">
                    <div id="left-side" class="side">
                        <!-- Left side content will be populated by JavaScript -->
                    </div>
                    <div id="right-side" class="side">
                        <!-- Right side content will be populated by JavaScript -->
                    </div>
                </div>
            </div>
            <div class="scrap-component" id="favoriteButton" onclick="toggleScrap()">
                <div id="scrap-count">
                    갯수
                </div>

                <div class="circle-background">
                    <img src="${pageContext.request.contextPath}/image/content/community/gray-heart.png"
                         alt="찜하기" class="scrap-button" id="scrapButton">
                </div>
            </div>
        </div>
    </div>

    <div class="gallery-component">
        <img src=""/>
        <button class="arrow left-arrow" id="leftButton" onclick="prevBtn()">◀</button>
        <div class="images" id="imageContainer">
            <div class="image-item" id="image-item-1" onclick="updateDiaryContent(this)">1</div>
            <div class="image-item" id="image-item-2" onclick="updateDiaryContent(this)">2</div>
            <div class="image-item" id="image-item-3" onclick="updateDiaryContent(this)">3</div>
            <div class="image-item" id="image-item-4" onclick="updateDiaryContent(this)">4</div>
            <div class="image-item" id="image-item-5" onclick="updateDiaryContent(this)">5</div>
        </div>
        <button class="arrow right-arrow" id="rightButton" onclick="nextBtn()">▶</button>
    </div>
</div>
<%--</div>--%>

<!-- js -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoApiKey}"></script>
<script>
  const imagePath = "${pageContext.request.contextPath}/image/content/community/";
</script>
<script>
  const imgCommonPath = "${pageContext.request.contextPath}/image/common/";
</script>
<script src="${pageContext.request.contextPath}/js/content/community/community-diary.js"></script>