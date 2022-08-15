<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<nav>
    <div id="nav-side" class="nav-side navFadeIn">

        <div class="middle-wrapper">

            <div class="header">
                <div class="user_info">
                    ${sessionScope.admin.name} 님
                </div>
                <button id="navHideButton" type="button" onclick="hideSideNav()">
                    <i class="fas fa-times"></i>
                </button>
            </div>

            <div class="body">

                <a href="${contextPath}/dashboard" class="nav_row">
                    대시보드
                </a>

                <a href="${contextPath}/nginxpolicy/home" class="nav_row">
                    nginx 정책 관리
                </a>

                <a href="${contextPath}/nginxserver/home" class="nav_row">
                    nginx 서버 관리
                </a>

                <a href="${contextPath}/database/home" class="nav_row">
                    DB 관리
                </a>

                <a href="${contextPath}/webserver/home" class="nav_row">
                    웹서버 관리
                </a>

                <a href="${contextPath}/server/home" class="nav_row">
                    서버 관리
                </a>

                <a href="${contextPath}/domain/home" class="nav_row">
                    domain 관리
                </a>

                <a href="${contextPath}/favicon/home" class="nav_row">
                    favicon 관리
                </a>

                <a href="${contextPath}/speedtest/home" class="nav_row">
                    인터넷 속도
                </a>

                <a href="${contextPath}/noti/home" class="nav_row">
                    알림 설정
                </a>

                <a href="${contextPath}/adminsetting/home" class="nav_row">
                    관리자 설정
                </a>

                <a href="${contextPath}/adminlog/home" class="nav_row">
                    관리자 로그
                </a>

                <a href="${contextPath}/systeminfo/home" class="nav_row">
                    시스템정보
                </a>

            </div>
        </div>

        <div class="middle-void" onclick="hideSideNav()">
        </div>


    </div>

    <div id="nav-button" data-display="0">
        <i class="fas fa-bars"></i>
    </div>
</nav>

<script>

    // let navBarShowFlag = false;

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

    setTimeout(() => {
        callList();
    }, 222);



</script>

