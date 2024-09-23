<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/content/diary/write-diary.css">

<div id="book-container">
    <!-- Book content section -->
    <div id="book-content" class="book-content">
        <!-- Left side -->
        <div id="left-side" class="side" style="background-image: url('${pageContext.request.contextPath}/image/content/diary/004.png')">
            <div class="book">
                <!-- Toolbar -->
                <div class="toolbar" id="toolbar">
                    <div class="toolbar-left">
                        <label for="fontSizeSelect"></label>
                        <select id="fontSizeSelect">
                            <option value="10">10</option>
                            <option value="12">12</option>
                            <option value="15" selected>15</option>
                            <option value="18">18</option>
                            <option value="20">20</option>
                        </select>

                        <label for="public-status"></label><select id="public-status">
                            <option value="y">공개</option>
                            <option value="n">비공개</option>
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
                        <label for="fontColor"></label>
                        <input type="color" id="fontColor" value="#333333">
                        <button id="saveBtn">저장</button>
                    </div>
                </div>

                <!-- 템플릿 영역 -->
                <div class="template" id="template" style="display: none;">
                    <div class="template-row">
                        <img src="${pageContext.request.contextPath}/image/content/diary/001.png" alt="Template 1" onclick="changeImage(this)">
                        <img src="${pageContext.request.contextPath}/image/content/diary/003.png" alt="Template 1" onclick="changeImage(this)">
                    </div>
                    <div class="template-row">
                        <img src="${pageContext.request.contextPath}/image/content/diary/004.png" alt="Template 1" onclick="changeImage(this)">
                        <img src="${pageContext.request.contextPath}/image/content/diary/005.png" alt="Template 2" onclick="changeImage(this)">
                    </div>
                    <div class="template-row">
                        <img src="${pageContext.request.contextPath}/image/content/diary/006.png" alt="Template 3" onclick="changeImage(this)">
                        <img src="${pageContext.request.contextPath}/image/content/diary/007.png" alt="Template 3" onclick="changeImage(this)">
                    </div>
                </div>
                <div class="weather-container">
                    <div class="weather-header">
                        <button class="weather-btn" onclick="getWeatherByCurrentLocation()">현재 위치의 날씨 조회</button>
                    </div>

                    <!-- Weather information area -->
                    <div class="weather-info" style="display: none;">
                        <div class="weather-icon"><i class="fas fa-cloud-sun" style="font-size: 50px;"></i></div>
                        <div class="weather-details">
                            <div class="weather-city">Your City</div>
                            <div class="current-temp">--º</div>
                        </div>

                    </div>
                </div>
                <!-- 다이어리 내용 -->
                <div class="editor-content" id="editorContentPage1">
                    <h4>오늘 날짜 : </h4>
                    <div class="text-content" id="textContentPage1" contenteditable="true">일기 내용 입력</div>
                </div>
            </div>
        </div>

        <!-- Previous Page Button -->
        <div id="prevPage" onclick="prevPage()"></div>

        <!-- Right side -->
        <div id="right-side" class="side">
            <div id="nextPage" onclick="nextPage()"></div>
            <!-- jsp -->
            <div id="right-component">
            <%--   지도 & 이미지 영역             --%>
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
        </div>
    </div>
</div>

<!-- JS Files -->
<script src="${pageContext.request.contextPath}/js/content/diary/write-diary-left-component.js"></script>
<script src="${pageContext.request.contextPath}/js/content/diary/diary-read.js"></script>
<script src="${pageContext.request.contextPath}/js/content/diary/write-diary.js"></script>

