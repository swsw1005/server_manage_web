<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div id="insert-button" data-display="0" onclick="adjustNginxSetting('simple-form', '${contextPath}/api/v1/nginxpolicy')">
  <i class="fas fa-sitemap"></i>
</div> 

<!-- <div id="simple-modal-form" class="simple-modal-form" style="display: none;">

</div> -->

<div id="listLocation" class="d-grid justify-content-center">
  <div class="spinner-border" role="status">
    <span class="visually-hidden">Loading...</span>
  </div>
</div>

