window.onload = function () {
  getCurrentLocation(); // 현재 위치 가져오기
  isFavorited = getScrapStatusFromAPI(); // API에서 상태 가져오기
  updateFavoriteButton(); // 초기 상태에 따라 이미지 설정
//loadImages('left'); // 초기 상태에서 1부터 5까지 보여주도록 왼쪽으로 이동
};

//TO DO: 현재 내 위치 받아오기
function getCurrentLocation() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition((position) => {
      const latitude = position.coords.latitude;
      const longitude = position.coords.longitude;

      // 현재 위치를 콘솔에 출력
      console.log(`현재 위치: 위도 ${latitude}, 경도 ${longitude}`);

      // 필요에 따라 여기서 추가 작업 수행 가능
      // 예: KakaoMap에 현재 위치 표시 등

    }, (error) => {
      console.error('위치 정보 가져오기 에러:', error);
    });
  } else {
    console.error('이 브라우저는 Geolocation을 지원하지 않습니다.');
  }
}

let currentImageIndex = 0;
const images = Array.from({length: 13}, (_, i) => i + 1); // 1부터 13까지의 배열

function loadImages(direction) {
  const imageContainer = document.getElementById('imageContainer');
  const imageItems = imageContainer.getElementsByClassName('image-item');
  const leftButton = document.getElementById('leftButton');
  const rightButton = document.getElementById('rightButton');

  console.log(imageItems);

  if (direction === 'right') {
    currentImageIndex += 5; // 오른쪽 화살표 클릭
  } else {
    currentImageIndex -= 5; // 왼쪽 화살표 클릭
  }

  // 버튼 방향에 따른 현재 0번 이미지 위치에 놓인 -> 실제 이미지 idx
  currentImageIndex = Math.max(0,
      Math.min(currentImageIndex, images.length - 5));

  //시작 위치, 몇개 받아올지
  for (let i = 0; i < imageItems.length; i++) {
    const imageNumber = images[currentImageIndex + i]; //해당 위치에 오는 이미지 idx
    // console.log("imageNumber" + imageNumber)
    imageItems[i].innerText = imageNumber; // 배열의 값을 div에 표시
    // console.log(i + " ImageItems" + imageItems[i])
    imageItems[i].setAttribute('data-idx', imageNumber); // 고유 값 설정
  }

  // 버튼 활성화/비활성화 설정
  leftButton.classList.toggle('disabled', currentImageIndex === 0);
  rightButton.classList.toggle('disabled',
      currentImageIndex >= images.length - 5);
}

//To DO : 페이지에 선택된 번호 표시
function updateDiaryContent(element) {
  const imageNumber = element.getAttribute('data-idx'); // 클릭한 아이템의 고유 값 가져오기
  const diaryContent = document.getElementById('diaryContent');
  diaryContent.innerText = '선택된 이미지: ' + imageNumber;
}

// 초기 이미지 로드
currentImageIndex = 0; // 초기 인덱스를 0으로 설정하여 1부터 시작하도록
loadImages('left'); // 초기 상태에서 1부터 5까지 보여주도록 왼쪽으로 이동

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

//const imagePath = "${pageContext.request.contextPath}/image/content/community/";

function updateDistanceValues() {
  const rangeValue = document.getElementById('distanceRange').value;
  // document.getElementById('minValue').innerText = `${rangeValue}km`;
  // document.getElementById('maxValue').innerText = `50km`; // 최대값은 항상 50km로 고정

  console.log("필터 값은" + rangeValue);
  // 슬라이더 값이 변경될 때마다 API 호출
  //fetchFilteredData(rangeValue);
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





