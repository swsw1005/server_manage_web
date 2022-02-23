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
    private String SMTP_USER = "";
    private String SMTP_PASSWORD = "";
    private String SMTP_HOST = "smtp.gmail.com";
    private int SMTP_PORT = 465;
    private boolean SMTP_AUTH = true;
    private boolean SMTP_SSL_ENABLE = true;
    private String SMTP_SSL_TRUST = "smtp.gmail.com";
    ////////////////////////////////////////
    private boolean DNS_UPDATE = false;
    ////////////////////////////////////////
    private boolean NOTI_ALL = false;
    private boolean NOTI_ALL_NATEON = false;
    private boolean NOTI_ALL_SLACK = false;
    ////////////////////////////////////////
    private boolean NOTI_STARTUP = false;
    private boolean NOTI_STARTUP_NATEON = false;
    private boolean NOTI_STARTUP_SLACK = false;
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




}
