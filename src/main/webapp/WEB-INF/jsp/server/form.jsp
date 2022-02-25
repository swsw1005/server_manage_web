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
                server 등록
            </h3>
        </div>

        <div class="modal-form-row">
            <div>
                서버 이름
            </div>
            <input type="text" name="name">
        </div>

        <div class="modal-form-row">
            <div>
                IP
            </div>
            <input type="text" name="ip">
        </div>

        <div class="modal-form-row">
            <div>
                 ssh id
            </div>
            <input type="text" name="id">
        </div>

        <div class="modal-form-row">
            <div>
                ssh password
            </div>
            <input type="password" name="password">
        </div>

        <div class="modal-form-row">
            <div>
                ssh 내부 port
            </div>
            <input type="number" name="innerSSHPort">
        </div>

        <div class="modal-form-row">
            <div>
                ssh 외부 port
            </div>
            <input type="number" name="outerSSHPort">
        </div>

    </form>

    <div class="modal-form-row" style="text-align: center;">
        <button type="button" class="w3-button w3-round w3-green"
                onclick="submitFormAjax('simple-form', 'simple-modal-form', '${contextPath}/api/v1/server')">
            저장
        </button>
        <button type="button" class="w3-button w3-round w3-red" onclick="closeInputModal('simple-modal-form')">
            취소
        </button>
    </div>

</div>

<div class="modal-form-right" onclick="closeInputModal('simple-modal-form')">
</div>
