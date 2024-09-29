<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/content/map/map-read.css">

<security:authorize access="isAuthenticated()">
    <security:authentication property="principal" var="principal"/>
</security:authorize>

<meta name="_csrf" content="${_csrf.token}"/>
<%--</div>--%>
<div class="map-content">
    <div id="map" class="map" style="width: 100%; height: 100%"></div>
    <div class="button-container">
        <button id="diary-marker" onclick="fetchLocations(1)">커플 다이어리 장소</button>
        <button id="my-scrap-marker" onclick="fetchLocations(2)">나의 스크랩 장소</button>
        <button id="partner-scrap-marker" onclick="fetchLocations(3)">연인의 스크랩 장소</button>
    </div>
</div>
<!-- js -->
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${kakaoApiKey}&libraries=clusterer"></script>
<script> const email = "${principal.email}"; </script>
<script> const coupleId = "${principal.couple.couple_id}"; </script>
<script> const imagePath = `${pageContext.request.contextPath}/image/common/`; </script>
<script src="${pageContext.request.contextPath}/js/content/map/map-read.js"></script>