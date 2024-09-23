window.onload = function () {
  getCurrentLocation(); // 현재 위치 가져오기
  isFavorited = getScrapStatusFromAPI(); // API에서 상태 가져오기
  updateFavoriteButton(); // 초기 상태에 따라 이미지 설정
//loadImages('left'); // 초기 상태에서 1부터 5까지 보여주도록 왼쪽으로 이동
};

let currentLocation = {latitude: null, longitude: null};
let currentRadius = 1500; // 기본 반경 값
let currentImageIndex = 0;
const limit = 5; // 한 번에 불러올 데이터 수
let communityData = []; // 받아온 데이터 저장 배열
let lastDirection = 'left'; // 마지막으로 눌린 방향 저장
let previousImageIndex = 0; // 이전 인덱스 저장
let isFirstLoad = false; // 첫 번째 로드 여부 체크

//TO DO: 현재 내 위치 받아오기
// _ 공개 페이지 목록 조회 API연동
function getCurrentLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition((position) => {
      currentLocation.latitude = position.coords.latitude;
      currentLocation.longitude = position.coords.longitude;

      // 현재 위치를 콘솔에 출력
      console.log(
          `현재 위치: 위도 ${currentLocation.latitude}, 경도 ${currentLocation.longitude}`);
      fetchCommunityData(currentRadius, 20); //초기 api 호출

    }, (error) => {
      console.error('위치 정보 가져오기 에러:', error);
    });
  } else {
    console.error('이 브라우저는 Geolocation을 지원하지 않습니다.');
  }
}

/*TODO : 현재 radius를 받아와 -> 공개 페이지 목록조회 API*/
function fetchCommunityData(radius, limit) {
  const email = "wjdekqls1@example.com"; // 예시 이메일-> 세션으로해야함
  const offset = communityData.length;// 현재 데이터 길이로 오프셋 설정
  const apiUrl = `/api/community?email=${email}&longitude=${currentLocation.longitude}&latitude=${currentLocation.latitude}&radius=${radius}&offset=${offset}&limit=${limit}`;

  fetch(apiUrl)
  .then(response => {
    if (!response.ok) {
      loadImages()
      throw new Error('Network response was not ok');
    }
    return response.json();
  })
  .then(data => {
    console.log("[비동기]API로부터 받은 데이터: ", data);
    addCommunityData(data); // 데이터를 추가하는 함수 호출
    loadImages('left');
  })

  .catch(error => console.error('Error fetching data:', error));
}

function addCommunityData(newData) {
  newData.forEach(item => {
    // 중복된 pageId가 있는지 확인
    if (!communityData.some(
        existingItem => existingItem.pageId === item.pageId)) {
      communityData.push(item); // 중복이 없으면 추가
    }
  });
}

/*이미지 갤러리에 삽입*/

function loadImages(direction) {
  const imageContainer = document.getElementById('imageContainer');
  const leftButton = document.getElementById('leftButton');
  const rightButton = document.getElementById('rightButton');

  console.log("loadImages", direction)

  if (direction === 'right') {
    currentImageIndex += limit; // 오른쪽 화살표 클릭

  } else if (direction === 'left') {
    currentImageIndex -= limit; // 왼쪽 화살표 클릭
  }

  console.log("현재 인덱스" + currentImageIndex, limit);
// 버튼 방향에 따른 현재 0번 이미지 위치에 놓인 -> 실제 이미지 idx
  currentImageIndex = Math.max(0,
      Math.min(currentImageIndex, communityData.length - limit));
  console.log("이거로 인덱스" + currentImageIndex);

  // 첫 번째 호출일 때만 이전 인덱스 저장
  if (isFirstLoad && (direction === 'right' || direction === 'left')) {
    previousImageIndex = currentImageIndex;
    console.log("1회차")
    isFirstLoad = false;
  } else {
    isFirstLoad = true; // 첫 번째 로드 완료
  }
// 이미지 항목 초기화
  imageContainer.innerHTML = '';

  let needsMoreData = false; // 추가 데이터 필요 여부

// 시작 위치, 몇 개 받아올지
  for (let i = 0; i < limit; i++) {
    if (previousImageIndex + i < communityData.length) {
      const imageItem = document.createElement('div');
      imageItem.className = 'image-item';
      imageItem.setAttribute('data-idx',
          communityData[previousImageIndex + i].pageId); // 페이지 ID 설정

      // 배경 이미지 설정
      imageItem.style.backgroundImage = `url(${communityData[previousImageIndex
      + i].imageUrl})`;
      imageItem.style.backgroundSize = 'cover'; // 배경 이미지 크기 조정
      imageItem.style.backgroundPosition = 'center'; // 중앙 정렬

      imageItem.onclick = () => updateDiaryContent(imageItem); // 클릭 이벤트 설정
      imageContainer.appendChild(imageItem);

    } else {
      // 데이터가 부족할 경우 플래그 설정
      needsMoreData = true;
    }
  }

  // 추가 데이터 요청
  if (needsMoreData) {
    console.log("추가로 10개 주시오", previousImageIndex);
    fetchCommunityData(currentRadius, 10); // 추가로 10개 요청
  }

// 버튼 활성화/비활성화 설정
  leftButton.classList.toggle('disabled', currentImageIndex === 0);
  rightButton.classList.toggle('disabled',
      currentImageIndex >= communityData.length - limit);
}

