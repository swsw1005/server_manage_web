<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<nav>
    <div id="nav-side" class="nav-side">

        <div class="middle-wrapper">

            <div class="header">
                <div class="user_info">
                    임성우 님
                </div>
                <button type="button" onclick="hideSideNav()">
                    <i class="fas fa-times"></i>
                </button>
            </div>

            <div class="body">

                <a href="${contextPath }/nginxpolicy/home" class="nav_row">
                    nginx 정책 관리
                </a>

                <a href="${contextPath }/nginxserver/home" class="nav_row">
                    nginx 서버 관리
                </a>

                <a href="${contextPath }/database/home" class="nav_row">
                    DB 관리
                </a>

                <a href="${contextPath }/webserver/home" class="nav_row">
                    웹서버 관리
                </a>

                <a href="${contextPath }/server/home" class="nav_row">
                    서버 관리
                </a>

                <a href="${contextPath }/domain/home" class="nav_row">
                    domain 관리
                </a>

                <a href="${contextPath }/favicon/home" class="nav_row">
                    favicon 관리
                </a>
                <a href="${contextPath }/adminlog/home" class="nav_row">
                    관리자 로그
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

