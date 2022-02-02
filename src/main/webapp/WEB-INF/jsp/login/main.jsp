<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>login</title>

    <link href="${contextPath }/static/css/simple-v1.css" rel="stylesheet" type="text/css"/>
    <script src="${contextPath }/static/script/jquery-3.6.0.min.js"></script>

    <style>
        #form-body {
            margin: auto;
            max-width: 400px;
        }

        .form-wrapper {
            /*max-width: 500px;*/
        }

        .form-row input {
            margin: 7px;
            width: 100%;
        }

        .form-row-button {
            text-align: center;
        }
    </style>

</head>

<body>

<div id="form-body">

    <form id="login-form" onsubmit="return false;">

        <div class="form-wrapper">
            <div class="form-row">
                <input type="text" name="email" placeholder="email">
            </div>
            <div class="form-row">
                <input type="password" name="password" placeholder="password">
            </div>
            <div class="form-row-button">
                <button type="button" onclick="loadMainPage()" class="">
                    login
                </button>
            </div>

        </div>

    </form>

</div>

<script>
    function loadMainPage() {
        $.ajax({
            type: "post",
            url: "${contextPath }/login",
            contentType: false,
            processData: false,
            data: new FormData(document.getElementById("login-form"))
            ,
            success: function (result, status, statusCode) {
                window.location = "${contextPath }/";
            },
            error: function (result, status, statusCode) {
                alert("loginFail");
            }
        });
    }

    window.onload = function () {
        document.getElementById("login-form").getElementsByTagName("input")[0].focus();
    }
</script>

</body>

</html>