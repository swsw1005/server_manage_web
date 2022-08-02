<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<h4>
    nginx 정책 관리
</h4>


<form id="simple-form">

    <input type="hidden" name="nginxServerSidString" id="nginxServerSidString"
        value="${nginxServerSidString}" />
    <input type="hidden" name="sid" value="${nginxPolicy.sid}" />

    <div class="modal-form-row form-row-horizontal">
        <div>
            worker
        </div>
        <input type="number" name="workerProcessed" value="${nginxPolicy.workerProcessed}">
    </div>

    <div class="modal-form-row form-row-horizontal">
        <div>
            worker/conn
        </div>
        <input type="number" name="workerConnections" value="${nginxPolicy.workerConnections}">
    </div>

    <div class="modal-form-row">
        <div>
            nginx server
        </div>
    </div>

    <div id="nginx-server-location" class="modal-form-row">
       
            <c:forEach var="dto" items="${nginxServerList}">
                <div class="nginx-server-wrapper">
                    <div class="nginx-server-chk-wrapper">
                        <c:if test="${dto.selected}">
                            <input type="checkbox" class="nginx-server-chkbox" value="${dto.sid}" id="nginx-server-chk=${dto.sid}" style="width: 30px !important; height: 30px !important;" checked>
                        </c:if>
                        <c:if test="${!dto.selected}">
                            <input type="checkbox" class="nginx-server-chkbox" value="${dto.sid}" id="nginx-server-chk=${dto.sid}" style="width: 30px !important; height: 30px !important;">
                        </c:if>
                    </div>

                    <label class="nginx-server-label-wrapper" for="nginx-server-chk=${dto.sid}">
                        <div>
                            ${dto.name}
                            | ${dto.domainEntity.domain}
                        </div>
                        <div>
                            <c:if test="${dto.seperateLog}">
                            로그 분리
                            </c:if>
                            <c:if test="${!dto.seperateLog}">
                            로그 통합
                            </c:if>
                            | ${dto.faviconEntity.path}
                        </div>
                        <div>
                            <c:if test="${dto.webServerEntity.https}">
                                https://${dto.webServerEntity.serverInfoEntity.ip}:${dto.webServerEntity.port}
                            </c:if>
                            <c:if test="${!dto.webServerEntity.https}">
                                http://${dto.webServerEntity.serverInfoEntity.ip}:${dto.webServerEntity.port}
                            </c:if>
                        </div>
                    </label>

                </div>
            </c:forEach>


    </div>

</form>

<div class="modal-form-row" style="text-align: center;">

    <button type="button" class="w3-button w3-round w3-green"
        onclick="submitFormAjax_NginxPolicy('simple-form', 'simple-modal-form', '${contextPath}/api/v1/nginxpolicyUpdate')">
        저장
    </button>

    <button type="button" class="w3-button w3-round w3-red" onclick="closeInputModal('simple-modal-form')">
        취소
    </button>
</div>