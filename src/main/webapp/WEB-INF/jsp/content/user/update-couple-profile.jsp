<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<security:authorize access="isAuthenticated()">
    <security:authentication property="principal" var="principal"/>
</security:authorize>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<!-- css -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/content/user/profile-style.css"/>

<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/content/user/profile-modal-style.css"/>
<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css"/>


<!-- jsp -->
<div class="main-panel">
    <div class="content-wrapper">
        <div class="page-header">
            <h3 class="page-title">Profile</h3>
        </div>
        <div class="profile-card">
            <input type="text" value="${principal.couple.coupleName}">
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
                    </div>
                    <div class="pfp-background">
                        <div class="pfp-container central-pfp-container" id="couple-pfp-container">
                            <img
                                    class="pfp"
                                    id="couple-pfp"
                                    src="${principal.couple.coupleProfileImage}"
                                    onerror="this.src='/image/content/user/default-avatar.avif'"
                            />
                            <input
                                    type="file"
                                    class="pfp-file-input"
                                    id="couple-pfp-file-input"
                                    style="display: none"
                                    accept="image/*"
                            />
                        </div>
                        <i class="bi bi-camera-fill"></i>
                    </div>
                    <div class="individual pfp-background">
                        <div class="pfp-container">
                            <img
                                    class="pfp"
                                    id="man-pfp"
                                    src="${partnerProfileUrl}"
                                    onerror="this.src='/image/content/user/default-avatar.avif'"
                            />
                        </div>
                    </div>
                </div>
            </div>
            <jsp:include page="/WEB-INF/jsp/component/couple-info.jsp"/>
            <div class="btn-container d-flex justify-content-between">
                <button type="submit"
                        class="btn btn-success"
                        id="save-profile-update-btn">
                    확인
                </button>
                <button
                        type="button"
                        class="btn btn-light cancel-button"
                        id="cancel-check-btn"
                        onclick="window.location.href='/user/couple'"

                >
                    취소
                </button>
            </div>
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
            <form class="forms-sample">
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
            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/content/user/update-couple-profile.js"></script>


