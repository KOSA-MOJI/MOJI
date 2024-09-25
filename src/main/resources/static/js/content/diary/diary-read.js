const coupleId = document.getElementById("log-in-couple-id").value
// const coupleName = document.getElementById("log-in-couple-id").value
const coupleName = "광환커플2"
let currentPage = 0;
let diaryId;
const lazyLoadLimit = 2;
const lazyLoadNum = 5;
let pages = []

function updatePageContent() {
  resetSideChild()
  const leftSide = document.getElementById('left-side');
  const rightSide = document.getElementById('right-side');

  // 현재 페이지가 범위를 벗어나지 않도록 처리
  if (currentPage < 0) {
      currentPage = 0;
  } else if (currentPage >= pages.length) {
      currentPage = pages.length - 1;
  }
  // 페이지 내용 업데이트
  leftSide.appendChild(createLeftChild(currentPage))
  rightSide.appendChild(createRightChild(currentPage))

  if (currentPage === 0) {
      leftSide.classList.add('hidden');
  } else {
      leftSide.classList.remove('hidden');
  }

}
function checkLoad(){
  if(currentPage===0) return
  if(currentPage<lazyLoadLimit){
      prefetchPages("before")
  }else if((pages.length-lazyLoadLimit)< currentPage){
      prefetchPages("after")
  }
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
function createLeftChild(idx){
  let data = pages[idx].left
  if(data===null){return document.createElement("div")}
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
  let prevPageDiv = document.createElement("div")
  let topContentDiv = document.createElement("div")

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

  //일기내용
  contentDiv.innerText = data.content;
  contentDiv.setAttribute("style",
      `font-size:${fontSize}px; font-color:${fontColor}; text-align:${textAlignment}`
      + `overflow-y: auto; max-height: 100%; padding: 10px;`
  )

  topContentDiv.setAttribute("style",
      "background-color: rgba(255, 255, 255, 0.7);;"
      + "display: flex;"
      + " justify-content: center;"
      + "align-items: center;"
      + " width: 80%;"
      + "height: 30rem;"
      + "margin-top: .5rem;")

  topContentDiv.appendChild(contentDiv)
  container.appendChild(topContentDiv)

  prevPageDiv.id="prevPage"
  prevPageDiv.addEventListener("click",prevPage)
  document.querySelector("#left-side").appendChild(prevPageDiv)

  container.setAttribute("style",
      "width:100%; height:100%;"+
      "display: flex;" +
      "flex-direction: column;" +
      "align-items: center;"+
      "gap: 10%;")
  return container
}

function createRightChild(idx){
  let data = pages[idx].right
  let container = document.createElement("div")
  let nextPageDiv = document.createElement("div")
  nextPageDiv.id="nextPage"
  nextPageDiv.addEventListener("click",nextPage)
  document.querySelector("#right-side").appendChild(nextPageDiv)

  if(idx===0){
    let diaryImg = document.createElement("img")
    diaryImg.src=`${imagePath}diary-cover.png`
    diaryImg.setAttribute("style","position: absolute; width: 100%; height: 100%; top: 0; left : 0; object-fit: cover; z-index: -1;")
    container.appendChild(diaryImg)
    let diaryTitle = document.createElement("h2")
    let diaryCoverImage = document.createElement("img")
    let imageUploadDiv = document.createElement("div")

    diaryTitle.innerText=coupleName+"의 다이어리"

    diaryCoverImage.src=data.coverImage
    diaryCoverImage.setAttribute("id","diaryCoverImage")
    diaryCoverImage.setAttribute("style","width:55%;height:50%;margin-top:7rem")
    imageUploadDiv.innerHTML= `
      <label for="upload-file" id="cover-image-upload-btn">
        업로드
      </label>
      <input id="upload-file" type="file" name="file" accept=".jpg, .jpeg, .png, .pdf" onchange="UploadImage(this)" style="display: none;"/>
    `
    container.appendChild(diaryTitle)
    container.appendChild(imageUploadDiv)
    container.appendChild(diaryCoverImage)
  }else{
    let templateImg = document.createElement("img")
    templateImg.src=pages[idx].left.template.templateImage;
    templateImg.setAttribute("style","position: absolute; width: 100%; height: 100%; object-fit: cover; z-index: -1;")
    container.appendChild(templateImg)

    let btnContainer = document.createElement("div")
    let deletePageBtn = document.createElement("img")
    let setPublicStatusBtn = document.createElement("img")

    deletePageBtn.setAttribute("style","position:absolute; top:2%; right:1%; width:7%; height:6%;")
    deletePageBtn.setAttribute("onclick","deleteCurPage()")
    setPublicStatusBtn.setAttribute("style","position:absolute; top:2%; right:11%; width:7%; height:6%;")
    setPublicStatusBtn.setAttribute("onclick","togglePublicStatus(this)")
    deletePageBtn.src=`${imagePath}delete-page.png`
    setPublicStatusBtn.src=`${imagePath}${pages[idx].left.publicStatus?"show-page.png":"hide-page.png"}`
    btnContainer.setAttribute("style","display:flex; flex-direction: row; top:0;width : 100%; height: 8%")

    btnContainer.appendChild(setPublicStatusBtn)
    btnContainer.appendChild(deletePageBtn)
    container.appendChild(btnContainer)

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

    img_next_btn.innerText="▶"
    img_next_btn.addEventListener("click",function (){
      if(cur_img_pointer<cur_img_list.length-1){
        cur_img_pointer++;
        img_box.src=cur_img_list[cur_img_pointer]
      }
    })
    img_next_btn.setAttribute("style","right:0")

    img_prev_btn.innerText="◀"
    img_prev_btn.addEventListener("click",function (){
      if(0<cur_img_pointer){
        cur_img_pointer--;
        img_box.src=cur_img_list[cur_img_pointer]
      }
    })
    img_prev_btn.setAttribute("style","left:0")
    img_next_btn.setAttribute("style", "right:0; border:none; outline:none;background:none");
    img_prev_btn.setAttribute("style", "left:0; border:none; outline:none;background:none");
    img_box.setAttribute("style",
        "width:80%;" +
        "height:80%;" +
        "vertical-align: middle;" +
        "padding:7px;" +
        "object-fit:contain;" +  // 이미지가 규격 내에 맞춰지도록 변경
        "visibility: visible;" +
        "margin-top: 90px;"
    );


    img_box.setAttribute("onerror","this.style.visibility='hidden';")
    img_container.appendChild(img_prev_btn)
    img_container.appendChild(img_box)
    img_container.appendChild(img_next_btn)
    container.appendChild(img_container)
  }
  container.setAttribute("style",
    "width: 100%; " +
    "height: 100%;" +
    "display: flex;" +
    "flex-direction: column;"+
    "align-items: center;"
    // "gap: 10%;"
  )
  return container
}
function deleteCurPage(){
  if(currentPage!==0){
    alert("페이지를 삭제하시겠습니까?")
    //TODO: 페이지 삭제
    // 페이지 실제 DB에서 삭제
    // 이전페이지로 옮기고, 해당 아이디를 기준으로 pages에서도 삭제
  }
}

function togglePublicStatus(elem){
  let curPage = pages[currentPage].left
  console.log(curPage)
  fetch(`/api/diary/public/${curPage.pageId}?publicStatus=${curPage==="y"?"false":"true"}`,{
    method:`POST`
  }).then((res)=>{
    if(res.ok) return null
    throw Error("cannot change public status this page!")
  }).then(()=>{
    curPage.publicStatus=curPage.publicStatus==="y"?"n":"y"
    elem.src = curPage.publicStatus==="y"
        ?`${imagePath}show-page.png`
        :`${imagePath}hide-page.png`
  }).catch(err=>console.log(err))
}



function UploadImage(input){
  const file = input.files[0]; // 선택된 파일 가져오기
  if (file) {
    const formData = new FormData();
    formData.append("diaryCoverImage", file);

    fetch(`/api/diary/coverImage/${diaryId}`, {
      method: "POST",
      body: formData,
    })
      .then(response => {
        if (!response.ok) {
          throw new Error("업로드 실패");
        }
        return response.text();
      })
      .then(data => {
        console.log("업로드 성공:", data);
        let img = document.getElementById("diaryCoverImage")
        if(!img){
          throw Error("변경할 이미지 박스가 없음!")
        }
        pages[0].right.coverImage=data
        img.src=data;
      })
      .catch(error => {
        console.error("오류:", error);
      });
  } else {
    console.log("파일이 선택되지 않았습니다.");
  }
}

function prevPage() {
  if (currentPage > 0) {
      const oldPage = currentPage;
      currentPage--;
      preparePageTransition('left', oldPage, currentPage);
  }
}

function nextPage() {
  if (currentPage < pages.length - 1) {
      const oldPage = currentPage;
      currentPage++;
      preparePageTransition('right', oldPage, currentPage);
  }
}

function preparePageTransition(direction, oldPage, newPage) {
  const bookContainer = document.getElementById('book-container');
  const leftSide = document.getElementById('left-side');
  const rightSide = document.getElementById('right-side');

  const pageTransition = document.createElement('div');
  pageTransition.className = 'page-transition';

  const frontContent = direction === 'right' ? createRightChild(oldPage) : createLeftChild(oldPage);
  const backContent = direction === 'right' ? createLeftChild(newPage) : createRightChild(newPage);

  const frontPage = document.createElement('div');
  frontPage.className = 'page-content front';
  frontPage.setAttribute("style","z-index:3;")
  frontPage.appendChild(frontContent);

  const backPage = document.createElement('div');
  backPage.className = 'page-content back';
  backPage.appendChild(backContent);

  pageTransition.appendChild(frontPage);
  pageTransition.appendChild(backPage);

  if(direction==='right'){
      pageTransition.style.right=`${bookContainer.style.width/2}`
  }else{
      pageTransition.style.left=`${bookContainer.style.width/2}`
  }
  pageTransition.style.transformOrigin = direction === 'right' ? '0% 50%' : '100% 50%';

  bookContainer.appendChild(pageTransition);
  if (direction === 'right') {
      resetRightSideChild()
      rightSide.appendChild(createRightChild(newPage))
  } else {
      resetLeftSideChild()
      leftSide.appendChild(createLeftChild(newPage))
  }

  setTimeout(() => {
      pageTransition.style.transition = 'transform 1s';
      if (direction === 'right') {
          pageTransition.style.animation = 'page-flip-right 1s forwards';
      } else {
          pageTransition.style.animation = 'page-flip-left 1s forwards';
      }
  }, 10);

  pageTransition.addEventListener('animationend', () => {
      bookContainer.removeChild(pageTransition)
      if (direction === 'right') {
          resetLeftSideChild()
          leftSide.appendChild(createLeftChild(newPage))
      } else {
          resetRightSideChild()
          rightSide.appendChild(createRightChild(newPage))
      }
      //TODO
      checkLoad()
      if (currentPage === 0) {
          leftSide.classList.add('hidden');
      } else {
          leftSide.classList.remove('hidden');
      }
  });
}


function createPagesData(pageData){
  let leftData = {
      pageId : pageData.pageId,
      createdAt : pageData.createdAt,
      weather : pageData.weather,
      content : pageData.content,
      fontSize : pageData.fontSize,
      fontColor: pageData.fontColor,
      textAlignment : pageData.textAlignment,
      publicStatus : pageData.publicStatus,
      template : pageData.template
  }
  let rightData ={
      locations : pageData.locations
  }
  return {
      left : leftData,
      right : rightData
  }
}

function loadDiary(id){
  fetch("api/diary/"+id).then((res)=>{
      console.log(id)
      if(res.ok) return res.json()
      throw Error("Diary Not Found")
  }).then((data)=>{
      console.log(data)
      diaryId=data.diaryId
      pages.push({
          left:null,
          right:{coverImage:data.coverImage}
      })
      let page = data.pages[0]
      pages.push(createPagesData(page))
      updatePageContent()
  }).catch(err=>console.log(err))
}

function addDays(date, days) {
  let result = new Date(date);
  result.setDate(result.getDate() + days);
  return result.toISOString().split('T')[0];
}

async function prefetchPages(direction){
  let curDate = pages[currentPage].left.createdAt
  let date = new Date(curDate);
  let startDate,endDate;
  if(direction==='after'){
      startDate=addDays(date,1)
      endDate=addDays(date,lazyLoadNum)
  }else{
      startDate=addDays(date,-(lazyLoadNum))
      endDate=addDays(date,-1)
  }
  console.log(`api/diary/page/${diaryId}?startDate=${startDate}&endDate=${endDate}`)
  fetch(`api/diary/page/${diaryId}?startDate=${startDate}&endDate=${endDate}`).then((res)=>{
      if(res.ok) return res.json()
      throw Error()
  }).then((data)=>{
      console.log(data)
      let dataList=[]
      data.forEach((page)=>{
          dataList.push(createPagesData(page))
      })
      if(direction==='after') {
          pages = pages.concat(dataList)
      }
      else {
          pages.splice(currentPage,0,...dataList)
          currentPage+=dataList.length
      }
  }).catch(err=>console.log(err))
}

document.addEventListener('DOMContentLoaded', function() {
  loadDiary(coupleId)
});
