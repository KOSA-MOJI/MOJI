let currentLocation = {latitude: null, longitude: null};
let currentRadius = 5; // 기본 반경 값
const email = userEmail;//"hahaha123@naver.com"
const listLimit = 5; // 한 번에 불러올 데이터 수
let communityData = []; // 받아온 데이터 저장 배열
let curDataIndex = 0; //하단의 시작위치가 실제 communityDatay에서 가리키는 위치
let selectedIndex = 1; //1~limit 하단 리스트에서 선택된 항목의 인덱스
let selectedDistanceValue = 5; //현재
let curPage;

const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
    'content');

// 위치정보 가져오기
function getCurrentLocation() {
  return new Promise((resolve, reject) => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        currentLocation.latitude = position.coords.latitude;
        currentLocation.longitude = position.coords.longitude;
        console.log(
            `현재 위치: 위도 ${currentLocation.latitude}, 경도 ${currentLocation.longitude}`);
        resolve(); // 작업이 성공적으로 끝난 경우
      }, (error) => {
        console.error('위치 정보 가져오기 에러:', error);
        reject(error); // 작업이 실패한 경우
      });
    } else {
      const errorMessage = '이 브라우저는 Geolocation을 지원하지 않습니다.';
      console.error(errorMessage);
      reject(new Error(errorMessage));
    }
  });
}

// 커뮤니티 하단 바 리스트 5개씩 조회
function updateListImage() {
  for (let i = 0; i < 5; i++) {
    let imageItemDiv = document.querySelector(`#image-item-${i + 1}`)
    imageItemDiv.innerText = ""
    imageItemDiv.innerHTML = "";

    let imgTag = document.createElement("img")
    imgTag.setAttribute("style",
        "display: flex; width:100%;height:100%;z-index: 1")
    imgTag.src = communityData[curDataIndex + i].imageUrl;

    let imgScrapButton = document.createElement("img");
    imgScrapButton.src = `${imagePath}full-heart.png`;
    imgScrapButton.alt = "찜하기";
    imgScrapButton.className = "gallery-scrap-button";
    imgScrapButton.style.zIndex = 5; // z-index 설정

    // 조건에 따라 하트 이미지의 스타일 조정
    if (communityData[curDataIndex + i].scrapped) {
      imgScrapButton.style.display = "block";
    } else {
      imgScrapButton.style.display = "none";
    }

    imageItemDiv.appendChild(imgTag);
    imageItemDiv.appendChild(imgScrapButton);

    // 기존 존재하는 클릭 이벤트 제거 (중복 방지)
    imageItemDiv.onclick = null;

    if (!communityData[curDataIndex + i].pageId) {
      ////pageId가 undefined이면-> 클릭 이벤트를 막기 위해 더미 함수 사용
      imageItemDiv.onclick = function (event) {
        event.preventDefault(); // 기본 동작 막기
      };
      imageItemDiv.style.cursor = "not-allowed";
      imageItemDiv.title = "존재하지 않는 페이지 입니다.";
    } else {
      // pageId가 있을 경우 클릭 이벤트 설정
      imageItemDiv.onclick = function () {
        updateDiaryContent(this);
      };
      imageItemDiv.style.cursor = "pointer";
      imageItemDiv.removeAttribute('title');
    }
  }
  updateBtnState();
}

function updateDiaryContent(element) {
  selectedIndex = Number(element.getAttribute("id").split("-")[2])
  curPage = communityData[curDataIndex + selectedIndex - 1]

  fetch(`/api/community/page/${curPage.pageId}`, {
    headers: {
      'X-CSRF-TOKEN': csrfToken // CSRF 헤더 추가
    }
  })
  .then((res) => {
    if (res.ok) {
      return res.json()
    }
    throw Error("page data not found!")
  }).then((data) => {
    let pageData = createPagesData(data)
    updatePageContent(pageData)
  }).then(() => {
    const scrapButton = document.getElementById('scrapButton')
    scrapButton.src = communityData[curDataIndex + selectedIndex - 1].scrapped
        ? `${imagePath}full-heart.png`
        : `${imagePath}gray-heart.png`

    //페이지 클릭마다 -> 해당 페이지 스크랩 갯수 업데이트
    fetchScrapCount(communityData[curDataIndex + selectedIndex - 1].pageId)

  })
  .catch(err => console.log(err)
  )
}

