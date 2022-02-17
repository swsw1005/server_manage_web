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

    <div class="modal-form-row">
        <div>
            이름
        </div>
        <input type="text" name="name" value="${nginxPolicy.name}">

    </div>

    <div class="modal-form-row form-row-horizontal">
        <div>
            worker 수
        </div>
        <input type="number" name="workerProcessed" value="${nginxPolicy.workerProcessed}">
    </div>

    <div class="modal-form-row form-row-horizontal">
        <div>
            worker 당 커넥션 수
        </div>
        <input type="number" name="workerConnections" value="${nginxPolicy.workerConnections}">
    </div>
      

    <div class="modal-form-row">
        <div>
            ROOT 도메인
        </div>
        <select name="domainInfoSid" class="w3-select">
            <option value="null">
                ----- 선택 -----
            </option>
            <c:forEach var="dto" items="${domainList}">

              <c:if test="${ dto.sid eq nginxPolicy.domainEntity.sid }">
                <option value="${dto.sid}" selected>
                  ${dto.domain}
                </option>
              </c:if>
              <c:if test="${ dto.sid ne nginxPolicy.domainEntity.sid }">
                <option value="${dto.sid}">
                  ${dto.domain}
                </option>
              </c:if>
            
            </c:forEach>
        </select>
    </div>


    <div class="modal-form-row">
        <div>
            nginx server
        </div>
    </div>

    <div id="nginx-server-location" class="modal-form-row"
        style="border: 1px solid white; background: #3a3a3a">
       
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
        onclick="submitFormAjax_NginxPolicy('simple-form', 'simple-modal-form', '${contextPath }/api/v1/nginxpolicyUpdate')">
        저장
    </button>

    <button type="button" class="w3-button w3-round w3-red" onclick="closeInputModal('simple-modal-form')">
        취소
    </button>
</div>