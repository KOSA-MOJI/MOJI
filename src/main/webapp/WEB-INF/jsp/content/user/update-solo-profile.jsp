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
      href="${pageContext.request.contextPath}/css/content/user/update-profile-style.css"/>

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
            <h4>${principal.userName}</h4>
            <form>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
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
                            <input
                                    type="file"
                                    class="pfp-file-input"
                                    id="solo-pfp-file-input"
                                    style="display: none"
                                    accept="image/*"
                            />
                        </div>
                        <i class="bi bi-camera-fill"></i>
                    </div>
                </div>
                <jsp:include page="/WEB-INF/jsp/component/user-info.jsp"/>
                <div class="btn-container d-flex justify-content-between">
                    <button type="submit"
                            class="btn btn-success"
                            id="save-profile-update-btn">
                        확인
                    </button>
                    <button
                            type="button"
                            class="btn btn-light cancel-button"
                            id="cancel-check-btn">
                        취소
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
</div>
<!-- js -->
<script src="${pageContext.request.contextPath}/js/content/user/update-solo-profile.js"></script>


