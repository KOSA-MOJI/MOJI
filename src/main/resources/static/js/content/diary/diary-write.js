const fontSizeSelect = document.getElementById('fontSizeSelect');
const fontColor = document.getElementById('fontColor');
const textContent = document.getElementById('textContentPage1');
const mainImage = document.getElementById('mainImage');
let curFontSize = 15
let curFontColor = "#000000"
let curTextAlignment = "center"
// const templateDiv = document.getElementById('template');
// const templateButton = document.getElementById('templateButton');
// // 템플릿 버튼 클릭 시 템플릿 목록 표시/숨기기
// templateButton.addEventListener('click', function() {
//   templateDiv.style.display = templateDiv.style.display === 'none' ? 'block' : 'none';
// });

const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
    'content');

// 이미지 변경함수
function changeImage(imgElement) {
  mainImage.src = imgElement.src;
  templateDiv.style.display = 'none';
}

function curDate() {
  const today = new Date(); // 년도
  const year = today.getFullYear(); // 월
  const month = (today.getMonth() + 1).toString().padStart(2, '0');  // 일
  const day = today.getDate().toString().padStart(2, '0');
  return `${year}-${month}-${day}`;
}

fontSizeSelect.addEventListener('change', function () {
  textContent.style.fontSize = fontSizeSelect.value + 'px';
  curFontSize=fontSizeSelect.value;
});
fontColor.addEventListener('input', function () {
  textContent.style.color = fontColor.value;
  curFontColor = fontColor.value;
});
document.getElementById('alignLeft').addEventListener('click', function () {
  textContent.style.textAlign = 'left';
  curTextAlignment = "left"
});
document.getElementById('alignCenter').addEventListener('click', function () {
  textContent.style.textAlign = 'center';
  curTextAlignment="center"
});
document.getElementById('alignRight').addEventListener('click', function () {
  textContent.style.textAlign = 'right';
  curTextAlignment="right"
});
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

