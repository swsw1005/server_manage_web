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

<div class="list_row">

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



        <div class="modal-form-half-row border-top">
            <div>
                IP 주소
            </div>
            <div>
                ${serverInfo.ipAddress}
            </div>
        </div>
        <div class="modal-form-half-row">
            <div>
                게이트웨이
            </div>
            <div>
                ${serverInfo.gateway}
            </div>
        </div>
        <div class="modal-form-half-row">
            <div>
                서브넷마스크
            </div>
            <div>
                ${serverInfo.subnetMask}
            </div>
        </div>
        <div class="modal-form-half-row">
            <div>
                MAC 주소
            </div>
            <div>
                ${serverInfo.mac}
            </div>
        </div>


        <div class="modal-form-23-row border-top">
            <div>
                System OS
            </div>
            <div>
                ${serverInfo.os}
            </div>
        </div>
        <div class="modal-form-1-row">
            ${serverInfo.kernel}
        </div>

        <div class="modal-form-half-row border-top">
            메모리
        </div>
        <div class="modal-form-2323-row">
            <div class="flex-center">
                avail
            </div>
            <div class="flex-right">
                ${serverInfo.availableMemStr}
            </div>
            <div class="flex-center">
                free
            </div>
            <div class="flex-right">
                ${serverInfo.freeMemStr}
            </div>
        </div>

        <div class="modal-form-2323-row">
            <div class="flex-center">
                swap
            </div>
            <div class="flex-right">
                ${serverInfo.swapMemStr}
            </div>
            <div class="flex-center">
                total
            </div>
            <div class="flex-right">
                ${serverInfo.totalMemStr}
            </div>
        </div>


        <div class="modal-form-half-row border-top">
            CPU
        </div>

        <c:forEach var="dto" items="${serverInfo.cpuList}" varStatus="idx">

            <div class="modal-form-1-row">
                [${idx.index}] ${dto.name}
            </div>
            <div class="modal-form-11-row">
                <div class="flex-center">
                        ${dto.core} c ${dto.thread} th
                </div>
                <div class="flex-right">
                        ${dto.clock}
                </div>
            </div>

        </c:forEach>


    </div>

</div>
