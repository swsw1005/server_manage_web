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

        <input type="hidden" name="nginxServerSidString" id="nginxServerSidString" value="${nginxServerSidString}"/>
        <input type="hidden" name="sid" value="${nginxPolicy.sid}"/>

        <div class="modal-form-row form-row-horizontal">
            <h3>
                nginx 정책 등록
            </h3>

            <c:if test="${!insert}">
                <div style="width: 100%;">
                    <button type="button" onclick="adjustNginxSetting('${nginxPolicy.sid}', '${contextPath}/api/v1/nginxpolicy')"
                            style="float: right; margin: 10px 10px 0px 0px; background-color: darkred;">
                        적용
                    </button>
                </div>
            </c:if>

        </div>

        <div class="modal-form-row">
            <div>
                이름 ${insert}
            </div>
            <c:if test="${insert}">
                <input type="text" name="name" value="">
            </c:if>
            <c:if test="${!insert}">
                <input type="text" name="name" value="${nginxPolicy.name}">
            </c:if>

        </div>

        <div class="modal-form-row form-row-horizontal">
            <div>
                worker 수
            </div>
            <c:if test="${insert}">
                <input type="number" name="workerProcessed" value="8">
            </c:if>
            <c:if test="${!insert}">
                <input type="number" name="workerProcessed" value="${nginxPolicy.workerProcessed}">
            </c:if>
        </div>

        <div class="modal-form-row form-row-horizontal">
            <div>
                worker 당 커넥션 수
            </div>
            <c:if test="${insert}">
                <input type="number" name="workerConnections" value="512">
            </c:if>
            <c:if test="${!insert}">
                <input type="number" name="workerConnections" value="${nginxPolicy.workerConnections}">
            </c:if>

        </div>

        <div id="nginx-server-selected-location" class="modal-form-row"
             style="border: 1px solid white; background: #3a3a3a">

        </div>

        <div class="modal-form-row">
            <div>
                nginx server
            </div>
            <select id="nginxServerSelector" class="w3-select" style="font-size: 0.6em;"
                    onchange="insertNewNginxServer()">
                <c:forEach var="dto" items="${nginxServerList}">
                    <option value="${dto.sid}"
                            data-name="${dto.name}"
                            data-domain="${dto.domainEntity.domain}"
                            data-ip="${dto.webServerEntity.ip}"
                            data-port="${dto.webServerEntity.port}"
                            data-favicon="${dto.faviconEntity.path}"
                            data-log="${dto.seperateLog}"
                            data-https="${dto.webServerEntity.https}"
                    >
                            ${dto.name}
                        | ${dto.domainEntity.domain}
                        | ${dto.webServerEntity.ip}
                        | ${dto.webServerEntity.port}
                    </option>
                </c:forEach>
            </select>
        </div>


    </form>

    <div class="modal-form-row" style="text-align: center;">

        <c:if test="${insert}">
            <button type="button" class="w3-button w3-round w3-green"
                    onclick="submitFormAjax('simple-form', 'simple-modal-form', '${contextPath }/api/v1/nginxpolicy')">
                저장
            </button>
        </c:if>

        <c:if test="${!insert}">
            <button type="button" class="w3-button w3-round w3-green"
                    onclick="submitFormAjax('simple-form', 'simple-modal-form', '${contextPath }/api/v1/nginxpolicyUpdate')">
                수정
            </button>
        </c:if>

        <button type="button" class="w3-button w3-round w3-red" onclick="closeInputModal('simple-modal-form')">
            취소
        </button>
    </div>

</div>
