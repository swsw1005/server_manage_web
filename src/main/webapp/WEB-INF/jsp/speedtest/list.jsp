<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<h4>
    인터넷 속도
</h4>

<c:forEach var="dto" items="${list}">

    <div class="list_row">
        <div class="list_row_item list_row_content">

            <div class="list_row_short">
                    ${dto.sid}
            </div>
            <div class="list_row_name">
                    ${dto.speedTestServerDto.host}
            </div>
            <div class="list_row_name">
                    ${dto.speedTestServerDto.name}
            </div>
            <div class="list_row_name">
                    ${dto.speedTestServerDto.country}
            </div>
            <div class="list_row_name">
                    ${dto.speedTestServerDto.latitude} / ${dto.speedTestServerDto.longitude}
            </div>

            <div class="list_row_500">
                    down / up / ping : ${dto.downloadSpeed} / ${dto.uploadSpeed} / ${dto.ping}
            </div>

            <div class="list_row_date">
                c: ${dto.created}
            </div>

        </div>
    </div>
</c:forEach>