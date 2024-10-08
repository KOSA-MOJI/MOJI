<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
      rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/diary-write.css">

<security:authorize access="isAuthenticated()">
    <security:authentication property="principal" var="principal"/>
</security:authorize>

<meta name="_csrf" content="${_csrf.token}"/>

<script>
  var principalCoupleId = "${principal.couple.couple_id}";
</script>


<div id="book-container">
    <!-- Book content section -->
    <button id="saveBtn">저장</button>

    <div id="book-content" class="book-content">
        <div id="left-side" class="side">
            <img class="current-template-image"
                 src="${pageContext.request.contextPath}/image/content/diary/001.png"
                 alt="Main Image" id="left-current-template">
            <!-- Left side content will be populated by JavaScript -->
            <div class="editor-container">
                <!-- 툴바 포함 -->
                <div class="toolbar" id="toolbar">
                    <div class="toolbar d-flex justify-content-between align-items-center p-2 bg-light rounded shadow-sm">
                        <div class="toolbar-left d-flex align-items-center gap-3">
                            <!-- 날짜 선택 -->
                            <input type="date" id="dateInput" class="form-control form-control-sm">

                            <!-- 글자 크기 선택 -->
                            <select id="fontSizeSelect" class="form-select form-select-sm">
                                <option value="10">10</option>
                                <option value="12">12</option>
                                <option value="15" selected>15</option>
                                <option value="18">18</option>
                                <option value="20">20</option>
                            </select>

                            <!-- 정렬 버튼 -->
                            <button id="alignLeft" class="btn btn-light">
                                <img src="${pageContext.request.contextPath}/image/content/diary/left.png"
                                     alt="left" style="width: 25px; height: 16px;">
                            </button>
                            <button id="alignCenter" class="btn btn-light">
                                <img src="${pageContext.request.contextPath}/image/content/diary/center.png"
                                     alt="center" style="width: 25px; height: 16px;">
                            </button>
                            <button id="alignRight" class="btn btn-light">
                                <img src="${pageContext.request.contextPath}/image/content/diary/right.png"
                                     alt="right" style="width: 25px; height: 16px;">
                            </button>

                            <!-- 템플릿 선택 -->
                            <label for="template-select"></label>
                            <select id="template-select" name="template">
                                <option value="template1" data-thumbnail="template1.png">템플릿 1
                                </option>
                                <option value="template2" data-thumbnail="template2.png">템플릿 2
                                </option>
                                <option value="template3" data-thumbnail="template3.png">템플릿 3
                                </option>
                            </select>
                        </div>

                        <div class="toolbar-right">
                            <!-- 글자 색상 선택 -->
                            <input type="color" id="fontColor" value="#333333"
                                   class="form-control form-control-color">
                        </div>
                    </div>
                </div>
                <!-- 템플릿 포함-->
                <div class="template" id="template" style="display: none;">
                </div>
                <!-- 다이어리 내용 포함 -->
                <div class="editor-content" id="editorContentPage1">
                    <div class="text-content" id="textContentPage1" contenteditable="true">
                        일기 내용 입력
                    </div>
                </div>

            </div>
        </div>
        <!-- 중간의 어두운 배경 -->
        <div id="center-gap" class="center-gap"></div>

        <div id="right-side" class="side">
            <img class="current-template-image"
                 src="${pageContext.request.contextPath}/image/content/diary/001.png"
                 alt="Main Image" id="right-current-template">
            <div class="editor-container">
                <div class="mode-select-container">
                <div class="map-container">
                    <div id="map" class="map" style="width: 100%; height: 15rem;"></div>
                    <div class="mode-select-btn-container">
                        <button id="add-datespot-btn" class="tabActive">
                            장소<br>추가
                        </button>
                        <button id="add-image-btn" disabled>
                            이미지<br>추가
                        </button>
                    </div>
                </div>
            </div>

                <!-- 지도 띄워주는 곳 -->
                <div class="extra-address"></div>
                <div class="marker-list" id="markerList"></div>

                <div id="add-datespot-div" style="width: 100%;">
                    <div>
                        <input type="text" id="search-query" placeholder="데이트 코스 검색">
                        <button id="search-map-btn">장소 검색</button>
                    </div>
                    <div id="places-list" style="width: 100%"></div>
                    <div id="pagination" style="display: flex"></div>
                </div>

                <div id="add-image-div" style="display: none;">
                    <div id="selected-location-box">
                        <label id="selected_location"></label>
                        <button id="delete-loc-btn" disabled style="display: none">마커 삭제</button>
                    </div>
                    <div id="selected-image-box">
                        <input id="image-input" type="file" accept="image/*">
                    </div>
                    <div id="image-container">
                        <button id="prevImageButton" disabled>◀</button>
                        <div id="location_img_box">
                            <img src="https://placehold.co/200" id="location_img">
                            <button id="removeImageBtn">X</button>
                        </div>
                        <button id="nextImageButton" disabled>▶</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<!-- js -->
<script> const imgCommonPath = `${pageContext.request.contextPath}/image/common/`;</script>
<script src="${pageContext.request.contextPath}/js/content/diary/diary-write.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoApiKey}&libraries=services"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>