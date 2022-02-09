<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="modal-form-head" onclick="closeInputModal('simple-modal-form')">
</div>

<div class="modal-form-left" onclick="closeInputModal('simple-modal-form')">
</div>

<div class="modal-form-body">

    <form id="simple-form">
        <div class="modal-form-row">
            <h3>
                nginx server 등록
            </h3>
        </div>

        <div class="modal-form-row">
            <div>
                이름
            </div>
            <input type="text" name="name">
        </div>

        <div class="modal-form-row">
            <div>
                <label for="https_true" class="w3-tooltip">
                    로그 분리
                </label>
                <input type="radio" name="seperateLog" value="true" id="https_true" class="w3-radio" checked>
                <br/>(해당 도메인명 아래 별도 폴더에 표시됩니다.)
            </div>
            <div>
                <label for="https_false" class="w3-tooltip">
                    로그 통합
                </label>
                <input type="radio" name="seperateLog" value="false" id="https_false" class="w3-radio">
                <br/>(기본 nginx 로그에 함께 표시됩니다.)
            </div>
        </div>


        <div class="modal-form-row">
            <div>
                domain
            </div>
            <select name="domainInfoSid" class="w3-select">
                <c:forEach var="dto" items="${domainList}">
                    <option value="${dto.sid}">
                            ${dto.domain}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="modal-form-row">
            <div>
                favicon
            </div>
            <select name="faviconInfoSid" class="w3-select">
                <c:forEach var="dto" items="${faviconList}">
                    <option value="${dto.sid}">
                            ${dto.path}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="modal-form-row">
            <div>
                web server
            </div>
            <select name="webServerInfoSid" class="w3-select">
                <c:forEach var="dto" items="${webServerList}">
                    <option value="${dto.sid}">
                            ${dto.name} | ${dto.ip}:${dto.port}
                    </option>
                </c:forEach>
            </select>
        </div>
    </form>

    <div class="modal-form-row" style="text-align: center;">
        <button type="button" class="w3-button w3-round w3-green"
                onclick="submitFormAjax('simple-form', 'simple-modal-form', '${contextPath }/api/v1/nginxserver')">
            저장
        </button>
        <button type="button" class="w3-button w3-round w3-red" onclick="closeInputModal('simple-modal-form')">
            취소
        </button>
    </div>

</div>

<div class="modal-form-right" onclick="closeInputModal('simple-modal-form')">
</div>
