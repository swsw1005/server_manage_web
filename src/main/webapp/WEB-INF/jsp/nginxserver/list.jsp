<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<h4>
    nginx server 관리
</h4>

<c:forEach var="dto" items="${list}">

    <div class="list_row">
        <div class="list_row_item list_row_content">
                <%--            <div>--%>
                <%--                    ${dto.sid}--%>
                <%--            </div>--%>
            <div class="list_row_name">
                    ${dto.name}
            </div>
            <div class="list_row_name">
                    ${dto.domainEntity.domain}
            </div>
            <div class="list_row_name">
                    ${dto.faviconEntity.path}
            </div>
            <div class="list_row_ip">
                <c:if test="${dto.webServerEntity.https}">
                    https://${dto.webServerEntity.serverInfoEntity.ip}:${dto.webServerEntity.port}
                </c:if>
                <c:if test="${!dto.webServerEntity.https}">
                    http://${dto.webServerEntity.serverInfoEntity.ip}:${dto.webServerEntity.port}
                </c:if>
            </div>

            <div class="list_row_date">
                c: ${dto.created }
            </div>
            <div class="list_row_date">
                u: ${dto.updated }
            </div>
            <div class="list_row_date">
                d: ${dto.deleted }
            </div>

        </div>

        <div class="list_row_item list_row_del" onclick="deleteListItem('${dto.sid}', '${contextPath}/api/v1/nginxserver')">
            <i class="fas fa-trash-alt"></i>
        </div>
    </div>
</c:forEach>