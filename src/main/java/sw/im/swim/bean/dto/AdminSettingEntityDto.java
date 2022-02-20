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
    private boolean NOTI_ALL = true;
    private boolean NOTI_ALL_NATEON = true;
    private boolean NOTI_ALL_SLACK = true;
    ////////////////////////////////////////
    private boolean NOTI_STARTUP = true;
    private boolean NOTI_STARTUP_NATEON = true;
    private boolean NOTI_STARTUP_SLACK = true;
    ////////////////////////////////////////
    private boolean NOTI_HEALTHCHECK = true;
    private boolean NOTI_HEALTHCHECK_NATEON = true;
    private boolean NOTI_HEALTHCHECK_SLACK = true;
    ////////////////////////////////////////
    private boolean NOTI_NGINX = true;
    private boolean NOTI_NGINX_NATEON = true;
    private boolean NOTI_NGINX_SLACK = true;
    ////////////////////////////////////////
    private boolean NOTI_DB_SUCCESS = true;
    private boolean NOTI_DB_SUCCESS_NATEON = true;
    private boolean NOTI_DB_SUCCESS_SLACK = true;
    ////////////////////////////////////////
    private boolean NOTI_DB_FAIL = true;
    private boolean NOTI_DB_FAIL_NATEON = true;
    private boolean NOTI_DB_FAIL_SLACK = true;
    ////////////////////////////////////////
}
