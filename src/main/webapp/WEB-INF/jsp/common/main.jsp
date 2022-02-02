<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
</head>

<body>

<jsp:include page="header.jsp"/>

<div id="content">

</div>

<jsp:include page="footer.jsp"/>

<script>
    function loadMainPage() {
        $.ajax({
            type: "get",
            url: "${contextPath }${mainPageUrl}",
            contentType: false,
            processData: false,
            data: {}
            ,
            success: function (result, status, statusCode) {
                document.getElementById("content").innerHTML = result;
            },
            error: function (result, status, statusCode) {
                //alert("댓글을 등록하는 중 오류가 발생되었습니다.");
                console.log(result);
            }
        });
    }

    setInterval(
        loadMainPage(), 500
    );

</script>

</body>

</html>