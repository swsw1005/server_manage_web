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
                webserver 등록
            </h3>
        </div>

        <div class="modal-form-row">
            <div>
                이름
            </div>
            <input type="text" name="name">
        </div>

        <div class="modal-form-row">
            <label for="https_false" class="w3-tooltip">
                http
            </label>
            <input type="radio" name="https" value="false" id="https_false" class="w3-radio" checked>

            <label for="https_true" class="w3-tooltip">
                https
            </label>
            <input type="radio" name="https" value="true" id="https_true" class="w3-radio">
        </div>

        <div class="modal-form-row">
            <div>
                서버
            </div>
            <select name="serverInfoSid" class="w3-select">
                <option value="null">
                    ----- 선택 -----
                </option>
                <c:forEach var="dto" items="${serverList}">
                    <option value="${dto.sid}">
                            ${dto.ip} / ${dto.name}
                        <c:if test="${!dto.alive}">
                            &nbsp;&nbsp;&nbsp;&nbsp;(삭제됨)
                        </c:if>
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="modal-form-row">
            <div>
                Port
            </div>
            <input type="text" name="port">
        </div>
    </form>

    <div class="modal-form-row" style="text-align: center;">
        <button type="button" class="w3-button w3-round w3-green"
                onclick="submitFormAjax('simple-form', 'simple-modal-form', '${contextPath}/api/v1/webserver', 'post')">
            저장
        </button>
        <button type="button" class="w3-button w3-round w3-red" onclick="closeInputModal('simple-modal-form')">
            취소
        </button>
    </div>

</div>

<div class="modal-form-right" onclick="closeInputModal('simple-modal-form')">
</div>
