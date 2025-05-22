<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<style>
    .toggle-wrapper input {
        display: none;
    }
</style>

<h4>
    관리자 세팅
</h4>


<form id="simple-form">

    <div class="modal-form-half-row border-top">
        <div>
            ROOT_DOMAIN
        </div>
        <input type="text" name="ROOT_DOMAIN" value="${adminSetting.ROOT_DOMAIN}">
    </div>

    <div class="modal-form-41-row border-top">
        <div>
            인증서 만료 알림 (n일전)
        </div>
        <div>
            <input type="text" name="CERT_EXPIRE_NOTI" value="${adminSetting.CERT_EXPIRE_NOTI}">
        </div>
    </div>

    <!-- ---------------------------------------------------------- -->

    <div class="modal-form-half-row border-top">
        <div>
            SERVER_MANAGER_USER
        </div>
        <input type="text" name="SERVER_MANAGER_USER" value="${adminSetting.SERVER_MANAGER_USER}">
    </div>

    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-half-row border-top">
        <div>
            인증서 만료 알림(CERT_NOTI_CRON)
        </div>
        <select name="CERT_NOTI_CRON" class="w3-select">
            <option value="">
                ----- 선택 -----
            </option>
            <c:forEach var="dto" items="${cronList}">
                <c:if test="${dto.cron eq adminSetting.CERT_NOTI_CRON}">
                    <option value="${dto.cron}" selected>${dto.name}</option>
                </c:if>
                <c:if test="${dto.cron ne adminSetting.CERT_NOTI_CRON}">
                    <option value="${dto.cron}">${dto.name}</option>
                </c:if>
            </c:forEach>
        </select>
    </div>

    <!-- ---------------------------------------------------------- -->
    <!-- ---------------------------------------------------------- -->

    <div class="modal-form-half-row border-top">
        <div>
            SMTP_USER
        </div>
        <input type="text" name="SMTP_USER" value="${adminSetting.SMTP_USER}">
    </div>

    <div class="modal-form-half-row">
        <div>
            SMTP_PASSWORD
        </div>
        <input type="password" name="SMTP_PASSWORD" value="${adminSetting.SMTP_PASSWORD}">
    </div>

    <div class="modal-form-half-row">
        <div>
            SMTP_HOST
        </div>
        <input type="text" name="SMTP_HOST" value="${adminSetting.SMTP_HOST}">
    </div>

    <div class="modal-form-half-row">
        <div>
            SMTP_PORT
        </div>
        <input type="text" name="SMTP_PORT" value="${adminSetting.SMTP_PORT}">
    </div>

    <div class="modal-form-41-row">
        <div>
            SMTP_AUTH
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.SMTP_AUTH}"
             onclick="toggleAdminSetting(this)">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="SMTP_AUTH" value="${adminSetting.SMTP_AUTH}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            SMTP_SSL_ENABLE
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.SMTP_SSL_ENABLE}"
             onclick="toggleAdminSetting(this)">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="SMTP_SSL_ENABLE" value="${adminSetting.SMTP_SSL_ENABLE}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            SMTP_STARTTLS_ENABLE
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.SMTP_STARTTLS_ENABLE}"
             onclick="toggleAdminSetting(this)">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="SMTP_STARTTLS_ENABLE" value="${adminSetting.SMTP_STARTTLS_ENABLE}">
        </div>
    </div>

    <div class="modal-form-half-row">
        <div>
            SMTP_SSL_TRUST
        </div>
        <input type="text" name="SMTP_SSL_TRUST" value="${adminSetting.SMTP_SSL_TRUST}">
    </div>

    <div class="modal-form-half-row border-top">
        <div>
            ADMIN_EMAIL
        </div>
        <input type="text" name="ADMIN_EMAIL" value="${adminSetting.ADMIN_EMAIL}">
    </div>

    <div class="modal-form-half-row">
        <div>
            ADMIN_LOG_MAIL_TITLE
        </div>
        <input type="text" name="ADMIN_LOG_MAIL_TITLE" value="${adminSetting.ADMIN_LOG_MAIL_TITLE}">
    </div>

    <div class="modal-form-half-row">
        <div>
            ADMIN_LOG_MAIL_CRON
        </div>
        <select name="ADMIN_LOG_MAIL_CRON" class="w3-select">
            <option value="">
                ----- 선택 -----
            </option>
            <c:forEach var="dto" items="${cronList}">
                <c:if test="${dto.cron eq adminSetting.ADMIN_LOG_MAIL_CRON}">
                    <option value="${dto.cron}" selected>${dto.name}</option>
                </c:if>
                <c:if test="${dto.cron ne adminSetting.ADMIN_LOG_MAIL_CRON}">
                    <option value="${dto.cron}">${dto.name}</option>
                </c:if>
            </c:forEach>
        </select>
    </div>

    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row border-top">
        <div>
            DNS_UPDATE
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.DNS_UPDATE}"
             onclick="toggleAdminSetting(this)">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="DNS_UPDATE" value="${adminSetting.DNS_UPDATE}">
        </div>
    </div>

    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row border-top">
        <div>
            NGINX_EXTERNAL_CERTBOT
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NGINX_EXTERNAL_CERTBOT}"
             onclick="toggleAdminSetting(this)">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NGINX_EXTERNAL_CERTBOT" value="${adminSetting.NGINX_EXTERNAL_CERTBOT}">
        </div>
    </div>

    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-half-row">
        <div>
            DB_BACKUP_CRON
        </div>
        <select name="DB_BACKUP_CRON" class="w3-select">
            <option value="">
                ----- 선택 -----
            </option>
            <c:forEach var="dto" items="${cronList}">
                <c:if test="${dto.cron eq adminSetting.DB_BACKUP_CRON}">
                    <option value="${dto.cron}" selected>${dto.name}</option>
                </c:if>
                <c:if test="${dto.cron ne adminSetting.DB_BACKUP_CRON}">
                    <option value="${dto.cron}">${dto.name}</option>
                </c:if>
            </c:forEach>
        </select>
    </div>

    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-half-row">
        <div>
            인터넷 속도 측정 주기
        </div>
        <select name="INTERNET_TEST_CRON" class="w3-select">
            <option value="">
                ----- 선택 -----
            </option>
            <c:forEach var="dto" items="${cronList}">
                <c:if test="${dto.cron eq adminSetting.INTERNET_TEST_CRON}">
                    <option value="${dto.cron}" selected>${dto.name}</option>
                </c:if>
                <c:if test="${dto.cron ne adminSetting.INTERNET_TEST_CRON}">
                    <option value="${dto.cron}">${dto.name}</option>
                </c:if>
            </c:forEach>
        </select>
    </div>

    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-half-row">
        <div>
            FAIL2BAN_TOKEN
        </div>
        <input type="text" name="FAIL2BAN_TOKEN" value="${adminSetting.FAIL2BAN_TOKEN}">
    </div>
    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row">
        <div>
            SERVER_HEALTH_CHECK
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.SERVER_HEALTH_CHECK}"
             onclick="toggleAdminSetting(this)">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="SERVER_HEALTH_CHECK" value="${adminSetting.SERVER_HEALTH_CHECK}">
        </div>
    </div>
    <div class="modal-form-41-row">
        <div>
            WEB_SERVER_HEALTH_CHECK
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.WEB_SERVER_HEALTH_CHECK}"
             onclick="toggleAdminSetting(this)">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="WEB_SERVER_HEALTH_CHECK" value="${adminSetting.WEB_SERVER_HEALTH_CHECK}">
        </div>
    </div>
    <div class="modal-form-41-row">
        <div>
            DB_HEALTH_CHECK
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.DB_HEALTH_CHECK}"
             onclick="toggleAdminSetting(this)">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="DB_HEALTH_CHECK" value="${adminSetting.DB_HEALTH_CHECK}">
        </div>
    </div>

    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row border-top">
        <div>
            NOTI_ALL
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_ALL} tg-pa-noti"
             onclick="toggleAdminSetting(this, ['tg-noti', 'tg-nateon', 'tg-slack'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_ALL" value="${adminSetting.NOTI_ALL}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_ALL_NATEON
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_ALL_NATEON} tg-noti tg-pa-nateon"
             onclick="toggleAdminSetting(this, ['tg-nateon'], ['tg-pa-noti'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_ALL_NATEON" value="${adminSetting.NOTI_ALL_NATEON}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_ALL_SLACK
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_ALL_SLACK} tg-noti tg-pa-slack"
             onclick="toggleAdminSetting(this, ['tg-slack'], ['tg-pa-noti'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_ALL_SLACK" value="${adminSetting.NOTI_ALL_SLACK}">
        </div>
    </div>


    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row border-top">
        <div>
            NOTI_STARTUP
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_STARTUP} tg-noti tg-pa-startup"
             onclick="toggleAdminSetting(this, ['tg-startup'], ['tg-pa-noti'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_STARTUP" value="${adminSetting.NOTI_STARTUP}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_STARTUP_NATEON
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_STARTUP_NATEON} tg-noti tg-nateon tg-startup"
             onclick="toggleAdminSetting(this, [], ['tg-pa-noti', 'tg-pa-nateon', 'tg-pa-startup'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_STARTUP_NATEON" value="${adminSetting.NOTI_STARTUP_NATEON}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_STARTUP_SLACK
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_STARTUP_SLACK} tg-noti tg-slack tg-startup"
             onclick="toggleAdminSetting(this, [], ['tg-pa-noti', 'tg-pa-slack', 'tg-pa-startup'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_STARTUP_SLACK" value="${adminSetting.NOTI_STARTUP_SLACK}">
        </div>
    </div>

    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row border-top">
        <div>
            NOTI_MAIL
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_MAIL} tg-noti tg-pa-mail"
             onclick="toggleAdminSetting(this, ['tg-mail'], ['tg-pa-mail'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_MAIL" value="${adminSetting.NOTI_MAIL}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_MAIL_NATEON
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_MAIL_NATEON} tg-noti tg-nateon tg-mail"
             onclick="toggleAdminSetting(this, [], ['tg-pa-mail', 'tg-pa-nateon', 'tg-pa-mail'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_MAIL_NATEON" value="${adminSetting.NOTI_MAIL_NATEON}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_MAIL_SLACK
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_MAIL_SLACK} tg-noti tg-slack tg-mail"
             onclick="toggleAdminSetting(this, [], ['tg-pa-mail', 'tg-pa-slack', 'tg-pa-mail'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_MAIL_SLACK" value="${adminSetting.NOTI_MAIL_SLACK}">
        </div>
    </div>


    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row border-top">
        <div>
            NOTI_HEALTHCHECK
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_HEALTHCHECK} tg-noti tg-pa-healthcheck"
             onclick="toggleAdminSetting(this, ['tg-healthcheck'], ['tg-pa-noti'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_HEALTHCHECK" value="${adminSetting.NOTI_HEALTHCHECK}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_HEALTHCHECK_NATEON
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_HEALTHCHECK_NATEON} tg-noti tg-nateon tg-healthcheck"
             onclick="toggleAdminSetting(this, [], ['tg-pa-noti', 'tg-pa-nateon', 'tg-pa-healthcheck'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_HEALTHCHECK_NATEON" value="${adminSetting.NOTI_HEALTHCHECK_NATEON}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_HEALTHCHECK_SLACK
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_HEALTHCHECK_SLACK} tg-noti tg-slack tg-healthcheck"
             onclick="toggleAdminSetting(this, [], ['tg-pa-noti', 'tg-pa-slack', 'tg-pa-healthcheck'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_HEALTHCHECK_SLACK" value="${adminSetting.NOTI_HEALTHCHECK_SLACK}">
        </div>
    </div>


    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row border-top">
        <div>
            NOTI_NGINX_UPDATE
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_NGINX_UPDATE} tg-noti tg-pa-nginx-update"
             onclick="toggleAdminSetting(this, ['tg-nginx-update'], ['tg-pa-noti'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_NGINX_UPDATE" value="${adminSetting.NOTI_NGINX_UPDATE}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_NGINX_UPDATE_NATEON
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_NGINX_UPDATE_NATEON} tg-noti tg-nateon tg-nginx-update"
             onclick="toggleAdminSetting(this, [], ['tg-pa-noti', 'tg-pa-nateon', 'tg-pa-nginx-update'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_NGINX_UPDATE_NATEON" value="${adminSetting.NOTI_NGINX_UPDATE_NATEON}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_NGINX_UPDATE_SLACK
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_NGINX_UPDATE_SLACK} tg-noti tg-slack tg-nginx-update"
             onclick="toggleAdminSetting(this, [], ['tg-pa-noti', 'tg-pa-slack', 'tg-pa-nginx-update'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_NGINX_UPDATE_SLACK" value="${adminSetting.NOTI_NGINX_UPDATE_SLACK}">
        </div>
    </div>


    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row border-top">
        <div>
            NOTI_NGINX_RESTORE
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_NGINX_RESTORE} tg-noti tg-pa-nginx-restore"
             onclick="toggleAdminSetting(this, ['tg-nginx-restore'], ['tg-pa-nginx-restore'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_NGINX_RESTORE" value="${adminSetting.NOTI_NGINX_RESTORE}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_NGINX_RESTORE_NATEON
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_NGINX_RESTORE_NATEON} tg-noti tg-nateon tg-nginx-restore"
             onclick="toggleAdminSetting(this, [], ['tg-pa-noti', 'tg-pa-nateon', 'tg-pa-nginx-restore'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_NGINX_RESTORE_NATEON" value="${adminSetting.NOTI_NGINX_RESTORE_NATEON}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_NGINX_RESTORE_SLACK
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_NGINX_RESTORE_SLACK} tg-noti tg-slack tg-nginx-restore"
             onclick="toggleAdminSetting(this, [], ['tg-pa-noti', 'tg-pa-slack', 'tg-pa-nginx-restore'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_NGINX_RESTORE_SLACK" value="${adminSetting.NOTI_NGINX_RESTORE_SLACK}">
        </div>
    </div>


    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row border-top">
        <div>
            NOTI_DB_SUCCESS
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_DB_SUCCESS} tg-noti tg-pa-dbsuccess"
             onclick="toggleAdminSetting(this, ['tg-dbsuccess'], ['tg-pa-noti'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_DB_SUCCESS" value="${adminSetting.NOTI_DB_SUCCESS}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_DB_SUCCESS_NATEON
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_DB_SUCCESS_NATEON} tg-noti tg-nateon tg-dbsuccess"
             onclick="toggleAdminSetting(this, [], ['tg-pa-noti', 'tg-pa-nateon', 'tg-pa-dbsuccess'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_DB_SUCCESS_NATEON" value="${adminSetting.NOTI_DB_SUCCESS_NATEON}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_DB_SUCCESS_SLACK
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_DB_SUCCESS_SLACK} tg-noti tg-slack tg-dbsuccess"
             onclick="toggleAdminSetting(this, [], ['tg-pa-noti', 'tg-pa-slack', 'tg-pa-dbsuccess'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_DB_SUCCESS_SLACK" value="${adminSetting.NOTI_DB_SUCCESS_SLACK}">
        </div>
    </div>


    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row border-top">
        <div>
            NOTI_DB_FAIL
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_DB_FAIL} tg-noti tg-pa-dbfail"
             onclick="toggleAdminSetting(this, ['tg-dbfail'], ['tg-pa-noti'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_DB_FAIL" value="${adminSetting.NOTI_DB_FAIL}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_DB_FAIL_NATEON
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_DB_FAIL_NATEON} tg-noti tg-nateon tg-dbfail"
             onclick="toggleAdminSetting(this, [], ['tg-pa-noti', 'tg-pa-nateon', 'tg-pa-dbfail'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_DB_FAIL_NATEON" value="${adminSetting.NOTI_DB_FAIL_NATEON}">
        </div>
    </div>

    <div class="modal-form-41-row">
        <div>
            NOTI_DB_FAIL_SLACK
        </div>
        <div class="toggle-wrapper toggle-${adminSetting.NOTI_DB_FAIL_SLACK} tg-noti tg-slack tg-dbfail"
             onclick="toggleAdminSetting(this, [], ['tg-pa-noti', 'tg-pa-slack', 'tg-pa-dbfail'])">
            <i class="fas fa-toggle-on"></i>
            <i class="fas fa-toggle-off"></i>
            <input type="text" name="NOTI_DB_FAIL_SLACK" value="${adminSetting.NOTI_DB_FAIL_SLACK}">
        </div>
    </div>


    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row border-top">
        <div>
            NGINX_LOG_FORMAT
        </div>
    </div>

    <div>
        <textarea style="width: 100%; height: 30vh; padding: 3px;"
                  name="NGINX_LOG_FORMAT">${adminSetting.NGINX_LOG_FORMAT}</textarea>
    </div>

    <!-- ---------------------------------------------------------- -->
    <div class="modal-form-41-row border-top">
        <div>
            EXTERNAL_CERT_CHECK_URLS
        </div>
    </div>

    <div>
        <textarea style="width: 100%; height: 30vh; padding: 3px;"
                  name="EXTERNAL_CERT_CHECK_URLS">${adminSetting.EXTERNAL_CERT_CHECK_URLS}</textarea>
    </div>

    <!-- ---------------------------------------------------------- -->
</form>

<%--<div class="modal-form-41-row" style="text-align: center;">--%>

<%--    <button type="button" class="w3-button w3-round w3-green"--%>
<%--            onclick="submitFormAjax_AdminSetting('simple-form', 'simple-modal-form', '${contextPath}/api/v1/adminsetting/update')">--%>
<%--        저장--%>
<%--    </button>--%>

<%--    <button type="button" class="w3-button w3-round w3-red" onclick="closeInputModal('simple-modal-form')">--%>
<%--        취소--%>
<%--    </button>--%>
<%--</div>--%>

<script>

    window.onload = function () {
        setTimeout(() => {
            submitFormAjax_AdminSetting('simple-form', 'simple-modal-form', '${contextPath}/api/v1/adminsetting/update');
        }, 1500);
    }

</script>
