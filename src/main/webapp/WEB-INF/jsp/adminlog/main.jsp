<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div id="insert-button" data-display="0" onclick="sendAdminLogEmail('${contextPath}')">
    <i class="fas fa-plus"></i>
</div>

<div id="simple-modal-form" class="simple-modal-form" style="display: none;">

</div>


<div id="listLocation">
    <i class="fas fa-spinner"></i>
</div>