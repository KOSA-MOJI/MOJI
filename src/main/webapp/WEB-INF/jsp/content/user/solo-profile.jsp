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
<link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
        crossorigin="anonymous"
/>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/content/user/profile-modal-style.css"/>
<link
        rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css"
/>

<!-- jsp -->
<div class="main-panel">
    <div class="content-wrapper">
        <div class="page-header">
            <h3 class="page-title">Profile</h3>
        </div>
        <div class="profile-card">
            <h4>${principal.userName}</h4>
            <!-- 정보 -->
            <div class="profile-content border-bottom text-center pb-4">
                <div class="pfp-background">
                    <div class="pfp-container">
                        <img
                                class="pfp"
                                id="solo-pfp"
                                src="${principal.profileImageUrl}"
                                alt="남자의 프로필 사진"
                                onerror="this.src='${pageContext.request.contextPath}/image/content/user/default-avatar.avif'"
                        />
                    </div>
                </div>
                <div class="btn-container d-flex justify-content-between">
                    <button class="btn btn-success btn-lg" id="couple-invitation-btn">
                        이메일로 커플 신청
                    </button>
                    <button class="btn btn-success btn-lg" id="check-invitation-btn">
                        커플 신청 확인
                    </button>
                </div>
            </div>
            <jsp:include page="/WEB-INF/jsp/component/user-info.jsp"/>
            <p class="clearfix">
                <span class="float-start"> 정보 변경 </span>
                <span class="float-end text-muted">
        <a href="update-profile">변경 </a>
        </span>
            </p>
        </div>
    </div>

    <!-- 커플신청 확인 모n창 -->
    <div class="modal" id="check-invitation-modal">
        <div class="modal-content">
            <div class="card-body">
                <h4 class="card-title">커플 신청 확인</h4>
                <p class="card-description">
                    000으로부터 커플신청이 왔습니다. 수락하시겠습니까?
                </p>
                <form class="forms-sample">
                    <div class="profile-content text-center pb-4">
                        <div class="pfp-background">
                            <div class="pfp-container">
                                <img
                                        class="pfp"
                                        src="${pageContext.request.contextPath}/image/content/user/man.jpg"
                                        alt="남자의 프로필 사진"
                                />
                            </div>
                        </div>
                        <p>안녕하세요 인도사람 입니다</p>
                        <div class="btn-container d-flex justify-content-between">
                            <button type="submit" class="btn btn-success">확인</button>
                            <button
                                    type="button"
                                    class="btn btn-light cancel-button"
                                    id="cancel-check-btn"
                            >
                                취소
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- 이메일로 커플 신청 모달창 -->
    <div id="couple-invitation-modal" class="modal">
        <div class="modal-content">
            <div class="card-body">
                <h4 class="card-title">이메일로 커플 신청</h4>
                <p class="card-description">
                    상대에게 내 이메일을 복사해주거나 이메일로 커플 신청을 보내세요
                </p>
                <div class="form-group">
                    <label for="exampleInputEmail3">나의 이메일</label>
                    <input
                            type="email"
                            class="form-control"
                            id="exampleInputEmail3"
                            placeholder="Email"
                            value="${principal.email}"
                            readonly
                    />
                </div>
                <div class="form-group">
                    <label for="email-input">이메일을 입력하세요</label>
                    <input
                            type="email"
                            class="form-control"
                            id="email-input"
                            placeholder="Email"
                    />
                </div>
                <button type="submit" class="btn btn-success" id="request-confirm-btn">확인
                </button>
                <button
                        type="button"
                        class="btn btn-light cancel-button"
                        id="cancel-invitation-btn"
                >
                    취소
                </button>

            </div>
        </div>
    </div>

    <!-- js -->
    <script src="${pageContext.request.contextPath}/js/content/user/solo-profile.js"></script>


