document.addEventListener("DOMContentLoaded",function() {
    console.log("loaded")
    document.getElementById("add-image-div").style.display="none"
    let cnt=0;
    let mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
            level: 3
        };
    let map = new kakao.maps.Map(mapContainer, mapOption); // 지도 생성
    let places = new kakao.maps.services.Places(); // 장소 검색 객체 생성
    let paginationEl = document.getElementById('pagination'); // 페이지네이션 컨테이너
    let markers=[]
    let imageMaps = {};
    let selectedMarkers=[];
    let selectedMarkerIdx;
    let selectedPlaces=[]

    document.getElementById("add-datespot-btn").addEventListener("click",function (){
        document.getElementById("add-image-div").style.display="none"
        document.getElementById("add-datespot-div").style.display="block"
        displaySearchPlaces(places)
    })
    document.getElementById("add-image-btn").addEventListener("click",function (){
        document.getElementById("add-image-div").style.display="block"
        document.getElementById("add-datespot-div").style.display="none"
        displaySelectedPlaces(selectedPlaces)
    })

    document.getElementById("result-maker").addEventListener("click", function () {
        console.log(selectedPlaces)
        console.log(imageMaps)
    })

    document.getElementById("delete-loc-btn").addEventListener("click",removeSelectedMarker)

    document.getElementById("image-input").addEventListener("change", function (event) {
        const file = event.target.files[0];
        console.log(selectedMarkerIdx);
        if (file && (selectedMarkerIdx!==null)) {
            console.log("image loaded");
            const imageFrame = document.createElement("div");
            const deleteButton = document.createElement("button");
            const imgTag = document.createElement("img");
            deleteButton.innerText='X'
            deleteButton.addEventListener('click',function(e){
                let deleteIdx = imageMaps[selectedMarkerIdx].indexOf(e.target.parentElement);
                imageMaps[selectedMarkerIdx].splice(deleteIdx,1);
                displayImage()
            })
            imgTag.src = URL.createObjectURL(file);
            imgTag.style.width = "200px";
            imgTag.style.margin = "10px";
            imageFrame.id=URL.createObjectURL(file)
            imageFrame.appendChild(deleteButton).appendChild(imgTag);
            imageMaps[selectedMarkerIdx].push(imageFrame)
            displayImage();
        }
    })


    // 검색 버튼 클릭 시 이벤트
    document.getElementById('search-map-btn').addEventListener('click', function() {
        let query = document.getElementById('search-query').value;
        if (query) {
            places.keywordSearch(query, placesSearchCB, {
                size: 5 // 페이지당 10개의 검색 결과를 표시
            });
        }
    });

    function displayImage(){
        let imageContainer = document.getElementById("image-container")
        imageContainer.replaceChildren();
        if(selectedMarkerIdx>0 && imageMaps[selectedMarkerIdx].length>0)
        imageMaps[selectedMarkerIdx].forEach((e)=>{
            imageContainer.appendChild(e);
        })

    }

    function placesSearchCB(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
            displaySearchPlaces(data);
            displayPagination(pagination);
        }
    }
    function displaySelectedPlaces(selectedPlaces){
        for (let i = 0; i < selectedPlaces.length; i++) {
            let marker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(selectedPlaces[i].y, selectedPlaces[i].x),
            });
            marker.placeInfo = selectedPlaces[i];
            selectedMarkers.push(marker)
            kakao.maps.event.addListener(marker,'click',function (){
                selectedMarkerIdx=i;
                document.getElementById("selected_point").innerText=selectedMarkers.includes(marker)?marker.placeInfo.place_name:"";
                displayImage();
            })
        }
        adjustMapToMarkers(selectedMarkers)
    }

    function displaySearchPlaces(places) {
        let placeList = document.getElementById('places-list');
        placeList.innerText='' // 내부 페이지 제거
        removeMarkers() // 마커 제거
        for (let i = 0; i < places.length; i++) {
            let item = document.createElement('div');
            let locInfo = document.createElement("div")
            let selectLocBtn = document.createElement("button")
            item.style.display="inline-block"
            item.style.width="100%"
            locInfo.style.float="left"
            locInfo.innerText = places[i].place_name;
            selectLocBtn.style.float="right"
            selectLocBtn.innerText="선택"
            selectLocBtn.addEventListener("click",function (){
                console.log("loc_selected")
                imageMaps[cnt++]=[]
                selectedPlaces.push(places[i])
                resetSearchResults();
                removeMarkers();
            })
            item.appendChild(locInfo)
            item.appendChild(selectLocBtn)
            placeList.appendChild(item);

            let marker = new kakao.maps.Marker({
                map: map,
                position: new kakao.maps.LatLng(places[i].y, places[i].x)
            });
            markers.push(marker)
        }
        adjustMapToMarkers(markers);
    }

    function removeSelectedMarker(){
        if((selectedMarkerIdx!==null)&&(selectedPlaces.length>0)){
            //삭제
            selectedMarkers.splice(selectedMarkerIdx,1)
            selectedPlaces.splice(selectedMarkerIdx,1)
            imageMaps[selectedMarkerIdx]=[]
            // 삭제 후 당기기
            for(let i = selectedMarkerIdx;i<imageMaps.length-1;i++){
                imageMaps[selectedMarkerIdx]=imageMaps[selectedMarkerIdx+1]

            delete imageMaps[imageMaps.length-1]
            }
        // selectedMarkerIdx=null;
            selectedMarkerIdx--;
            displaySelectedPlaces(selectedPlaces)
            displayImage()
        }
    }


    function removeMarkers() {
        for (let i = 0; i < markers.length; i++) {
            markers[i].setMap(null); // 지도에서 마커를 제거
        }
        markers=[];
    }

    // pagination
    function displayPagination(pagination) {
        paginationEl.innerHTML = '';

        for (var i = 1; i <= pagination.last; i++) {
            var pageButton = document.createElement('a');
            pageButton.href = "#";
            pageButton.innerText = i;
            pageButton.classList.add("page-link");

            if (i === pagination.current) {
                pageButton.style.color = "red";
            }
            (function(i) {
                pageButton.addEventListener('click', function(event) {
                    event.preventDefault();
                    pagination.gotoPage(i);
                });
            })(i);

            paginationEl.appendChild(pageButton);
        }
    }
    function adjustMapToMarkers(markers) {
        if (markers.length === 0) return;

        let bounds = new kakao.maps.LatLngBounds();
        for (let i = 0; i < markers.length; i++) {
            bounds.extend(markers[i].getPosition());
        }
        map.setBounds(bounds);
    }

    function resetSearchResults(){
        document.getElementById("search-query").value=""
        document.getElementById("places-list").innerText=""
        document.getElementById("pagination").innerText=""
    }
})