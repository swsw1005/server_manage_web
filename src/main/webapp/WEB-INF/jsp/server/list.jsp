<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<h4>
    server 관리
</h4>

<c:forEach var="dto" items="${list}">

    <div class="list_row">
        <div class="list_row_item list_row_content" onclick="openInputModal('simple-modal-form', '${contextPath}/server/detail/${dto.sid}')">
                <%--            <div>--%>
                <%--                    ${dto.sid}--%>
                <%--            </div>--%>

            <div class="list_row_name">
                    ${dto.ip}
            </div>

            <div class="list_row_name" style="font-size: 1.2em;">
                    ${dto.name}
            </div>

            <div class="list_row_name">
                    ${dto.id}
            </div>
            <div class="list_row_name">
                    ${dto.sshPort}
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

        <div class="list_row_item list_row_del"
             onclick="deleteListItem('${dto.sid}', '${contextPath}/api/v1/server')">
            <i class="fas fa-trash-alt"></i>
        </div>
    </div>
</c:forEach>