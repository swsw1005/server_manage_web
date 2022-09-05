package sw.im.swim.bean.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <PRE>
 * 관리자 설정값 전달, 보관 객체
 * |   DB  table         DTO
 * |   key   value  =>  String, int , boolean
 * ** String을 제외하고 원시타입만 사용하도록 한다.
 * </PRE>
 */
@Data
public class AdminSettingEntityDto implements Serializable {
    ////////////////////////////////////////
    private String NGINX_CONF_DIR = "/etc/nginx";
    private boolean NGINX_NATIVE = true;
    private boolean NGINX_80_PROXY = true;
    ////////////////////////////////////////
    private boolean NGINX_EXTERNAL_CERTBOT = true;
    ////////////////////////////////////////
    private String NGINX_LOG_FORMAT = "";
    ////////////////////////////////////////
    private String ROOT_DOMAIN = "";
    ////////////////////////////////////////
    private int CERT_EXPIRE_NOTI = 10;
    private String CERT_NOTI_CRON = "";
    ////////////////////////////////////////
    private final String SERVER_MANAGER_USER = "root";
    ////////////////////////////////////////
    private String WORK_SPACE = "/usr/local/server-manager";
    ////////////////////////////////////////
    private String SMTP_USER = "";
    private String SMTP_PASSWORD = "";
    private String SMTP_HOST = "smtp.gmail.com";
    private int SMTP_PORT = 465;
    private boolean SMTP_AUTH = true;
    private boolean SMTP_SSL_ENABLE = true;
    private boolean SMTP_STARTTLS_ENABLE = true;
    private String SMTP_SSL_TRUST = "smtp.gmail.com";
    ////////////////////////////////////////
    private String ADMIN_EMAIL = "";
    private String ADMIN_LOG_MAIL_TITLE = "[관리자 로그] 메일";
    private String ADMIN_LOG_MAIL_CRON = "";
    ////////////////////////////////////////
    private boolean DNS_UPDATE = false;
    ////////////////////////////////////////
    private String DB_BACKUP_CRON = "";
    ////////////////////////////////////////
    private String INTERNET_TEST_CRON = "";
    ////////////////////////////////////////
    private String FAIL2BAN_TOKEN = "";
    ////////////////////////////////////////
    private boolean SERVER_HEALTH_CHECK = false;
    private boolean WEB_SERVER_HEALTH_CHECK = false;
    private boolean DB_HEALTH_CHECK = false;
    ////////////////////////////////////////
    private boolean NOTI_ALL = false;
    private boolean NOTI_ALL_NATEON = false;
    private boolean NOTI_ALL_SLACK = false;
    ////////////////////////////////////////
    private boolean NOTI_STARTUP = false;
    private boolean NOTI_STARTUP_NATEON = false;
    private boolean NOTI_STARTUP_SLACK = false;
    ////////////////////////////////////////
    private boolean NOTI_CERTBOT = true;
    private boolean NOTI_CERTBOT_NATEON = true;
    private boolean NOTI_CERTBOT_SLACK = true;
    ////////////////////////////////////////
    private boolean NOTI_MAIL = false;
    private boolean NOTI_MAIL_NATEON = false;
    private boolean NOTI_MAIL_SLACK = false;
    ////////////////////////////////////////
    private boolean NOTI_HEALTHCHECK = false;
    private boolean NOTI_HEALTHCHECK_NATEON = false;
    private boolean NOTI_HEALTHCHECK_SLACK = false;
    ////////////////////////////////////////
    private boolean NOTI_NGINX_UPDATE = false;
    private boolean NOTI_NGINX_UPDATE_NATEON = false;
    private boolean NOTI_NGINX_UPDATE_SLACK = false;
    ////////////////////////////////////////
    private boolean NOTI_NGINX_RESTORE = false;
    private boolean NOTI_NGINX_RESTORE_NATEON = false;
    private boolean NOTI_NGINX_RESTORE_SLACK = false;
    ////////////////////////////////////////
    private boolean NOTI_DB_SUCCESS = false;
    private boolean NOTI_DB_SUCCESS_NATEON = false;
    private boolean NOTI_DB_SUCCESS_SLACK = false;
    ////////////////////////////////////////
    private boolean NOTI_DB_FAIL = false;
    private boolean NOTI_DB_FAIL_NATEON = false;
    private boolean NOTI_DB_FAIL_SLACK = false;
    ////////////////////////////////////////
    private boolean FAIL2BAN = false;
    private boolean FAIL2BAN_NATEON = false;
    private boolean FAIL2BAN_SLACK = false;
    ////////////////////////////////////////


}
