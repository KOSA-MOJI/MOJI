<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<security:authorize access="isAuthenticated()">
    <security:authentication property="principal" var="principal"/>
</security:authorize>
<div class="py-4">
    <p class="clearfix">
        <span class="float-start"> 커플 상태 </span>
        <span class="float-end text-muted" id="couple-stat">
            <c:choose>
                <c:when test="${principal.coupleId eq '1'}">커플</c:when>
                <c:when test="${principal.coupleId eq '0'}">솔로</c:when>
            </c:choose>
        </span>
    </p>
    <p class="clearfix">
        <span class="float-start"> email </span>
        <span class="float-end text-muted"
              id="email"> ${principal.email} </span>
    </p>
    <p class="clearfix">
        <span class="float-start"> 생일 </span>
        <span class="float-end text-muted">
            ${principal.birthday}
        </span>
    </p>
    <p class="clearfix">
        <span class="float-start"> 성별 </span>
        <span class="float-end text-muted">
            <c:choose>
                <c:when test="${principal.gender eq 'M'}">남자</c:when>
                <c:when test="${principal.gender eq 'F'}">여자</c:when>
            </c:choose>
        </span>
    </p>
    <p class="clearfix">
        <span class="float-start"> 가입일 </span>
        <span class="float-end text-muted">
            <tags:formatLocalDate
                    date="${principal.createdAt}"
                    pattern="yyyy년 MM월 dd일"/>
        </span>
    </p>
</div>