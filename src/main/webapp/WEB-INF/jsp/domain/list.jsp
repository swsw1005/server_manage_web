<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<h4>
    domain 관리
</h4>

<c:forEach var="dto" items="${list}">

    <div class="list_row">
        <div class="list_row_item list_row_content_">
                <%--            <div>--%>
                <%--                    ${dto.sid}--%>
                <%--            </div>--%>
            <div>
                    ${dto.domain}
            </div>

            <div class="list_row_date">
                    ${dto.created }
            </div>

        </div>

        <div class="list_row_item list_row_del" onclick="deleteListItem('${dto.sid}', '${contextPath}/api/v1/domain')">
            <i class="fas fa-trash-alt"></i>
        </div>
    </div>
</c:forEach>