// const coupleId = document.getElementById("coupleId").value;
let curStatus = 1;
let map, clusterer;
let pageMarkers = [];
let polylines= [];
let curLocMarker;
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute(
  'content');

function resetMap() {
  console.log("리셋 호출")
  pageMarkers.forEach((marker)=> {
    marker.setMap(null)
  })
  polylines.forEach((polyline)=> {
    polyline.setMap(null)
  })
  pageMarkers=[]
  polylines=[]
  if (map && clusterer) {
    map.setCenter(new kakao.maps.LatLng(36.2683, 127.6358))
    map.setLevel(13)
    clusterer.clear();
    return; // 지도와 클러스터러를 재사용
  }

  map = new kakao.maps.Map(document.getElementById('map'), {
    center: new kakao.maps.LatLng(36.2683, 127.6358),
    level: 13
  });

  clusterer = new kakao.maps.MarkerClusterer({
    map: map,
    averageCenter: true,
    minLevel: 10
  });

  kakao.maps.event.addListener(clusterer, 'clusterclick', function (cluster) {
    var level = map.getLevel() - 1;
    map.setLevel(level, { anchor: cluster.getCenter() });
  });
}

function fetchLocations(caseNum) {
  curStatus = caseNum;
  resetMap();
  console.log(coupleId);
  let url = caseNum === 1
    ? `/api/map/diary/${coupleId}`
    : caseNum === 2
      ? `/api/map/scrap/mine?email=${email}`
      : `/api/map/scrap/partner?coupleId=${coupleId}&email=${email}`;

  console.log(url)
  fetch(url,{
    method:'GET',
    headers: {
      'X-CSRF-TOKEN': csrfToken
    }
  })
    .then((res) => res.json())
    .then((locations) => {
      if (!Array.isArray(locations)) {
        throw new Error("Expected an array of locations but got a different response.");
      }
      console.log(locations)
      let markersPromises = locations.map(async (location) => {
        let imgInfo = location.imageUrls;

        let imageSrc = imgInfo.length === 0
          ? `${imagePath}color-no-image.png`
          : imgInfo[0].mapImage;

        const image = new Image();
        image.src = imageSrc;

        const loadedImageSrc = await new Promise((resolve) => {
          image.onload = () => resolve(imageSrc);
          image.onerror = () => {
            console.log(imageSrc + " is not an image!");
            resolve(`${imagePath}color-no-image.png`);
          };
        });

        let content =
          `<div class="custom-image-marker" onclick="showPageLocation(${location.pageId})">`
          + `<img src="${loadedImageSrc}">`
          + `</div>`;

        let position = new kakao.maps.LatLng(location.latitude, location.longitude);

        return new kakao.maps.CustomOverlay({
          position: position,
          content: content
        });
      });

      Promise.all(markersPromises).then((markers) => {
        clusterer.addMarkers(markers);
      });
    })
    .catch(err => console.log(err));
}

function createOverlay(location){
  return  content =
  `<div className="wrap">
    <div className="info">
      <div className="address">
        ${location.address}
        <div
          className="close"
          onClick={() => setIsOpen(false)}
          title="닫기"
        ></div>
      </div>
      <div className="body">
        <div className="img">
          <img
            src="${location.imageUrls[0]}"
            width="73"
            height="70"
            alt="첫번째 장소"
          />
        </div>
        <div className="desc">
        </div>
      </div>
    </div>
  </div>`

}


function showPageLocation(pageId) {
  resetMap()
  console.log(pageId);
  fetch(`/api/map/page/${pageId}`,{
    method:'GET',
    headers: {
      'X-CSRF-TOKEN': csrfToken
    }
  }).then((res) => {
    if (res.ok) return res.json();
    throw Error();
  }).then((data) => {
    clusterer.clear();
    let markers = []
    data.locations.forEach((location, idx) => {
      let marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(location.latitude, location.longitude)
      });
      markers.push(marker);
      let content =
        `<div class="custom-spot-marker">`
        + `${idx + 1}`
        + `</div>`;
      let customMarker = new kakao.maps.CustomOverlay({
        position: marker.getPosition(),
        content: content
      });
      pageMarkers.push(customMarker)
      customMarker.setMap(map)
    })
    console.log(pageMarkers)
    setMapBounds(markers)
    if (pageMarkers.length >= 2) {
      for (let i = 0; i < pageMarkers.length - 1; i++) {
        let markers = [pageMarkers[i], pageMarkers[i + 1]]
        drawLines(markers)
      }
    }
  }).catch(err => console.log(err));
}

function setMapBounds(markers) {
  // 마커의 위치를 기반으로 경계 설정
  // let bounds = new kakao.maps.LatLngBounds();
  //
  // // 마커가 하나일 경우, 해당 위치로 경계 설정
  // if (markers.length === 1) {
  //   let position = markers[0].getPosition();
  //   bounds.extend(new kakao.maps.LatLng(position.getLat() + 0.001, position.getLng() + 0.001)); // 위쪽 오른쪽
  //   bounds.extend(new kakao.maps.LatLng(position.getLat() - 0.001, position.getLng() - 0.001)); // 아래쪽 왼쪽
  // } else {
  //   markers.forEach(marker => {
  //     bounds.extend(marker.getPosition());
  //   });
  //
  //   // 경계의 너비와 높이를 계산
  //   let hGap = bounds.getNorthEast().getLng() - bounds.getSouthWest().getLng(); // 경계의 가로 길이
  //   let vGap = bounds.getNorthEast().getLat() - bounds.getSouthWest().getLat(); // 경계의 세로 길이
  //
  //   // 경계 수정: 너비와 높이를 2배로 늘리기
  //   bounds.extend(new kakao.maps.LatLng(bounds.getNorthEast().getLat() + vGap, bounds.getNorthEast().getLng())); // 상단 추가
  //   bounds.extend(new kakao.maps.LatLng(bounds.getSouthWest().getLat() - vGap, bounds.getSouthWest().getLng())); // 하단 추가
  // }
  //
  // map.setBounds(bounds);
  //
  // let newNorthEast = bounds.getNorthEast();
  // let newSouthWest = bounds.getSouthWest();
  // let centerLat = newSouthWest.getLat() + (newNorthEast.getLat() - newSouthWest.getLat()) / 8;
  // let centerLng = newSouthWest.getLng() + (newNorthEast.getLng() - newSouthWest.getLng()) / 2;
  // const newCenter = new kakao.maps.LatLng(centerLat, centerLng);
  //
  // map.setCenter(newCenter);

  let bounds = new kakao.maps.LatLngBounds();
  markers.forEach(marker => {
    bounds.extend(marker.getPosition());
  });

  map.setBounds(bounds);
  let center = map.getCenter();
  const offset = 0.0005; // 조정할 값 (위도 방향으로 약간 위로 이동)
  const newCenter = new kakao.maps.LatLng(center.getLat() - offset, center.getLng());
  map.setCenter(newCenter);
}



function drawLines(markers){
  let path = markers.map(marker => marker.getPosition());
  polyline = new kakao.maps.Polyline({
    path: path,
    strokeWeight: 5,
    strokeColor: '#9aff93',
    strokeOpacity: 1,
    strokeStyle: 'solid'
  });
  polylines.push(polyline)
  polyline.setMap(map);
}


document.addEventListener("DOMContentLoaded", function () {
  if(coupleId===""){
    document.getElementById("diary-marker").disabled=true
    document.getElementById("partner-scrap-marker").disabled=true
    curStatus=2;
  }
  fetchLocations(curStatus);
});
