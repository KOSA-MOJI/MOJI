<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2024-09-21
  Time: 오후 7:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <span class="float-end text-muted"> OOO님과 커플 </span>
    </p>
    <p class="clearfix">
        <span class="float-start"> 전화번호 </span>
        <span class="float-end text-muted"> +821012345678 </span>
    </p>
    <p class="clearfix">
        <span class="float-start"> email </span>
        <span class="float-end text-muted"> example@gmail.com </span>
    </p>
</div>