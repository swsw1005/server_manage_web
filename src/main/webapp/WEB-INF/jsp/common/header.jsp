<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<head>
    <%--    <link href="static/css/simple-v1.css" rel="stylesheet" type="text/css"/>--%>
    <link href="${contextPath }/static/fontawesome/v5.15/css/all.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath }/static/css/w3.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath }/static/css/server_manage.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath }/static/notification.js/notifications.css" rel="stylesheet" type="text/css"/>

    <script src="${contextPath }/static/script/jquery-3.6.0.min.js"></script>
    <script src="${contextPath }/static/notification.js/notifications.js"></script>
</head>

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

                <a href="${contextPath }/database/home" class="nav_row">
                    DB 관리
                </a>

                <a href="${contextPath }/webserver/home" class="nav_row">
                    웹서버 관리
                </a>

                <a href="${contextPath }/nginxserver/home" class="nav_row">
                    nginx 서버 관리
                </a>

                <a href="${contextPath }/nginxpolicy/home" class="nav_row">
                    nginx 정책 관리
                </a>

                <a href="${contextPath }/domain/home" class="nav_row">
                    domain 관리
                </a>

                <a href="${contextPath }/favicon/home" class="nav_row">
                    favicon 관리
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

    function showSideNav() {
        document.querySelector("#nav-side").style.display = "flex";
        document.querySelector("#nav-side").classList.add("navFadeIn");
        document.querySelector("#nav-side").classList.remove("navFadeOut");
        // document.querySelector("#nav-button").setAttribute("data-display", "1");
        // navBarShowFlag = true;
    }

    function hideSideNav() {
        document.querySelector("#nav-side").style.display = "none";
        document.querySelector("#nav-side").classList.add("navFadeOut");
        document.querySelector("#nav-side").classList.remove("navFadeIn");
        // document.querySelector("#nav-button").setAttribute("data-display", "0");
        // navBarShowFlag = false;
    }


    function openInputModal(formId, url) {

        $.ajax({
            type: "get",
            url: "${contextPath }/" + url,
            contentType: false,
            processData: false,
            data: {}
            ,
            success: function (result, status, statusCode) {
                document.getElementById(formId).innerHTML = result;

                document.getElementById(formId).style.display = "flex";
                document.getElementById(formId).classList.add("modalFadeIn");
                document.getElementById(formId).classList.remove("modalFadeOut");
            },
            error: function (result, status, statusCode) {
                alert("form 호출 중 ERROR", formId, url);
                console.log(result);
            }
        });

    }

    function closeInputModal(formId) {
        document.getElementById(formId).style.display = "none";
        document.getElementById(formId).classList.add("modalFadeOut");
        document.getElementById(formId).classList.remove("modalFadeIn");
        document.getElementById(formId).innerHTML = "";

        callList();
    }

    function callList() {
        $.ajax({
            type: "get",
            url: "./list",
            contentType: false,
            processData: false,
            data: {}
            ,
            success: function (result, status, statusCode) {
                document.getElementById("listLocation").innerHTML = result;
            },
            error: function (result, status, statusCode) {
                Notification({
                    title: 'error',
                    message: result.responseJSON.error_msg,
                    theme: 'error'
                });
            }
        });
    }

    function submitFormAjax(formId, modalId, URL, successCallBack, errorCallBack) {

        console.log(formId, modalId, URL);

        var submitFormData = new FormData(document.getElementById(formId));

        $.ajax({
            type: "post",
            url: "${contextPath }" + URL,
            contentType: false,
            processData: false,
            data: submitFormData
            ,
            success: function (result, status, statusCode) {
                if (successCallBack == null) {
                    Notification({
                        title: 'success',
                        message: 'Notification Message',
                        theme: 'success'
                    });
                    closeInputModal(modalId);
                } else {
                    successCallBack();
                }
            },
            error: function (result, status, statusCode) {
                console.log(result);
                if (errorCallBack == null) {
                    Notification({
                        title: 'error',
                        message: result.responseJSON.error_msg,
                        theme: 'error'
                    });
                    closeInputModal(modalId);
                } else {
                    errorCallBack();
                }
            }
        });
    }


    const Notification = window.createNotification({
        // options here
        // close on click
        closeOnClick: true,
        // displays close button
        displayCloseButton: false,
        // nfc-top-left
        // nfc-bottom-right
        // nfc-bottom-left
        positionClass: 'nfc-bottom-right',
        // callback
        // onclick: false,
        // timeout in milliseconds
        showDuration: 30000,
        // success, info, warning, error, and none
        // theme: 'success'
    });

    // Notification({
    //     title: 'success',
    //     message: 'Notification Message',
    //     theme: 'success'
    // });

    // Notification({
    //     title: 'info',
    //     message: 'Notification Message',
    //     theme: 'info'
    // });

    // Notification({
    //     title: 'warning',
    //     message: 'Notification Message',
    //     theme: 'warning'
    // });

    // Notification({
    //     title: 'error',
    //     message: 'Notification Message',
    //     theme: 'error'
    // });

    setTimeout(() => {
        callList();
    }, 736);

</script>