<!-- Kakao Maps API -->
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoApiKey}&libraries=services"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- Custom Script -->
<script>
  const fontSizeSelect = document.getElementById('fontSizeSelect');
  const fontColor = document.getElementById('fontColor');
  const textContent = document.getElementById('textContentPage1');
  const templateDiv = document.getElementById('template');
  const templateButton = document.getElementById('templateButton');

  // 템플릿 버튼 클릭 시 템플릿 목록 표시/숨기기
  templateButton.addEventListener('click', function() {
    templateDiv.style.display = templateDiv.style.display === 'none' ? 'block' : 'none';
  });

  // 이미지 변경 함수
  function changeImage(imgElement) {
    const leftSide = document.getElementById('left-side');
    leftSide.style.backgroundImage = url(${imgElement.src});
    templateDiv.style.display = 'none';
  }

  // 글자 크기 변경
  fontSizeSelect.addEventListener('change', function () {
    textContent.style.fontSize = fontSizeSelect.value + 'px';
  });

  // 글자 색상 변경
  fontColor.addEventListener('input', function () {
    textContent.style.color = fontColor.value;
  });

  // 정렬 버튼
  document.getElementById('alignLeft').addEventListener('click', function () {
    textContent.style.textAlign = 'left';
  });
  document.getElementById('alignCenter').addEventListener('click', function () {
    textContent.style.textAlign = 'center';
  });
  document.getElementById('alignRight').addEventListener('click', function () {
    textContent.style.textAlign = 'right';
  });

  // 일기 내용 기본 텍스트 처리
  textContent.addEventListener('focus', function () {
    if (textContent.innerHTML.trim() === '일기 내용 입력') {
      textContent.innerHTML = '';
      textContent.style.color = '#000';
    }
  });
  textContent.addEventListener('blur', function () {
    if (textContent.innerHTML.trim() === '') {
      textContent.innerHTML = '일기 내용 입력';
      textContent.style.color = '#888';
    }
  });

  // 저장 버튼 클릭 시
  document.getElementById("saveBtn").addEventListener("click", function() {
    window.location.href = "read-diary.jsp";
  });
</script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script defer src="https://use.fontawesome.com/releases/v5.15.2/js/all.js" integrity="sha384-vuFJ2JiSdUpXLKGK+tDteQZBqNlMwAjhZ3TvPaDfN9QmbPb7Q8qUpbSNapQev3YF" crossorigin="anonymous"></script>
<script>
  var weatherApiKey = "${weatherApiKey}";

  $(document).ready(function() {
    let weatherIcon = {
      '01': 'fas fa-sun',
      '02': 'fas fa-cloud-sun',
      '03': 'fas fa-cloud',
      '04': 'fas fa-cloud-meatball',
      '09': 'fas fa-cloud-sun-rain',
      '10': 'fas fa-cloud-showers-heavy',
      '11': 'fas fa-poo-storm',
      '13': 'far fa-snowflake',
      '50': 'fas fa-smog'
    };

    window.getWeatherByCurrentLocation = async function() {
      try {
        const coords = await getCurrentLocation();
        getWeather(coords.latitude, coords.longitude);
        $('.weather-btn').hide(); // Hide button
        $('.weather-info').show(); // Show weather info
      } catch (error) {
        console.error("위치 정보를 가져오는데 실패했습니다:", error);
        alert("위치 정보를 가져오는데 실패했습니다. 기본 위치(서울)의 날씨를 표시합니다.");
        getWeather(37.5665, 126.9780);
        $('.weather-btn').hide(); // Hide button
        $('.weather-info').show(); // Show weather info
      }
    };

    function getCurrentLocation() {
      return new Promise((resolve, reject) => {
        if ("geolocation" in navigator) {
          navigator.geolocation.getCurrentPosition(
              position => resolve(position.coords),
              error => reject(error)
          );
        } else {
          reject(new Error("Geolocation is not supported by this browser."));
        }
      });
    }

    function getWeather(lat, lon) {
      var apiURI = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + weatherApiKey + "&units=metric";
      console.log("API URI:", apiURI);

      $.ajax({
        url: apiURI,
        dataType: "json",
        type: "GET",
        success: function(data) {
          console.log("Weather data:", data);
          var iconCode = data.weather[0].icon.substr(0,2);
          var $Temp = Math.floor(data.main.temp) + 'º';
          var $city = data.name;
          var $weather_description = data.weather[0].main;

          $('.weather-icon').html('<i class="' + weatherIcon[iconCode] + '" style="font-size:100px;"></i>');
          $('.current-temp').text($Temp);
          $('.weather-city').text($city);


        },
        error:function(jqXHR,textStatus,errorThrown){
          console.error("API 요청 실패:", textStatus, errorThrown);
          alert('날씨 데이터를 가져오지 못했습니다. 오류:' + textStatus);
        }
      });
    }
  });
</script>