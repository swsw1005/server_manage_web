package sw.im.swim.util.nginx;

import lombok.extern.slf4j.Slf4j;
import sw.im.swim.config.GeneralConfig;

/**
 * <PRE>
 * nginx 설정에 쓰이는 string 보관
 * 또는 그 string 조합하여 폴더/파일명 제공
 * </PRE>
 */
@Slf4j
public class NginxConfStringContext {

    public static final String CERT_FILE_PREFIX() {
        return GeneralConfig.USER_HOME_DIR("pem");
    }

    public static final String CERT_FILE_FULLCHAIN = "fullchain.pem";
    public static final String CERT_FILE_PRIKEY = "privkey.pem";
    public static final String CERT_FILE_CHAIN = "chain.pem";


    public static final String pathSeparator = "/";

    public static final String NGINX_SITES_DIRNAME = "sites-available";
    public static final String NGINX_CONF_DIRNAME = "conf.d";

    public static final String NGINX_MIME_TYPE = "mime.types";

    public static final String NGINX_CONF_FILE = "nginx.conf";
    public static final String UPDATE_TITLE = "NGINX UPDATE";
    public static final String RESTORE_TITLE = "NGINX RESTORE";

    public static final String NGINX_CONF_FILE_PATH = NGINX_CONF_DIR() + pathSeparator + NGINX_CONF_FILE;


    /**
     * <PRE>
     * server block location
     * ex)  /etc/nginx/sites-enabled
     * </PRE>
     *
     * @return
     */
    public static final String NGINX_EXTRA_SITES_DIR() {
        return NGINX_CONF_DIR() + pathSeparator + NGINX_SITES_DIRNAME;
    }

    /**
     * <PRE>
     * extra conf location
     * ex)  /etc/nginx/conf.d
     * </PRE>
     *
     * @return
     */
    public static final String NGINX_EXTRA_CONF_DIR() {
        return NGINX_CONF_DIR() + pathSeparator + NGINX_CONF_DIRNAME;
    }

    public static final String NGINX_EXTRA_MIME_TYPE() {
        return NGINX_CONF_DIR() + pathSeparator + NGINX_MIME_TYPE;
    }


    /**
     * <PRE>
     * DB에 설정된 nginx conf 디렉토리
     * ex)  /etc/nginx
     * </PRE>
     *
     * @return
     */
    public static final String NGINX_CONF_DIR() {
        try {
            final String nginxConfDir = GeneralConfig.ADMIN_SETTING.getNGINX_CONF_DIR();

            if (nginxConfDir.length() > 4) {
                return nginxConfDir;
            }

        } catch (NullPointerException e) {
            log.warn(" GeneralConfig ADMIN_SETTING NGINX_CONF_DIR is NULL !  " + e);
        } catch (Exception e) {
            log.warn("GeneralConfig.ADMIN_SETTING.getNGINX_CONF_DIR(); ERROR !! | " + e + " | " + e.getMessage());
        }
        throw new NullPointerException("nginx conf ERROR !  ");
    }

}
