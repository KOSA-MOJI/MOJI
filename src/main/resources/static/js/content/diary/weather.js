var weatherApiKey = "${weatherApiKey}";

$(document).ready(function() {
  let weatherIcon = {
    '01': 'fas fa-sun',
    '02': 'fas fa-cloud-sun',
    '03': 'fas fa-cloud',
    '04': 'fas fa-cloud-meatball',
    '09': 'fas fa-cloud-sun-rain',
    '10': 'fas fa-cloud-showers-heavy',
    '11': 'fas fa-poo-storm',
    '13': 'far fa-snowflake',
    '50': 'fas fa-smog'
  };

  window.getWeatherByCurrentLocation = async function() {
    try {
      const coords = await getCurrentLocation();
      getWeather(coords.latitude, coords.longitude);
      $('.weather-btn').hide(); // Hide button
      $('.weather-info').show(); // Show weather info
    } catch (error) {
      console.error("위치 정보를 가져오는데 실패했습니다:", error);
      alert("위치 정보를 가져오는데 실패했습니다. 기본 위치(서울)의 날씨를 표시합니다.");
      getWeather(37.5665, 126.9780);
      $('.weather-btn').hide(); // Hide button
      $('.weather-info').show(); // Show weather info
    }
  };

  function getCurrentLocation() {
    return new Promise((resolve, reject) => {
      if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(
            position => resolve(position.coords),
            error => reject(error)
        );
      } else {
        reject(new Error("Geolocation is not supported by this browser."));
      }
    });
  }

  function getWeather(lat, lon) {
    var apiURI = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + weatherApiKey + "&units=metric";
    console.log("API URI:", apiURI);

    $.ajax({
      url: apiURI,
      dataType: "json",
      type: "GET",
      success: function(data) {
        console.log("Weather data:", data);
        var iconCode = data.weather[0].icon.substr(0,2);
        var $Temp = Math.floor(data.main.temp) + 'º';
        var $city = data.name;
        var $weather_description = data.weather[0].main;

        $('.weather-icon').html('<i class="' + weatherIcon[iconCode] + '" style="font-size:100px;"></i>');
        $('.current-temp').text($Temp);
        $('.weather-city').text($city);


      },
      error:function(jqXHR,textStatus,errorThrown){
        console.error("API 요청 실패:", textStatus, errorThrown);
        alert('날씨 데이터를 가져오지 못했습니다. 오류:' + textStatus);
      }
    });
  }
});