// 1. 오른쪽 버튼 클릭 이벤트 리스너 추가
const rightButton = document.getElementById('rightButton');
rightButton.addEventListener('click', () => {
  lastDirection = 'right'; // 마지막으로 눌린 방향 저장
  console.log("오른쪽 클릭", currentImageIndex);
  loadImages('right');

});

// 왼쪽 버튼 클릭 이벤트 리스너 추가
const leftButton = document.getElementById('leftButton');
leftButton.addEventListener('click', () => {
  lastDirection = 'left'; // 왼쪽 버튼 클릭 시 방향 저장
  console.log("왼쪽 클릭", currentImageIndex);
  loadImages('left'); // 왼쪽 버튼 클릭 시 이미지 로드
});

// 선택된 이미지 번호 표시
function updateDiaryContent(element) {
  const imageNumber = element.getAttribute('data-idx'); // 클릭한 아이템의 고유 값 가져오기
  const diaryContent = document.getElementById('diaryContent');
  diaryContent.innerText = '선택된 이미지: ' + imageNumber;
}

/*이미지 갤러리.js*/
// let currentImageIndex = 0;
// const images = Array.from({length: 13}, (_, i) => i + 1); // 1부터 13까지의 배열
//
// function loadImages(direction) {
//   const imageContainer = document.getElementById('imageContainer');
//   const imageItems = imageContainer.getElementsByClassName('image-item');
//   const leftButton = document.getElementById('leftButton');
//   const rightButton = document.getElementById('rightButton');
//
//   console.log(imageItems);
//
//   if (direction === 'right') {
//     currentImageIndex += 5; // 오른쪽 화살표 클릭
//   } else {
//     currentImageIndex -= 5; // 왼쪽 화살표 클릭
//   }
//
//   // 버튼 방향에 따른 현재 0번 이미지 위치에 놓인 -> 실제 이미지 idx
//   currentImageIndex = Math.max(0,
//       Math.min(currentImageIndex, images.length - 5));
//
//   //시작 위치, 몇개 받아올지
//   for (let i = 0; i < imageItems.length; i++) {
//     const imageNumber = images[currentImageIndex + i]; //해당 위치에 오는 이미지 idx
//     // console.log("imageNumber" + imageNumber)
//     imageItems[i].innerText = imageNumber; // 배열의 값을 div에 표시
//     // console.log(i + " ImageItems" + imageItems[i])
//     imageItems[i].setAttribute('data-idx', imageNumber); // 고유 값 설정
//   }
//
//   // 버튼 활성화/비활성화 설정
//   leftButton.classList.toggle('disabled', currentImageIndex === 0);
//   rightButton.classList.toggle('disabled',
//       currentImageIndex >= images.length - 5);
// }
//
//To DO : 페이지에 선택된 번호 표시
function updateDiaryContent(element) {
  const imageNumber = element.getAttribute('data-idx'); // 클릭한 아이템의 고유 값 가져오기
  const diaryContent = document.getElementById('diaryContent');
  diaryContent.innerText = '선택된 이미지: ' + imageNumber;
}

//
// // 초기 이미지 로드
// currentImageIndex = 0; // 초기 인덱스를 0으로 설정하여 1부터 시작하도록
// loadImages('left'); // 초기 상태에서 1부터 5까지 보여주도록 왼쪽으로 이동

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

/*필터 클릭시 - 슬라이더 표시*/

function toggleFilter() {
  const filterContainer = document.getElementById('filterContainer');
  filterContainer.style.display = filterContainer.style.display === 'none'
  || filterContainer.style.display === '' ? 'flex' : 'none';
}

/*필터(반경거리) range*/
function updateDistanceValues() {
  const rangeValue = document.getElementById('distanceRange').value;

  console.log("필터 값은" + rangeValue);
  currentRadius = rangeValue;
  getCurrentLocation();// 슬라이더 값이 변경될 때마다 API 호출
}

function fetchFilteredData(minDistance) {
  const maxDistance = 50; // 최대값 고정

  const apiUrl = `${pageContext.request.contextPath}/api/filter?minDistance=${minDistance}&maxDistance=${maxDistance}`; // API URL 수정

  fetch(apiUrl)
  .then(response => response.json())
  .then(data => {
    console.log(data); // API로부터 받은 데이터 처리
// 필요에 따라 데이터 처리 로직 추가
  })
  .catch(error => console.error('Error fetching data:', error));
}