document.addEventListener("DOMContentLoaded", function () {
  let curTemplateId;
  //템플릿 정보
  fetch("/user/couple/api/diary/template", {
    headers: {
      'X-CSRF-TOKEN': csrfToken
    }
  }).then((res) => {
    if (res.ok) {
      return res.json()
    }
    throw Error("template not found")
  }).then((data) => {
    let templateSelect = document.getElementById("template-select")
    templateSelect.innerHTML = ``
    for (let i = 1; i <= data.length; i++) {
      let template = data[i - 1]
      let templateOption = document.createElement("option")
      templateOption.value = template.templateId
      templateOption.setAttribute("data-thumbnail", template.templateImage)
      templateOption.setAttribute("data-templateId", template.templateId)
      templateOption.innerText = `템플릿 ${i}`
      templateSelect.appendChild(templateOption)
    }
  }).then(() => {
    const selectedOption = document.getElementById(
        "template-select").options[0];
    curTemplateId = selectedOption.getAttribute("data-templateId")
    const imageUrl = selectedOption.getAttribute('data-thumbnail');
    if (imageUrl) {
      for (let templateCover of
          document.getElementsByClassName("current-template-image")) {
        templateCover.src = imageUrl;
      }
    } else {
      throw Error("template not found")
    }
  }).catch(err => console.log(err))

  document.getElementById('template-select').addEventListener('change',
      function () {
        const selectedOption = this.options[this.selectedIndex];
        curTemplateId = selectedOption.getAttribute("data-templateId")
        console.log(curTemplateId)
        const imageUrl = selectedOption.getAttribute('data-thumbnail');
        if (imageUrl) {
          for (let templateCover of
              document.getElementsByClassName("current-template-image")) {
            templateCover.src = imageUrl;
          }
        }
      });

  console.log("loaded");
  const coupleId = principalCoupleId;

  // 날짜 정보
  let getDisabledDates = async function(coupleId) {
    try {
      let res = await fetch(`/user/couple/api/diary/date/${coupleId}`, {
        headers: {
          'X-CSRF-TOKEN': csrfToken
        }
      });
      if (!res.ok) {
        throw new Error('Failed to fetch disabled dates');
      }
      let result = await res.json();
      return result;
    } catch (err) {
      console.log(err);
      return [];  // 에러 발생 시 빈 배열 반환
    }
  };


  let dateInput = document.getElementById("dateInput");
  dateInput.max = curDate()
  dateInput.min = `1900-01-01`
  dateInput.value = curDate()

  let map = new kakao.maps.Map(document.getElementById('map'), {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3
  });

  let places = new kakao.maps.services.Places();
  let paginationListDiv = document.getElementById('pagination');
  let curDisplayMarkers = [];
  let locations = [];
  let curLocation = null;
  let curImageIndex = 0;
  let isSelectedLocation = false

  document.getElementById("add-datespot-btn").addEventListener("click",
      function () {
        document.getElementById("add-image-div").style.display = "none";
        document.getElementById("add-datespot-div").style.display = "block";
        isSelectedLocation = false
        curLocation = null;
        resetMap()
      });

  document.getElementById("add-image-btn").addEventListener("click",
      function () {
        document.getElementById("add-image-div").style.display = "block";
        document.getElementById("add-datespot-div").style.display = "none";
        isSelectedLocation = true
        curImageIndex = curLocation ? curImageIndex : 0;
        curLocation = curLocation ? curLocation : locations[0];
        updateMap(locations)
        displayImages()
      });

  // 장소 검색 후 생성되는 페이지네이션 콜백
  function paginationCallback(places, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
      updateMap(places);
      displayPlaceList(places);
      displayPagination(pagination);
    }
  }

  // 지도 내 마커 삭제 함수
  function resetMap() {
    for (let marker of curDisplayMarkers) {
      marker.setMap(null);
    }
    curDisplayMarkers = []
  }

  // 데이트 스팟일 경우 클릭하면 발생할 함수
  function markerClickEvent(location) {
    curLocation = location
    curImageIndex = 0;
    console.log(curLocation)
    console.log(location.image_urls)
    displayImages()
  }

  // 맵에 보여지는 화면 구성
  function updateMap(places) {
    resetMap()
    for (let place of places) {
      let marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y || place.latitude,
            place.x || place.longitude)
      });
      // 선택된 장소라면 마커 클릭시 이미지 이벤트 발생

      if (isSelectedLocation) {
        kakao.maps.event.addListener(marker, 'click', () => {
          markerClickEvent(place)
        })
      }
      curDisplayMarkers.push(marker)
    }
    if (curDisplayMarkers.length > 0) {
      let bounds = new kakao.maps.LatLngBounds();
      for (let marker of curDisplayMarkers) {
        bounds.extend(marker.getPosition())
      }
      setTimeout(() => {
        map.relayout(); // 지도 크기를 재설정
        map.setBounds(bounds); // bounds에 맞게 지도를 설정
      }, 100); // 지연 시간 100ms (필요에 따라 조정 가능)
    }
  }

  //각 페이지네이션 당 보여지는 위치, 선택 버튼
  function displayPlaceList(places) {
    let placeList = document.getElementById('places-list');
    placeList.innerText = '';
    for (let place of places) {
      let placeItem = document.createElement('div');
      let locInfo = document.createElement("div");
      let selectLocBtn = document.createElement("button");

      placeItem.style.display = "inline-block";
      placeItem.style.width = "100%";
      locInfo.style.float = "left";
      locInfo.style.marginLeft = "1rem"; // 여기서 margin-left 추가
      locInfo.innerText = place.place_name;
      selectLocBtn.style.float = "right";
      selectLocBtn.innerText = "️️✔️";

      // 각 버튼 클릭시 추가될 정보
      selectLocBtn.addEventListener("click", function () {
        console.log("clicked!")
        if (!locations.map(elem => elem.name).includes(place.place_name)) {
          locations.push({
            name: place.place_name,
            address: place.address_name,
            latitude: place.y,
            longitude: place.x,
            image_urls: []
          })
        }
        document.getElementById("search-query").value = ""
        document.getElementById("places-list").innerHTML = ""
        document.getElementById("pagination").innerHTML = ""
        resetMap()
        if (locations.length > 0) {
          document.getElementById(
              "add-image-btn").disabled = false
        }
        console.log(locations)
      })

      placeItem.appendChild(locInfo);
      placeItem.appendChild(selectLocBtn);
      placeList.appendChild(placeItem);
    }
  }
  // 페이지네이션 스타일 추가 함수
  // 페이지네이션 스타일 추가 함수
  function addPaginationStyles() {
    const style = document.createElement('style');
    style.innerHTML = `
    #pagination {
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 1rem;
      margin-top: -1.1rem;
    }

    #pagination .page-link {
      display: inline-block;
      margin: 0 8px; /* 버튼 간격 */
      padding: 10px 16px; /* 버튼 크기 */
      text-decoration: none;
      color: #555;
      border: 1px solid #ddd;
      border-radius: 10px; /* 둥근 버튼 모양 */
      background-color: #fafafa;
      box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
      transition: all 0.3s ease;
      font-size: 16px;
      font-weight: bold;
    }

    #pagination .page-link:hover {
      background-color: #d5a3fd; /* 버튼에 호버할 때 배경색 */
      color: white;
      border-color: #d5a3fd;
      transform: translateY(-3px); /* 호버 시 살짝 위로 올라가는 효과 */
      box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15); /* 호버 시 그림자 강조 */
    }

    #pagination .page-link.active {
      background-color: #cc83fb; /* 현재 페이지 배경색 */
      color: white;
      border-color: #cc83fb;
      pointer-events: none;
      transform: scale(1.1); /* 현재 페이지는 크기가 조금 더 크게 */
      box-shadow: 0 6px 10px rgba(0, 0, 0, 0.15); /* 활성화된 페이지 그림자 */
    }

    /* 페이지네이션 넘버 간격을 줄이기 */
    #pagination .page-link:first-child {
      margin-left: 0;
    }

    #pagination .page-link:last-child {
      margin-right: 0;
    }

    /* 페이드 인 애니메이션 추가 */
    @keyframes fadeIn {
      from {
        opacity: 0;
      }
      to {
        opacity: 1;
      }
    }

    #pagination .page-link {
      animation: fadeIn 0.5s ease-in-out;
    }
  `;
    document.head.appendChild(style);
  }

  // 장소 검색 결과 페이지네이션 화면 구성 함수
  function displayPagination(pagination) {
    addPaginationStyles();
    let paginationDiv = document.getElementById("pagination")
    paginationDiv.innerHTML = '';
    for (let i = 1; i <= pagination.last; i++) {
      let pageButton = document.createElement('a');
      pageButton.href = "#";
      pageButton.innerText = i;
      pageButton.classList.add("page-link");
      if (i === pagination.current) {
        pageButton.style.color = "purple";
      }
      (function (i) {
        pageButton.addEventListener('click', function (event) {
          event.preventDefault();
          pagination.gotoPage(i);
        });
      })(i);
      paginationDiv.appendChild(pageButton);
    }
  }
  // 장소검색 버튼 함수
  function searchLocationBtn() {
    let query = document.getElementById('search-query').value;
    if (query) {
      places.keywordSearch(query, paginationCallback, {size: 5});
    }
  }

  // 장소검색 버튼에 함수 삽입
  document.getElementById('search-map-btn').addEventListener('click',
      searchLocationBtn);

  // 선택한 장소명, 이미지 보여주기
  function displayImages() {
    let prevBtn = document.getElementById("prevImageButton");
    let nextBtn = document.getElementById("nextImageButton");
    document.getElementById("selected_location").innerText = curLocation.name
        || "데이트 장소를 먼저 선택해주세요!";

    if (!curLocation) {
      document.getElementById("image-input").disabled = true;
      prevBtn.disabled = true;
      nextBtn.disabled = true;
    } else {
      document.getElementById("image-input").disabled = false;

      // imgList가 배열인지 확인
      let imgList = Array.isArray(curLocation.image_urls)
          ? curLocation.image_urls : [];
      let locationImg = document.getElementById("location_img");

      // 이미지 스타일 설정
      locationImg.style.width = "100%";             // 너비를 80%로 설정
      locationImg.style.height = "100%";            // 높이를 80%로 설정
      locationImg.style.padding = "7px";           // 7px의 패딩 설정
      locationImg.style.objectFit = "contain";     // 이미지 비율을 유지하며 영역에 맞게 조정
      locationImg.style.visibility = "visible";    // 이미지가 보이도록 설정

      // 이미지 src 설정
      locationImg.src = imgList[curImageIndex] === undefined ? `${imgCommonPath}color-no-image.png` : imgList[curImageIndex];




      // 이미지가 없을 때 기본 이미지 표시
      document.getElementById("location_img").src = imgList[curImageIndex]
      === undefined ? `${imageCommonPath}color-no-image.png` : imgList[curImageIndex];

      // 버튼 상태 처리
      nextBtn.disabled = curImageIndex >= (imgList.length - 1) || imgList.length
          === 0;
      prevBtn.disabled = curImageIndex === 0 || imgList.length === 0;
    }
  }

  // 템플릿 선택
  // document.getElementById("templateButtonp").addEventListener("click",()=>{
  //
  // })

      // 이미지 업로드
      document.getElementById("image-input").addEventListener("change", () => {
        const fileInput = document.getElementById("image-input");
        const file = fileInput.files[0]; // 선택된 파일 가져오기

        if (file) {
          const formData = new FormData();
          formData.append("image", file); // 'image'는 서버에서 받을 파일의 키 이름

          // Fetch API를 사용해 POST 요청으로 파일을 서버로 전송
          fetch('/user/couple/api/diary/preupload', {
            method: 'POST',
            headers: {
              'X-CSRF-TOKEN': csrfToken
            },
            body: formData, // formData를 body로 전송
          })
          .then(response => response.text())
          .then(data => {
            console.log('파일 전송 성공:', data);
            curLocation.image_urls.push(data)
            curImageIndex = Math.max(curLocation.image_urls.length - 1, 0)
            displayImages()
          })
          .catch(error => {
            console.error('파일 전송 실패:', error);
          });
        } else {
          console.log('파일이 선택되지 않았습니다.');
        }
      })

    // 이미지 삭제
    document.getElementById("removeImageBtn").addEventListener("click", () => {
      if (curLocation.image_urls.length > 0) {
        let preuploadImageUrl = curLocation.image_urls[curImageIndex]
        fetch(`/user/couple/api/diary/preupload?imageUrl=${preuploadImageUrl}`, {
          method: 'DELETE',
          headers: {
            'X-CSRF-TOKEN': csrfToken // CSRF 헤더 추가
          }
        })
        .then((response) => {
          if (!response.ok) {
            throw Error();
          }
        })
        .then(() => {
          curLocation.image_urls.splice(curImageIndex, 1)
          curImageIndex = Math.max(curImageIndex -= 1, 0)
          displayImages()
        })
        .catch(error => {
          console.error('파일 전송 실패:', error);
        });
      }
    })

    function nextImageBtnOnClick() {
      curImageIndex++;
      console.log(curLocation)
      displayImages()
    }

    function prevImageBtnOnClick() {
      curImageIndex--;
      console.log(curLocation)
      displayImages()
    }

    document.getElementById("nextImageButton").addEventListener("click",
        nextImageBtnOnClick)
    document.getElementById("prevImageButton").addEventListener("click",
        prevImageBtnOnClick)

  // 다이어리 작성결과 저장
  async function saveResults() {
    let date = document.getElementById("dateInput").value;
    let content = document.getElementById("textContentPage1").innerHTML;

    // 비동기 함수 호출 시 await 사용
    let disabledDates = await getDisabledDates(coupleId);

    if (disabledDates.includes(date)) {
      alert("해당 날짜는 이미 작성된 페이지가 존재합니다!");
      return;
    }

    let locationImageMap = [];
    for (let location of locations) {
      let locationInfo = {};
      locationInfo.address = location.address;
      locationInfo.latitude = location.latitude;
      locationInfo.longitude = location.longitude;
      let imageUrlInfo = [];
      for (let imageUrl of location.image_urls) {
        imageUrlInfo.push({ mapImage: imageUrl });
      }
      locationInfo.imageUrls = imageUrlInfo;
      locationImageMap.push(locationInfo);
    }

    let result = {
      coupleId: coupleId,
      createdAt: date,
      weather: "맑음",
      content: content,
      fontSize: Number(curFontSize),
      fontColor: curFontColor,
      textAlignment: curTextAlignment,
      publicStatus: 'n',
      templateId: Number(curTemplateId),
      locations: locationImageMap
    };

    console.log(result);

    // 데이터 저장 요청
    fetch("/user/couple/api/diary/page", {
      method: "POST",
      headers: {
        'X-CSRF-TOKEN': csrfToken,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(result)
    })
      .then((res) => {
        if (res.ok) {
          alert("저장되었습니다."); // 저장 성공 메시지
          // 저장 성공 시 /user/couple/diary로 이동
          window.location.href = "/user/couple/diary";
          return;
        }
        throw new Error('Failed to save the diary page');
      })
      .catch(err => {
        console.error(err);
        alert("저장에 실패했습니다. 다시 시도해주세요.");
      });
    }
  document.getElementById("saveBtn").addEventListener("click", saveResults)
});