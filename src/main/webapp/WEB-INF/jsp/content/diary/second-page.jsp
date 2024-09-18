<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Second Page -->
<label for="open-book" class="book__page book__page--2">
    <!-- jsp -->
    <div id="right-component">
        <div class="mode-select-btn-container" style="width: 100%;">
            <button id="add-datespot-btn" class="tabActive">Add Date Place</button>
            <button id="add-image-btn">Add Image to Place</button>
        </div>
        <div id="map" class="map" style="width: 100%; height: 200px"></div>

        <!-- 지도 띄워주는 곳 -->
        <div class="extra-address"></div>
        <div class = "marker-list" id = "markerList"></div>

        <div id="add-datespot-div" style="width: 100%;">
            <div>
                <input type="text" id="search-query" placeholder="데이트 코스 검색">
                <button id="search-map-btn">주소 검색</button>
            </div>
            <div id="places-list" style="width: 100%"></div>
            <div id="pagination" style="display: flex"></div>
        </div>

        <div id="add-image-div" style="width: 100%;">
            <button id="result-maker">결과 생성</button>
            <div>
                <label id="selected_point"></label>
                <button id="delete-loc-btn" disabled>마커 삭제</button>
            </div>
            <div>
                <input
                        id="image-input"
                        type="file"
                        accept="image/*"
                >
            </div>
            <div id="image-container" style="border: #007bff solid 3px">
            </div>
        </div>
    </div>
</label>