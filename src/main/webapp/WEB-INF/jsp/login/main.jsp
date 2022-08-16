<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>login</title>

    <jsp:include page="../common/resouces.jsp"/>

    <style>
        #form-body {
            /*margin: auto;*/
            max-width: 400px;
            /*padding: 10px;*/
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

<div id="form-body" class="container-sm">

    <div style="height: 50px;">
    </div>

    <form id="login-form" onsubmit="return false;">

        <div class="mb-3">
            <div class="mb-3">
                <label for="email" class="form-label">email</label>
                <input type="text" class="form-control" id="email" name="email"/>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">password</label>
                <input type="password" class="form-control" id="password" name="password"/>
            </div>

            <div class="d-flex justify-content-evenly">
                <button type="button" class="btn btn-primary" onclick="loadMainPage()">
                    login
                </button>
            </div>

            <div class="mb-3 align-items-end">

            </div>

        </div>

    </form>

</div>

<script>
    function loadMainPage() {
        $.ajax({
            type: "post",
            url: "${contextPath}/login",
            contentType: false,
            processData: false,
            data: new FormData(document.getElementById("login-form"))
            ,
            success: function (result, status, statusCode) {
                window.location = "${contextPath}/";
            },
            error: function (result, status, statusCode) {
                Notify(
                    'error',
                    'fail login',
                    'error'
                );
            }
        });
    }

    window.onload = function () {
        document.getElementById("login-form").getElementsByTagName("input")[0].focus();
    }
</script>

</body>

</html>