//TODO : 왼쪽 오른쪽 버튼 비활성화/활성화 상태 업데이트 함수
function updateBtnState() {
  const leftButton = document.getElementById('leftButton'); // 왼쪽 버튼
  const rightButton = document.getElementById('rightButton'); // 오른쪽 버튼

  if (curDataIndex === 0) { //왼쪽 버튼 비활성화
    leftButton.disabled = true;
    leftButton.style.opacity = "0.5";
  } else {
    leftButton.disabled = false;
    leftButton.style.opacity = "1";
  }

  //더 이상 가져올,보여줄 데이터가 (x)
  if (curDataIndex + 5 >= communityData.length) {
    rightButton.disabled = true;
    rightButton.style.opacity = "0.5";
  } else {
    rightButton.disabled = false;
    rightButton.style.opacity = "1";
  }

}

function prevBtn() {
  curDataIndex = Math.max(curDataIndex - (selectedIndex - 1) - 5, 0)
  selectedIndex = 1
  updateListImage()
  updateBtnState(); //버튼 상태 업데이트
}

async function nextBtn() {
  if ((communityData.length - curDataIndex) <= 10) {
    await fetchCommunityData(communityData.length, 20, false);
    //false를 하는 이유-> 끌고 온 데이터를 바탕으로 curDataIndex를 수정 후, updateListImage()
  }

  curDataIndex = (communityData.length - 5) >= (Math.floor(curDataIndex / 5)
      + 1) * 5
      ? (Math.floor(curDataIndex / 5) + 1) * 5
      : communityData.length - 5;
  selectedIndex = 1;

  updateListImage();
  updateBtnState(); //버튼 상태 업데이트
}

// 커뮤니티 하단 리스트 비동기 불러오기
function fetchCommunityData(offset, limit, isInit) {
  fetch(
      `/api/community?email=${email}&longitude=${currentLocation.longitude}&latitude=${currentLocation.latitude}&radius=${currentRadius}&offset=${offset}&limit=${limit}`
      , {
        headers: {
          'X-CSRF-TOKEN': csrfToken // CSRF 헤더 추가
        }
      }).then((res) => {
    if (res.ok) {
      return res.json()
    }
    throw Error("data not found")
  })
  .then((data) => {
    if (data.length === 0) {
      return
    }
    let pageIds = communityData.map(elem => elem.pageId)

    data.forEach((singleData) => { //기존 communityData에 새로 온 singleData와 중복을 확인
      if (!pageIds.includes(singleData.pageId)) {
        singleData.imageUrl = singleData.imageUrl ? singleData.imageUrl
            : `${imgCommonPath}color-no-image.png`
        communityData.push(singleData)
      }
    })
    // 5개 미만일 경우
    if (communityData.length < 5) {
      const missingCount = 5 - communityData.length; // 5개까지 채우기 위해 필요한 개수
      for (let i = 0; i < missingCount; i++) {
        // 기본(대체) 이미지를 추가
        communityData.push({
          imageUrl: `${imgCommonPath}color-no-image.png` // 기본 이미지 URL
        });
      }
    }
  }).then(() => {
    if (isInit) {
      updateListImage()
    }
  }).then(() => {
    if (isInit) {
      updateDiaryContent(document.querySelector("#image-item-1"))
    }
  }).then(() => {
    if (isInit) {
      fetchScrapCount(curPage.pageId);
    }
  }).catch(err => {
    console.log(err)
    alert("5km 이내 데이트 코스가 존재하지 않습니다.")

    //반경 50km 재설정 및 호출
    currentRadius = 50;
    selectedDistanceValue = 50;

    //필터 슬라이더 및 input 값 재조정
    document.getElementById('distanceRange').value = currentRadius;
    document.getElementById('selectedDistance').value = currentRadius;

    getCurrentLocation().then(() => {
      fetchCommunityData(0, 20, true)
    });

  })
}

