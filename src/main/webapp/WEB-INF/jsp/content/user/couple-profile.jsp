<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<security:authorize access="isAuthenticated()">
    <security:authentication property="principal" var="principal"/>
</security:authorize>
<!-- css -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/content/user/profile-style.css"/>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/content/user/profile-modal-style.css"/>


<!-- jsp -->
<div class="main-panel">
    <div class="content-wrapper">
        <div class="page-header">
            <h3 class="page-title">Profile</h3>
        </div>
        <div class="profile-card">
            <h4>${principal.couple.coupleName}</h4>
            <div class="profile-content border-bottom text-center pb-4">
                <div class="triple-pfp-container">
                    <div class="individual pfp-background">
                        <div class="pfp-container">
                            <img
                                    class="pfp"
                                    id="woman-pfp"
                                    src="${principal.profileImageUrl}"
                                    onerror="this.src='${pageContext.request.contextPath}/image/content/user/default-avatar.avif'"
                            />
                        </div>
                        <i class="hidden bi bi-camera-fill"></i>
                    </div>
                    <div class="pfp-background">
                        <div class="pfp-container">
                            <img
                                    class="pfp"
                                    id="couple-pfp"
                                    src="${principal.couple.coupleProfileImage}"
                                    onerror="this.src='/image/content/user/default-avatar.avif'"
                            />
                        </div>
                        <i class="hidden bi bi-camera-fill"></i>
                    </div>
                    <div class="individual pfp-background">
                        <div class="pfp-container">
                            <img
                                    class="pfp"
                                    id="man-pfp"
                                    src="${partner.profileImageUrl}"
                                    onerror="this.src='/image/content/user/default-avatar.avif'"
                            />
                        </div>
                        <i class="hidden bi bi-camera-fill"></i>
                    </div>
                </div>
                <div class="btn-container d-flex justify-content-between">
                    <button
                            class="btn btn-success btn-lg"
                            onclick="location.href='#'"
                    >
                        <img src="${pageContext.request.contextPath}/image/common/diary.png"
                             width="40px"
                             alt="다이어리 버튼"/>
                        <p>다이어리</p>
                    </button>
                    <button
                            class="btn btn-success btn-lg"
                            onclick="location.href='#'"
                    >
                        <img src="${pageContext.request.contextPath}/image/common/map.png"
                             width="40px"
                             alt="데이트 스팟"/>
                        <p>데이트 스팟</p>
                    </button>
                    <button
                            class="btn btn-success btn-lg"
                            id="couple-info-edit-btn"
                            onclick="location.href='/user/couple/update-profile'"
                    >
                        <img src="${pageContext.request.contextPath}/image/common/About.png"
                             alt=""/>
                        <p>커플정보수정</p>
                    </button>
                </div>
            </div>
            <jsp:include page="/WEB-INF/jsp/component/couple-info.jsp"/>
            <p class="clearfix">
                <span class="float-start"> 커플 정보 변경 </span>
                <span class="float-end text-muted">
                <a href="/user/couple/update-profile" id="break-up"
                   style="color: rgb(255, 148, 148)"
                >변경</a>
                </span>
            <p class="clearfix">
                <span class="float-start"> 커플 상태 변경 </span>
                <span class="float-end text-muted">
                <a href="#" id="change-couple-profile" style="color: rgb(255, 148, 148)"
                >커플 끊기</a>
                </span>
            </p>

        </div>
    </div>
</div>
<!-- 커플 정보 수정 모달창-->
<div id="couple-info-edit-modal" class="modal">
    <div class="modal-content">
        <div class="card-body">
            <h4 class="card-title">커플 정보</h4>
            <p class="card-description">정보를 입력하세요</p>
            <form class="forms-sample">
                <div class="form-group">
                    <label for="exampleInputName1">커플 닉네임</label>
                    <input
                            type="text"
                            class="form-control"
                            id="exampleInputName1"
                            placeholder="커플 닉네임"
                    />
                </div>
                <div class="form-group">
                    <label for="exampleInputEmail3">애칭</label>
                    <div id="nickname-form-group">
                        <input
                                type="text"
                                class="form-control nickname-input"
                                id="exampleInputEmail2"
                                placeholder="여자 애칭"
                        />
                        <input
                                type="text"
                                class="form-control nickname-input"
                                id="exampleInputPassword4"
                                placeholder="남자 애칭"
                        />
                    </div>
                </div>
                <button type="submit" class="btn btn-success">확인</button>
                <button
                        type="button"
                        class="btn btn-light cancel-button"
                        id="cancel-info-edit-btn"
                >
                    취소
                </button>
            </form>
        </div>
    </div>
</div>

<!-- 커플 끊기 모달창 -->
<div id="break-up-modal" class="modal">
    <div class="modal-content">
        <div class="card-body">
            <h4 class="card-title">커플 끊기 신청</h4>
            <p class="card-description">
                커플 끊기 신청 후 회원님에게 40일간 유예기간이 적용되며 유예기간
                동안 회원님은 커플 게시물을 조회할 수 없습니다
            </p>
            <div class="form-group">
                <label for="exampleInputEmail3">사유를 입력하세요</label>
                <input
                        type="email"
                        class="form-control"
                        id="exampleInputEmail3"
                        placeholder="사유"
                />
            </div>
            <button type="submit" class="btn btn-success">확인</button>
            <button
                    type="button"
                    class="btn btn-light cancel-button"
                    id="cancel-break-up-btn"
            >
                취소
            </button>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/content/user/couple-profile.js"></script>


