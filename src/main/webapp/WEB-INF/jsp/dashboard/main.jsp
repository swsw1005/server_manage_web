<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<h4>
    서버 관리 페이지
</h4>

<style>

    .border-top {
        padding: 5px 0px;
    }

</style>

<div id="listLocation" class="list_row static">

    <div style="width: 100%; padding: 5px 0px;">

        <div class="modal-form-half-row border-top">
            <div>
                공인 IP 주소
            </div>
            <div>
                ${publicIpInfo.ip} | ${ip}
            </div>
        </div>
        <div class="modal-form-half-row">
            <div>
                지역
            </div>
            <div>
                ${publicIpInfo.city} (${publicIpInfo.region}) / ${publicIpInfo.country}
            </div>
        </div>
        <div class="modal-form-half-row">
            <div>
                timezone
            </div>
            <div>
                ${publicIpInfo.timezone}
            </div>
        </div>
        <div class="modal-form-half-row">
            <div>
                postal
            </div>
            <div>
                ${publicIpInfo.postal}
            </div>
        </div>
        <div class="modal-form-half-row">
            <div>
                loc
            </div>
            <div>
                ${publicIpInfo.loc}
            </div>
        </div>
        <div class="modal-form-half-row">
            <div>
                org
            </div>
            <div>
                ${publicIpInfo.org}
            </div>
        </div>


        <div class="modal-form-1-row border-top">
            <h5>
                인증서 유효기간 :: ${leftDay} 일 남음
                <i class="fas fa-question-circle" title="${certMessage}"></i>
            </h5>
        </div>
        <div class="modal-form-1-row">
            ${startDate} ~ ${endDate}
        </div>


        <div class="modal-form-half-row border-top">
            <div>
                uptime
            </div>
            <div>
                ${serverInfo.uptime}
            </div>
        </div>
        <div class="modal-form-half-row">
            <div>
                users
            </div>
            <div>
                ${serverInfo.users}
            </div>
        </div>
        <div class="modal-form-half-row">
            <div>
                load average
            </div>
            <div>
                ${serverInfo.loadAverage}
            </div>
        </div>


        <div class="modal-form-half-row border-top">
            <h3>
                Cpu
            </h3>
        </div>
        <c:forEach var="dto" items="${serverInfo.cpuInfos}" varStatus="idx">
            <h5 class="modal-form-1-row">
                [${idx.index}] ${dto.cpuName}
            </h5>
            <div class="modal-form-1111-row">
                <div>
                        ${dto.core} c ${dto.thread} th
                </div>
                <div>
                        ${dto.cpuMhz}
                </div>
                <div>
                        ${dto.cpuCache}
                </div>
            </div>
        </c:forEach>


        <div class="modal-form-half-row border-top">
            <h3>
                Network
            </h3>
        </div>
        <c:forEach var="dto" items="${serverInfo.networkInterfaceInfos}" varStatus="idx">
            <div class="modal-form-11-row">
                <h5>
                    [${dto.idx}] ${dto.name}
                </h5>
                <div>
                        ${dto.displayName}
                </div>
            </div>

            <c:forEach var="dto2" items="${dto.interfaceAddressInfos}">
                <div class="modal-form-11-row">
                    <div>
                            ${dto2.address} / ${dto2.maskLength}
                    </div>
                    <div>
                            ${dto2.broadcast}
                    </div>
                </div>
            </c:forEach>


        </c:forEach>


        <div class="modal-form-half-row border-top">
            <h3>
                File System
            </h3>
        </div>
        <div class="modal-form-1-row">
            <div>
                fileSystem
            </div>
        </div>
        <div class="modal-form-1111-row">
            <div>
            </div>
            <div>
                used
            </div>
            <div>
                avail
            </div>
            <div>
                percent
            </div>
        </div>
        <div class="modal-form-1-row">
            <div>
                mounted
            </div>
        </div>


        <c:forEach var="dto" items="${fileList}" varStatus="idx">

            <div class="modal-form-1-row">
                <h5>
                    [${idx.index}] ${dto.fileSystem}
                </h5>
            </div>
            <div class="modal-form-1111-row">
                <div>
                </div>
                <div>
                        ${dto.used}
                </div>
                <div>
                        ${dto.avail}
                </div>
                <div>
                        ${dto.percent}
                </div>
            </div>
            <div class="modal-form-1-row">
                <div title="${dto.mounted}" style="overflow-x: hidden;">
                        ${dto.mounted}
                </div>
            </div>
        </c:forEach>


    </div>

</div>