// 페이지 화면 구현
function createPagesData(data) {
  let leftData = {
    pageId: data.pageId,
    createdAt: data.createdAt,
    weather: data.weather,
    content: data.content,
    fontSize: data.fontSize,
    fontColor: data.fontColor,
    textAlignment: data.textAlignment,
    publicStatus: data.publicStatus,
    template: data.template
  }
  let rightData = {
    locations: data.locations
  }
  return {
    left: leftData,
    right: rightData
  }
}

function updatePageContent(data) {
  resetSideChild()
  const leftSide = document.getElementById('left-side');
  const rightSide = document.getElementById('right-side');
  leftSide.appendChild(createLeftChild(data))
  rightSide.appendChild(createRightChild(data))
}

function resetLeftSideChild() {
  const leftSide = document.getElementById('left-side');
  while (leftSide.firstChild) {
    leftSide.removeChild(leftSide.firstChild);
  }
}

function resetRightSideChild() {
  const rightSide = document.getElementById('right-side');
  while (rightSide.firstChild) {
    rightSide.removeChild(rightSide.firstChild);
  }
}

function resetSideChild() {
  resetLeftSideChild()
  resetRightSideChild()
}

// TO DO : 왼쪽 요소 css 뜯어 고치기
function createLeftChild(pageData) {
  let data = pageData.left
  let fontColor = data.fontColor;
  let fontSize = data.fontSize;
  let textAlignment = data.textAlignment;
  let templateUrl = data.template.templateImage;
  let container = document.createElement("div")
  let dateDiv = document.createElement("div")
  let weatherDiv = document.createElement("div")
  let dateWeatherDiv = document.createElement("div")
  let contentDiv = document.createElement("div")
  let templateImg = document.createElement("img")
  let topContentDiv = document.createElement("div")

  templateImg.src = templateUrl;
  // class: side 밑
  templateImg.setAttribute("style",
      "position: absolute; width: 100%; height: 100%; object-fit: cover; z-index: -1;")
  container.appendChild(templateImg)
  dateDiv.innerText = data.createdAt;
  // weatherDiv.innerText = data.weather;

  dateDiv.setAttribute("style", "position: absolute; left: 10%;")
//date css추가
  // 인라인 스타일 추가
  dateDiv.setAttribute("style", `
    font-size: 1.2rem;
    font-weight: bold;
    color: #333;
    background-color: #f0f8ff;
    padding: 0.5rem 1rem;
    border-radius: 8px;
    text-align: center;
    display: inline-block;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    margin: 1rem auto;
    letter-spacing: 0.05rem;
`);

  // container 또는 원하는 부모 요소에 추가
  container.appendChild(dateDiv);
  dateWeatherDiv.appendChild(weatherDiv)

  dateWeatherDiv.setAttribute("style",
      "display: flex; flex-direction: row; width: 100%;")
  container.appendChild(dateWeatherDiv) //(상단영역)

  //일기내용
  contentDiv.innerHTML = data.content;
  contentDiv.setAttribute("style",
      `font-size:${fontSize}px; color:${fontColor}; text-align:${textAlignment};`
      + "overflow-y: auto; max-height: 100%; padding: 10px;margin-top:-1rem; width: 100%"
  )

  topContentDiv.setAttribute("style",
      "background-color: rgba(255, 255, 255, 0.7);"
      + "display: flex;"
      + " justify-content: center;"
      + "align-items: center;"
      + " width: 80%;"
      + "height: 21rem;"
      + "margin-top: -4.5rem;"
      + "overflow: hidden;")

  topContentDiv.appendChild(contentDiv)
  container.appendChild(topContentDiv)

  //TO DO : class:"side" 바로 ㄴ div
  container.setAttribute("style",
      "width:100%; height:100%;" +
      "display: flex;" +
      "flex-direction: column;" +
      "align-items: center;" +
      "gap: 10%;")
  return container
}

