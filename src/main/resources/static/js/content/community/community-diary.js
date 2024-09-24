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
  fetch(`/api/community/page/${communityData[curDataIndex + selectedIndex - 1].pageId}`)
    .then((res) => {
      if (res.ok) {
        return res.json()
      }
      throw Error("page data not found!")
    }).then((data) => {
    let pageData = createPagesData(data)
    updatePageContent(pageData)
  }).then(()=>{
    const scrapButton= document.getElementById('scrapButton')
    scrapButton.src = communityData[curDataIndex + selectedIndex - 1].scrapped
      ?`${imagePath}full-heart.png`
      :`${imagePath}gray-heart.png`
  })
    .catch(err => console.log(err))
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
  }).then(() => {
    if (isInit) updateDiaryContent(document.querySelector("#image-item-1"))
  }).catch(err => console.log(err))
}

// 페이지 화면 구현
function createPagesData(data){
  let leftData = {
    pageId : data.pageId,
    createdAt : data.createdAt,
    weather : data.weather,
    content : data.content,
    fontSize : data.fontSize,
    fontColor : data.fontColor,
    textAlignment : data.textAlignment,
    publicStatus : data.publicStatus,
    template : data.template
  }
  let rightData ={
    locations : data.locations
  }
  return {
    left : leftData,
    right : rightData
  }
}

function updatePageContent(data) {
  resetSideChild()
  const leftSide = document.getElementById('left-side');
  const rightSide = document.getElementById('right-side');
  leftSide.appendChild(createLeftChild(data))
  rightSide.appendChild(createRightChild(data))
}

function resetLeftSideChild(){
  const leftSide = document.getElementById('left-side');
  while (leftSide.firstChild) {
    leftSide.removeChild(leftSide.firstChild);
  }
}
function resetRightSideChild(){
  const rightSide = document.getElementById('right-side');
  while (rightSide.firstChild) {
    rightSide.removeChild(rightSide.firstChild);
  }
}
function resetSideChild(){
  resetLeftSideChild()
  resetRightSideChild()
}
function createLeftChild(pageData){
  let data = pageData.left
  let fontColor = data.fontColor;
  let fontSize = data.fontSize;
  let textAlignment = data.textAlignment;
  let templateUrl = data.template.templateImage;
  let container = document.createElement("div")
  let dateDiv=document.createElement("div")
  let weatherDiv = document.createElement("div")
  let dateWeatherDiv = document.createElement("div")
  let contentDiv=document.createElement("div")
  let templateImg = document.createElement("img")

  templateImg.src=templateUrl;
  templateImg.setAttribute("style","position: absolute; width: 100%; height: 100%; object-fit: cover; z-index: -1;")
  container.appendChild(templateImg)

  dateDiv.innerText=data.createdAt;
  weatherDiv.innerText=data.weather;
  dateDiv.setAttribute("style","position: absolute; left: 10%;")
  weatherDiv.setAttribute("style","position: absolute; right: 10%;")
  dateWeatherDiv.appendChild(dateDiv)
  dateWeatherDiv.appendChild(weatherDiv)
  dateWeatherDiv.setAttribute("style","display: flex; flex-direction: row; width: 100%;")
  container.appendChild(dateWeatherDiv)

  contentDiv.innerText=data.content;
  contentDiv.setAttribute("style",`font-size:${fontSize}px; font-color:${fontColor}; text-align:${textAlignment}`)
  container.appendChild(contentDiv)

  container.setAttribute("style",
    "width:100%; height:100%;"+
    "display: flex;" +
    "flex-direction: column;" +
    "align-items: center;"+
    "gap: 10%;")
  return container
}

function createRightChild(pageData){
  let data = pageData.right
  let container = document.createElement("div")
  let templateImg = document.createElement("img")
  templateImg.src=pageData.left.template.templateImage;
  templateImg.setAttribute("style","position: absolute; width: 100%; height: 100%; object-fit: cover; z-index: -1;")
  container.appendChild(templateImg)

  let mapContainer = document.createElement("div");
  mapContainer.id = "map";
  mapContainer.setAttribute("style", "width:80%; height:40%; position : relative");
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
  let cur_img_list=[]
  let cur_img_pointer=0;
  let img_container = document.createElement("div")
  let img_next_btn = document.createElement("button")
  let img_prev_btn = document.createElement("button")
  let img_box = document.createElement("img")
  img_container.setAttribute("style",
    "width:90%; height:40%;"+
    "display: inline-block;" +
    "align-items: center;")

  // 만들어진 위치 마커와 이미지연결
  data.locations.forEach((location,idx) => {
    let marker = new kakao.maps.Marker({
      map: map,
      position: new kakao.maps.LatLng(location.latitude, location.longitude),
    });
    img_lists.push(location.imageUrls.length>0?location.imageUrls.map(data=>data.mapImage):["https://placehold.co/400"])
    kakao.maps.event.addListener(marker,'click',function (){
      cur_img_list=img_lists[idx]
      cur_img_pointer=0
      img_box.src=cur_img_list[cur_img_pointer]
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

  cur_img_list=img_lists
  img_box.src=cur_img_list[cur_img_pointer]

  img_next_btn.innerText="다음"
  img_next_btn.addEventListener("click",function (){
    if(cur_img_pointer<cur_img_list.length-1){
      cur_img_pointer++;
      img_box.src=cur_img_list[cur_img_pointer]
    }
  })
  img_next_btn.setAttribute("style","right:0")

  img_prev_btn.innerText="이전"
  img_prev_btn.addEventListener("click",function (){
    if(0<cur_img_pointer){
      cur_img_pointer--;
      img_box.src=cur_img_list[cur_img_pointer]
    }
  })
  img_prev_btn.setAttribute("style","left:0")

  img_box.setAttribute("style",
    "width:80%;" +
    "height:80%;"+
    "vertical-align: middle;"+
    "object-fit:cover"+
    "background-color: lightgray;"+
    "visibility: visible;"
  )
  img_box.setAttribute("onerror","this.style.visibility='hidden';")

  img_container.appendChild(img_prev_btn)
  img_container.appendChild(img_box)
  img_container.appendChild(img_next_btn)
  container.appendChild(img_container)

  container.setAttribute("style",
    "width: 100%; " +
    "height: 100%;" +
    "display: flex;" +
    "flex-direction: column;"+
    "align-items: center;"+
    "gap: 10%;"

  )
  return container
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
function applyFilter() {
  const updateRadius = document.getElementById('distanceRange').value;

  // console.log("필터 값은" + updateRadius);

  //radius 지정 및 초기화
  resetDefaultData(updateRadius);

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

function toggleScrap(){
  let curPage = communityData[curDataIndex+selectedIndex-1]
  fetch(`/api/community/scrap?email=${email}&pageId=${curPage.pageId}`,{
    method:`${curPage.scrapped?"DELETE":"POST"}`
  }).then((res)=>{
    if(res.ok) return null
    throw Error("cannot scrap this page!")
  }).then(()=>{
    const scrapButton= document.getElementById('scrapButton')
    curPage.scrapped=!curPage.scrapped
    scrapButton.src = curPage.scrapped
      ?`${imagePath}full-heart.png`
      :`${imagePath}gray-heart.png`
  }).catch(err=>console.log(err))
}
