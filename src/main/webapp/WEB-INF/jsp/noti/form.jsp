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
                알림 채널 등록
            </h3>
        </div>

        <div class="modal-form-row">
            <div>
                이름
            </div>
            <input type="text" name="name">
        </div>

        <div class="modal-form-half-row">
            <div>
                알림 타입
            </div>
            <select name="notiType" class="w3-select" id="notiTypeSelect" onchange="notiFormTypeSelect()">
                <option value="null">
                    ----- 선택 -----
                </option>
                <option value="NATEON">
                    네이트온 팀룸
                </option>
                <option value="SLACK">
                    Slack
                </option>
            </select>
        </div>

        <div class="modal-form-row" id="notiColumn1" style="display: none;">
            <div>
                column1
            </div>
            <input type="text" name="column1">
        </div>

        <div class="modal-form-row" id="notiColumn2" style="display: none;">
            <div>
                column2
            </div>
            <input type="text" name="column2">
        </div>

    </form>

    <div class="modal-form-row" style="text-align: center;">
        <button type="button" class="w3-button w3-round w3-green"
                onclick="submitFormAjax('simple-form', 'simple-modal-form', '${contextPath}/api/v1/noti', 'post')">
            저장
        </button>
        <button type="button" class="w3-button w3-round w3-red" onclick="closeInputModal('simple-modal-form')">
            취소
        </button>
    </div>

</div>

<div class="modal-form-right" onclick="closeInputModal('simple-modal-form')">
</div>