function createRightChild(pageData) {
  let data = pageData.right
  let container = document.createElement("div")
  let templateImg = document.createElement("img")
  templateImg.src = pageData.left.template.templateImage;
  templateImg.setAttribute("style",
      "position: absolute; width: 100%; height: 100%; object-fit: cover; z-index: -1;")
  container.appendChild(templateImg)

  let mapContainer = document.createElement("div");
  mapContainer.id = "map";
  mapContainer.setAttribute("style",
      "width:80%; height:40%; position : relative");
  let mapOption = {
    center: new kakao.maps.LatLng(37.5665, 126.9780),
    level: 3
  };
  let map = new kakao.maps.Map(mapContainer, mapOption);
  container.appendChild(mapContainer);

  window.addEventListener('resize', () => {
    map.relayout();
  });

  let markers = [];
  let img_lists = [];
  let cur_img_list = []
  let cur_img_pointer = 0;
  let img_container = document.createElement("div")
  let img_next_btn = document.createElement("button")
  let img_prev_btn = document.createElement("button")
  let img_box = document.createElement("img")
  img_container.setAttribute("style",
      "width:90%; height:40%;" +
      "display: inline-block;" +
      "align-items: center;")

  // 만들어진 위치 마커와 이미지연결
  if (data.locations.length === 0) {
    img_lists = [[`${imageCommonPath}color-no-image.png`]]
  }

  data.locations.forEach((location, idx) => {
    let marker = new kakao.maps.Marker({
      map: map,
      position: new kakao.maps.LatLng(location.latitude, location.longitude),
    });
    img_lists.push(location.imageUrls.length > 0 ? location.imageUrls.map(
        data => data.mapImage) : [`${imgCommonPath}color-no-image.png`])
    kakao.maps.event.addListener(marker, 'click', function () {
      cur_img_list = img_lists[idx]
      cur_img_pointer = 0
      img_box.src = cur_img_list[cur_img_pointer]
      updateImageButtons(); //버튼 상태 업데이트
    })
    markers.push(marker)
  });

  // 모여진 마커들을 기반으로 맵의 바운드 설정
  if (markers.length > 0) {
    let bounds = new kakao.maps.LatLngBounds();

    // 각 마커의 위치를 bounds에 추가
    for (let i = 0; i < markers.length; i++) {
      bounds.extend(markers[i].getPosition());
    }

    // 약간의 지연을 두고 setBounds 호출
    setTimeout(() => {
      map.relayout(); // 지도 크기를 재설정
      map.setBounds(bounds); // bounds에 맞게 지도를 설정
    }, 100); // 지연 시간 100ms (필요에 따라 조정 가능)
  }

  cur_img_list = img_lists[0]
  img_box.src = cur_img_list[cur_img_pointer]

  // 이미지 이동을 처리하는 함수
  function updateImageButtons() {
    // cur_img_pointer 값에 따라 버튼 활성화/비활성화 처리
    if (cur_img_pointer >= cur_img_list.length - 1) {
      img_next_btn.disabled = true;
      img_next_btn.style.opacity = "0.5";  // 버튼 비활성화 시 시각적으로 구분하기 위해 투명도 조정
    } else {
      img_next_btn.disabled = false;
      img_next_btn.style.opacity = "1";
    }

    if (cur_img_pointer <= 0) {
      img_prev_btn.disabled = true;
      img_prev_btn.style.opacity = "0.5";
    } else {
      img_prev_btn.disabled = false;
      img_prev_btn.style.opacity = "1";
    }
  }

  img_next_btn.innerText = "▶"
  img_next_btn.addEventListener("click", function () {
    if (cur_img_pointer < cur_img_list.length - 1) {
      cur_img_pointer++;
      img_box.src = cur_img_list[cur_img_pointer]
      updateImageButtons();
    }
  })
  img_next_btn.setAttribute("style", "right:0")

  img_prev_btn.innerText = "◀"
  img_prev_btn.addEventListener("click", function () {
    if (0 < cur_img_pointer) {
      cur_img_pointer--;
      img_box.src = cur_img_list[cur_img_pointer]
      updateImageButtons();
    }
  })

  updateImageButtons();

  img_prev_btn.setAttribute("style", "left:0")
  img_next_btn.setAttribute("style",
      "right:0;border:none; outline:none;background:none");
  img_prev_btn.setAttribute("style",
      "left:0; border:none; outline:none;background:none");
  img_box.setAttribute("style",
      "width:80%;" +
      "height:100%;" +
      "vertical-align: middle;" +
      "padding:7px;" +
      "object-fit:contain;" +  // 이미지가 규격 내에 맞춰지도록 변경
      "visibility: visible;"
  );
  // img_box.setAttribute("onerror", "this.style.visibility='hidden';")

  img_container.appendChild(img_prev_btn)
  img_container.appendChild(img_box)
  img_container.appendChild(img_next_btn)
  container.appendChild(img_container)

  container.setAttribute("style",
      "width: 100%; " +
      "height: 100%;" +
      "display: flex;" +
      "flex-direction: column;" +
      "align-items: center;" +
      "gap: 10%;"
  )
  return container
}

