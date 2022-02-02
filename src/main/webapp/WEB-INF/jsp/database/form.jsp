<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<div class="modal-form-head" onclick="closeInputModal('simple-modal-form')">
</div>

<div class="modal-form-body">

    <form id="simple-form">
        <div class="modal-form-row">
            <h3>
                database 등록
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
                IP
            </div>
            <input type="text" name="ip">
        </div>

        <div class="modal-form-row">
            <div>
                port
            </div>
            <input type="text" name="port">
        </div>

        <div class="modal-form-row">
            <div>
                id
            </div>
            <input type="text" name="id">
        </div>

        <div class="modal-form-row">
            <div>
                password
            </div>
            <input type="password" name="password">
        </div>

        <div class="modal-form-row">
            <div>
                DB 타입
            </div>
            <select name="dbType" class="w3-select">
                <c:forEach var="dto" items="${dbTypes}">
                    <option value="${dto}">
                            ${dto}
                    </option>
                </c:forEach>
            </select>
        </div>

    </form>

    <div class="modal-form-row" style="text-align: center;">
        <button type="button" class="w3-button w3-round w3-green"
                onclick="submitFormAjax('simple-form', 'simple-modal-form', '${contextPath }/api/v1/database')">
            저장
        </button>
        <button type="button" class="w3-button w3-round w3-red" onclick="closeInputModal('simple-modal-form')">
            취소
        </button>
    </div>

</div>

