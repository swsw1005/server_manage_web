<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>

    <jsp:include page="resouces.jsp"/>

</head>

<body>

<jsp:include page="header.jsp"/>

<div id="content" class="d-grid justify-content-center">
    <div class="spinner-border" role="status">
        <span class="visually-hidden">Loading...</span>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script>
    function loadMainPage(successCallback) {
        $.ajax({
            type: "get",
            url: "${contextPath}${mainPageUrl}",
            contentType: false,
            processData: false,
            data: {}
            ,
            success: function (result, status, statusCode) {
                document.getElementById("content").innerHTML = result;
                setTimeout(() => {
                    successCallback();
                }, 234);
            },
            error: function (result, status, statusCode) {
                //alert("댓글을 등록하는 중 오류가 발생되었습니다.");
                console.log(result);
            }
        });
    }


    // let navBarShowFlag = false;


    window.onload = function () {
        setTimeout(() => {
            loadMainPage(callList);
        }, 123);

        document.querySelector("#nav-button").addEventListener(
            "click", function (event) {
                var targetElement = event.target.getAttribute("data-display") == 0;
                // if (navBarShowFlag) {
                //     hideSideNav();
                // } else {
                showSideNav();
                // }

            }
        );

    }

</script>

</body>

</html>