document.addEventListener("DOMContentLoaded", function () {
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
      'content');
  getCurrentLocation().then(() => {
    fetchCommunityData(0, 10, true)
    updateBtnState()
  })
})

function toggleFilter() {
  const modal = document.getElementById("filterModal");
  if (modal.style.display === 'none') {
    modal.style.display = 'block';

    // 저장된 거리 값을 슬라이더와 input에 반영
    document.getElementById('distanceRange').value = selectedDistanceValue;
    document.getElementById('selectedDistance').value = selectedDistanceValue;
  } else {
    modal.style.display = 'none';
  }
}

function showModal() {
  const modal = document.getElementById("filterModal")
  modal.style.display = "block";
}

function closeModal() {
  const modal = document.getElementById("filterModal")
  modal.style.display = "none";
}

// 슬라이더 값이 변경될 때마다 input 업데이트
document.getElementById('distanceRange')
.addEventListener('input', function () {
  const inputRange = this.value;
  document.getElementById('selectedDistance').value = inputRange; // input에 값 반영
});

//input이 입력되었을 때마다 슬라이더 값 업데이트
document.getElementById('selectedDistance')
.addEventListener('blur', function () {
  //문자열 -> 정수처리
  let inputValue = parseInt(this.value, 10);

  if (inputValue < 5) {
    alert("검색 가능한 최소 반경은 5km 입니다.");
    inputValue = 5;
  }
  if (inputValue > 50) {
    alert("검색 가능한 최대/ 반경은 50km 입니다.");
    inputValue = 50;
  }

  this.value = inputValue;
  document.getElementById('distanceRange').value = inputValue;
});

/*필터(반경거리) range*/
function applyFilter() {
  //슬라이더->input 안의 현재 값을 저장
  selectedDistanceValue = document.getElementById('selectedDistance').value;

  //radius 지정 및 초기화
  resetDefaultData(selectedDistanceValue);

  getCurrentLocation().then(() => {
    fetchCommunityData(0, 20, true)
  });// 슬라이더 값이 변경될 때마다 API 호출

  const modal = document.getElementById('filterModal');
  modal.style.display = 'none'; // 모달을 닫음

}

//변수 초기화 함수
function resetDefaultData(radius) {
  currentRadius = radius;
  communityData = [];
  curDataIndex = 0;
  selectedIndex = 1;
}

function toggleScrap() {
  // let curPage = communityData[curDataIndex + selectedIndex - 1]
  fetch(`/api/community/scrap?email=${email}&pageId=${curPage.pageId}`, {
    method: `${curPage.scrapped ? "DELETE" : "POST"}`,
    headers: {
      'X-CSRF-TOKEN': csrfToken
    }
  }).then((res) => {
    if (res.ok) {
      return null
    }
    throw Error("cannot scrap this page!")
  }).then(() => {
    const scrapButton = document.getElementById('scrapButton')
    curPage.scrapped = !curPage.scrapped
    scrapButton.src = curPage.scrapped
        ? `${imagePath}full-heart.png`
        : `${imagePath}gray-heart.png`

    fetchScrapCount(curPage.pageId); //스크랩 갯수 업데이트
    updateListImage();

  }).catch(err => console.log(err))
}

function updateScrapCount(count) {
  const scrapCnt = document.getElementById('scrap-count');
  scrapCnt.textContent = count;
}

/*스크랩 갯수 업데이트 */
function fetchScrapCount(pageId) {
  fetch(`/api/community/scrap/${pageId}`, {
    headers: {
      'X-CSRF-TOKEN': csrfToken
    }
  }).then((res) => {
    if (res.ok) {
      return res.json()
    }
    throw Error("No Scrap!!!!")
  }).then((data) => {
    updateScrapCount(data)

  }).catch(err => console.log(err))
}