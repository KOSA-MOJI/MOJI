let currentLocation = {latitude: null, longitude: null};
let currentRadius = 1500; // 기본 반경 값
const email = "wjdekqls1@example.com"
const listLimit = 5; // 한 번에 불러올 데이터 수
let communityData = []; // 받아온 데이터 저장 배열
let curDataIndex = 0; //실제 가리키는 보여줄 데이터의 시작 위치
let selectedIndex = 1; //1~limit

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
    imgTag.setAttribute("style", "width:100%;height:100%")
    imgTag.src = communityData[curDataIndex + i].imageUrl
    imageItemDiv.appendChild(imgTag)
  }
}

function updateDiaryContent(element) {
  selectedIndex = Number(element.getAttribute("id").split("-")[2])
  // console.log(`${curDataIndex+selectedIndex - 1} 번째 요소여야함`)
  // console.log(communityData)
  // console.log(communityData[curDataIndex+selectedIndex - 1])
  fetch(`/api/community/page/${communityData[curDataIndex + selectedIndex
  - 1].pageId}`).then((res) => {
    if (res.ok) {
      return res.json()
    }
    throw Error("page data not found!")
  }).then((data) => {
    console.log(data)
  }).catch(err => console.log(err))
}

function prevBtn() {
  curDataIndex = Math.max(curDataIndex - (selectedIndex - 1) - 5, 0)
  selectedIndex = 1
  updateListImage()
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
}

// 커뮤니티 하단 리스트 비동기 불러오기
function fetchCommunityData(offset, limit, isInit) {
  fetch(
      `/api/community?email=${email}&longitude=${currentLocation.longitude}&latitude=${currentLocation.latitude}&radius=${currentRadius}&offset=${offset}&limit=${limit}`)
  .then((res) => {
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
            : "https://placehold.co/600x400"
        communityData.push(singleData)
      }
    })
  }).then(() => {
    if (isInit) {
      updateListImage()
    }
  })
  .catch(err => console.log(err))
}

document.addEventListener("DOMContentLoaded", function () {
  getCurrentLocation().then(() => {
    fetchCommunityData(0, 10, true)
  })
})

function toggleFilter() {
  const modal = document.getElementById("filterModal");
  modal.style.display = modal.style.display === "block" ? "none" : "block"; // 모달을 토글
}

function showModal() {
  const modal = document.getElementById("filterModal")
  modal.style.display = "block";
}

function closeModal() {
  const modal = document.getElementById("filterModal")
  modal.style.display = "none";
}

/*필터(반경거리) range*/
function updateDistanceValues() {
  const rangeValue = document.getElementById('distanceRange').value;

  console.log("필터 값은" + rangeValue);
  currentRadius = rangeValue;
  communityData = []; //offset 초기화
  getCurrentLocation();// 슬라이더 값이 변경될 때마다 API 호출
}

/*스크랩(찜버튼)*/
let isFavorited = false; // 찜 상태를 저장할 변수

// API로부터 스크랩 상태를 가져오는 함수 (true 또는 false를 반환)
function getScrapStatusFromAPI() {
// 실제 API 호출 로직을 여기에 추가
  return true; // 예시로 false를 반환
}

//클릭시, 스크랩 이미지 업데이트1
function toggleFavorite() {
  isFavorited = !isFavorited; // 찜 상태 토글
  updateFavoriteButton(); // 이미지 업데이트
}

//클릭시, 스크랩 이미지 업데이트2
function updateFavoriteButton() {
  const img = document.getElementById('uploadButton');
  if (isFavorited) {
    img.src = `${imagePath}full-heart.png`; // 찜된 이미지

  } else {
    img.src = `${imagePath}gray-heart.png`; // 회색 이미지
  }
}

//호버시 , 스크랩 이미지 업데이트
function hoverFavorite(isHovering) {
  const img = document.getElementById('uploadButton');
  if (isHovering) {
    img.src = isFavorited
        ? `${imagePath}gray-heart.png`
        : `${imagePath}full-heart.png`; // 반대 이미지
  } else {
    updateFavoriteButton(); // 원래 상태로 되돌리기
  }
}