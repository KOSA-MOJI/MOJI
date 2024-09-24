let coupleId;
let currentPage = 0;
let diaryId;
const lazyLoadLimit = 2;
const lazyLoadNum = 5;
const loginCoupleId = document.getElementById("log-in-couple-id").value
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

function checkLoad() {
  if (currentPage === 0) {
    return
  }
  if (currentPage < lazyLoadLimit) {
    prefetchPages("before")
  } else if ((pages.length - lazyLoadLimit) < currentPage) {
    prefetchPages("after")
  }
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

function createLeftChild(idx) {
  let data = pages[idx].left
  if (data === null) {
    return document.createElement("div")
  }
  let container = document.createElement("div")
  let dateDiv = document.createElement("div")
  let weatherDiv = document.createElement("div")
  let contentDiv = document.createElement("div")
  dateDiv.innerText = data.createdAt;
  weatherDiv.innerText = data.weather;
  contentDiv.innerText = data.content;
  let prevPageDiv = document.createElement("div")
  prevPageDiv.id = "prevPage"
  prevPageDiv.addEventListener("click", prevPage)
  document.querySelector("#left-side").appendChild(prevPageDiv)
  container.appendChild(dateDiv)
  container.appendChild(weatherDiv)
  container.appendChild(contentDiv)
  container.setAttribute("style",
      "width:100%; height:100%;" +
      "display: flex;" +
      "flex-direction: column;" +
      "align-items: center;" +
      "gap: 10%;")
  return container
}

function createRightChild(idx) {
  let data = pages[idx].right
  let container = document.createElement("div")
  if (idx === 0) {
    let diaryTitle = document.createElement("h2")
    let diaryCoverImage = document.createElement("img")
    diaryTitle.innerText = coupleId + "의 다이어리"
    diaryCoverImage.src = data.coverImage
    diaryCoverImage.setAttribute("style", "width:50%;height:50%;")
    container.appendChild(diaryTitle)
    container.appendChild(diaryCoverImage)
  } else {
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

    setTimeout(() => {
      map.relayout();
      map.setCenter(new kakao.maps.LatLng(37.5665, 126.9780));
    }, 0);
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
    data.locations.forEach((location, idx) => {
      let marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(location.latitude, location.longitude),
      });
      img_lists.push(location.imageUrls.length > 0 ? location.imageUrls.map(
          data => data.mapImage) : ["https://placehold.co/400"])
      kakao.maps.event.addListener(marker, 'click', function () {
        cur_img_list = img_lists[idx]
        cur_img_pointer = 0
        img_box.src = cur_img_list[cur_img_pointer]
      })
      markers.push(marker)
    });
    cur_img_list = img_lists
    img_box.src = cur_img_list[cur_img_pointer]

    img_next_btn.innerText = "다음"
    img_next_btn.addEventListener("click", function () {
      if (cur_img_pointer < cur_img_list.length - 1) {
        cur_img_pointer++;
        img_box.src = cur_img_list[cur_img_pointer]
      }
    })
    img_next_btn.setAttribute("style", "right:0")

    img_prev_btn.innerText = "이전"
    img_prev_btn.addEventListener("click", function () {
      if (0 < cur_img_pointer) {
        cur_img_pointer--;
        img_box.src = cur_img_list[cur_img_pointer]
      }
    })
    img_prev_btn.setAttribute("style", "left:0")

    img_box.setAttribute("style",
        "width:80%;" +
        "height:80%;" +
        "vertical-align: middle;" +
        "object-fit:cover" +
        "background-color: lightgray;" +
        "visibility: visible;"
    )
    img_box.setAttribute("onerror", "this.style.visibility='hidden';")

    img_container.appendChild(img_prev_btn)
    img_container.appendChild(img_box)
    img_container.appendChild(img_next_btn)
    container.appendChild(img_container)

    if (markers.length > 0) {
      let bounds = new kakao.maps.LatLngBounds();
      for (let i = 0; i < markers.length; i++) {
        bounds.extend(markers[i].getPosition());
      }
      map.setBounds(bounds);
    }
  }
  let nextPageDiv = document.createElement("div")
  nextPageDiv.id = "nextPage"
  nextPageDiv.addEventListener("click", nextPage)
  document.querySelector("#right-side").appendChild(nextPageDiv)

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

  const frontContent = direction === 'right' ? createRightChild(
      oldPage).outerHTML : createLeftChild(oldPage).outerHTML;
  const backContent = direction === 'right' ? createLeftChild(newPage).outerHTML
      : createRightChild(newPage).outerHTML;

  pageTransition.innerHTML =
      `<div class="page-content front">${frontContent || ""}</div>
         <div class="page-content back">${backContent || ""}</div>`;

  if (direction === 'right') {
    pageTransition.style.right = `${bookContainer.style.width / 2}`
  } else {
    pageTransition.style.left = `${bookContainer.style.width / 2}`
  }
  pageTransition.style.transformOrigin = direction === 'right' ? '0% 50%'
      : '100% 50%';

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

function createPagesData(pageData) {
  let leftData = {
    pageId: pageData.pageId,
    createdAt: pageData.createdAt,
    weather: pageData.weather,
    content: pageData.content,
    publicStatus: pageData.publicStatus,
    template: pageData.template
  }
  let rightData = {
    locations: pageData.locations
  }
  return {
    left: leftData,
    right: rightData
  }
}

function loadDiary(id) {
  coupleId = id
  fetch("api/diary/" + coupleId).then((res) => {
    if (res.ok) {
      return res.json()
    }
    throw Error("Diary Not Found")
  }).then((data) => {
    console.log(data)
    diaryId = data.diaryId
    pages.push({
      left: null,
      right: {coverImage: data.coverImage}
    })
    let page = data.pages[0]
    pages.push(createPagesData(page))
    updatePageContent()
  }).catch(err => console.log(err))
}

function addDays(date, days) {
  let result = new Date(date);
  result.setDate(result.getDate() + days);
  return result.toISOString().split('T')[0];
}

async function prefetchPages(direction) {
  let curDate = pages[currentPage].left.createdAt
  let date = new Date(curDate);
  let startDate, endDate;
  if (direction === 'after') {
    startDate = addDays(date, 1)
    endDate = addDays(date, lazyLoadNum)
  } else {
    startDate = addDays(date, -(lazyLoadNum))
    endDate = addDays(date, -1)
  }
  console.log(
      `api/diary/page/${diaryId}?startDate=${startDate}&endDate=${endDate}`)
  fetch(
      `api/diary/page/${diaryId}?startDate=${startDate}&endDate=${endDate}`).then(
      (res) => {
        if (res.ok) {
          return res.json()
        }
        throw Error()
      }).then((data) => {
    console.log(data)
    let dataList = []
    data.forEach((page) => {
      dataList.push(createPagesData(page))
    })
    if (direction === 'after') {
      pages = pages.concat(dataList)
    } else {
      pages.splice(currentPage, 0, ...dataList)
      currentPage += dataList.length
    }
  }).catch(err => console.log(err))
}

document.addEventListener('DOMContentLoaded', function () {
  loadDiary(loginCoupleId)
});
