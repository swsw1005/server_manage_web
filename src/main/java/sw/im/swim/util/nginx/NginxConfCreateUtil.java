package sw.im.swim.util.nginx;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import sw.im.swim.bean.dto.DomainEntityDto;
import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.bean.dto.WebServerEntityDto;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.util.date.DateFormatUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NginxConfCreateUtil {


    private static final String
            // ------------------------------------
            TAB = "    ";

    private static final String[] HEAD(int workerProcessed, int workerConnections) {

        final String user;

        final String generalConfigNginxUser = GeneralConfig.ADMIN_SETTING.getNGINX_USER();

        if (generalConfigNginxUser == null || generalConfigNginxUser.isEmpty()) {
            user = "";
        } else {
            user = "user " + generalConfigNginxUser + ";";
        }

        String[] result = {user,
                //
                "worker_processes " + workerProcessed + ";",
                //
                "pid /run/nginx.pid;",
                //
                "include /etc/nginx/modules-enabled/*.conf;",
                //
                "",
                //
                "events {",
                // ------------------------------------
                TAB + "worker_connections " + workerConnections + ";",
                //
                TAB + "# multi_accept on;",
                //
                "}",
                //
                ""};

        return result;
    }

    public static void CREATE_SITES_ENABLES(final String NGINX_SITES_DIR, List<NginxServerEntityDto> nginxServerEntityList) {

        try {

            final String ROOT_DOMAIN_NAME = GeneralConfig.ADMIN_SETTING.getROOT_DOMAIN();

            final String CURRENT_IP = GeneralConfig.CURRENT_SERVER_INFO.getIp();

            final String LOCAL_IP_ADDR = CURRENT_IP.substring(0, CURRENT_IP.lastIndexOf("."));

            final boolean IS_ROOT_DOMAIN_PROXY = GeneralConfig.ADMIN_SETTING.isNGINX_80_PROXY();

            if (ROOT_DOMAIN_NAME == null || ROOT_DOMAIN_NAME.length() < 5) {
                throw new Exception("ROOT 도메인이 너무 짧습니다. 올바르게 설정해주세요 : " + ROOT_DOMAIN_NAME + "\t" + ROOT_DOMAIN_NAME.length());
            }

            new File(NginxConfStringContext.CERT_FILE_PREFIX + "/" + ROOT_DOMAIN_NAME).mkdirs();

            GeneralConfig.CERT_FILE_FULLCHAIN = NginxConfStringContext.CERT_FILE_PREFIX + "/" + ROOT_DOMAIN_NAME + "/" + NginxConfStringContext.CERT_FILE_FULLCHAIN;
            GeneralConfig.CERT_FILE_PRIKEY = NginxConfStringContext.CERT_FILE_PREFIX + "/" + ROOT_DOMAIN_NAME + "/" + NginxConfStringContext.CERT_FILE_PRIKEY;
            GeneralConfig.CERT_FILE_CHAIN = NginxConfStringContext.CERT_FILE_PREFIX + "/" + ROOT_DOMAIN_NAME + "/" + NginxConfStringContext.CERT_FILE_CHAIN;

            File CERT_FILE_FULLCHAIN = new File(GeneralConfig.CERT_FILE_FULLCHAIN);
            File CERT_FILE_PRIKEY = new File(GeneralConfig.CERT_FILE_PRIKEY);
            File CERT_FILE_CHAIN = new File(GeneralConfig.CERT_FILE_CHAIN);

            // assertFileExitst(CERT_FILE_FULLCHAIN);
            // assertFileExitst(CERT_FILE_PRIKEY);
            // assertFileExitst(CERT_FILE_CHAIN);

            for (NginxServerEntityDto nginxServerEntityDto : nginxServerEntityList) {

                final WebServerEntityDto webServerEntityDto = nginxServerEntityDto.getWebServerEntity();
                final DomainEntityDto domainEntityDto = nginxServerEntityDto.getDomainEntity();

                final String NAME = nginxServerEntityDto.getName();
                final String IP = webServerEntityDto.getServerInfoEntity().getIp();
                final String HTTPS = webServerEntityDto.HTTPS_PREFIX();
                final String ADDRESS = webServerEntityDto.getAddress();
                final String FAVICON = nginxServerEntityDto.getFaviconEntity().getPath();
                final String bodySize = nginxServerEntityDto.getBodySize();
                String DOMAIN = domainEntityDto.getDomain();

                if (!DOMAIN.contains(ROOT_DOMAIN_NAME)) {
                    DOMAIN += "." + ROOT_DOMAIN_NAME;
                }

                final boolean IS_ROOT_DOMAIN = DOMAIN.equals(ROOT_DOMAIN_NAME);

                List<String> list = new ArrayList<>();

                list.add("#-------------------------------------");
                list.add("## " + ADDRESS + " | " + NAME);
                list.add("##  " + IP);
                list.add("#-------------------------------------");
                list.add("");
                list.add("server {");
                list.add("");

                if (IS_ROOT_DOMAIN) {
                } else {
                    list.add(TAB + "server_name " + DOMAIN + ";");
                }
                list.add("");
                list.add(TAB + "client_max_body_size " + bodySize + ";");
                list.add("");

                list.add(TAB + "proxy_set_header Host $http_host;");
                list.add(TAB + "proxy_set_header Connection \"\";");
                list.add("");
                list.add(TAB + "add_header      X-Frame-Options SAMEORIGIN;");
                list.add(TAB + "add_header      X-Content-Type-Options nosniff;");
                list.add(TAB + "add_header      Cache-Control \"no-cache, no-store, must-revalidate\";");
                list.add(TAB + "proxy_cookie_path / \"/; SameSite=lax; HTTPOnly; Secure\";");
                list.add("");
                list.add(TAB + "location /favicon.ico {");
                list.add(TAB + TAB + "access_log off;");
                list.add(TAB + TAB + "log_not_found off;");
                list.add(TAB + TAB + "alias " + FAVICON + ";");
                list.add(TAB + "}");
                list.add("");

                if (IS_ROOT_DOMAIN) {
                    list.add(TAB + "location / {");
                    list.add("");
                    list.add(TAB + TAB + "## sub domain에서 호출시 cors 처리");
                    list.add(TAB + TAB + "if ($http_origin ~* (https?://[^/]*\\." + ROOT_DOMAIN_NAME + "(:[0-9]+)?)$) {");
                    list.add(TAB + TAB + TAB + "add_header 'Access-Control-Allow-Origin' \"${http_origin}\";");
                    list.add(TAB + TAB + "}");
                    list.add("");
                    list.add(TAB + TAB + "## 내부 IP로 작업시 cors 처리");
                    list.add(TAB + TAB + "if ($http_origin ~* (https?://" + LOCAL_IP_ADDR + "(.[0-9]+)(:[0-9]+)?)$) {");
                    list.add(TAB + TAB + TAB + "add_header 'Access-Control-Allow-Origin' \"${http_origin}\";");
                    list.add(TAB + TAB + "}");
                    list.add("");

                    if (IS_ROOT_DOMAIN_PROXY) {
                        addLocationBlock(HTTPS, ADDRESS, list);
                    } else {
                        list.add(TAB + TAB + "root /var/www/html;");
                        list.add(TAB + TAB + "index index.html index.htm;");
                        list.add(TAB + "}");
                        list.add("");
                    }
                } else {
                    addLocationBlock(HTTPS, ADDRESS, list);
                }

                list.add(TAB + "listen 443 ssl;");
                list.add(TAB + "ssl_protocols TLSv1 TLSv1.1 TLSv1.2;");
                list.add(TAB + "ssl_ciphers 'ECDHE-RSA-AES128-GCM-SHA256:ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES256-GCM-SHA384:ECDHE-ECDSA-AES256-GCM-SHA384:DHE-RSA-AES128-GCM-SHA256:DHE-DSS-AES128-GCM-SHA256:kEDH+AESGCM:ECDHE-RSA-AES128-SHA256:ECDHE-ECDSA-AES128-SHA256:ECDHE-RSA-AES128-SHA:ECDHE-ECDSA-AES128-SHA:ECDHE-RSA-AES256-SHA384:ECDHE-ECDSA-AES256-SHA384:ECDHE-RSA-AES256-SHA:ECDHE-ECDSA-AES256-SHA:DHE-RSA-AES128-SHA256:DHE-RSA-AES128-SHA:DHE-DSS-AES128-SHA256:DHE-RSA-AES256-SHA256:DHE-DSS-AES256-SHA:DHE-RSA-AES256-SHA:AES128-GCM-SHA256:AES256-GCM-SHA384:AES128-SHA256:AES256-SHA256:AES128-SHA:AES256-SHA:AES:CAMELLIA:DES-CBC3-SHA:!aNULL:!eNULL:!EXPORT:!DES:!RC4:!MD5:!PSK:!aECDH:!EDH-DSS-DES-CBC3-SHA:!EDH-RSA-DES-CBC3-SHA:!KRB5-DES-CBC3-SHA';");
                list.add(TAB + "ssl_prefer_server_ciphers on;");
                list.add(TAB + "ssl_certificate          " + GeneralConfig.CERT_FILE_FULLCHAIN + ";");
                list.add(TAB + "ssl_certificate_key      " + GeneralConfig.CERT_FILE_PRIKEY + ";");
                list.add(TAB + "ssl_trusted_certificate  " + GeneralConfig.CERT_FILE_CHAIN + ";");

                list.add("");
                list.add("}");

                list.add("server {");
                list.add(TAB + "if ($host = " + DOMAIN + ") {");
                list.add(TAB + TAB + "return 301 https://$host$request_uri;");
                list.add(TAB + "}");
                list.add(TAB + "server_name " + DOMAIN + ";");
                list.add(TAB + TAB + "listen 80;");
                list.add(TAB + TAB + "return 404;");
                list.add("}");
                list.add("");
                list.add("##########################################################");


                // 파일에 작성
                String fileName = DOMAIN.replace("\\.", "_").replace(" ", "_");
//                        DOMAIN.replace(ROOT_DOMAIN_NAME, "_");

                if (IS_ROOT_DOMAIN) {
                    fileName = "01_" + fileName;
                } else {
                    fileName = "02_" + fileName;
                }
                fileName = NGINX_SITES_DIR + "/" + fileName;

                File newFile = new File(fileName);
                newFile.createNewFile();

                FileUtils.writeLines(newFile, StandardCharsets.UTF_8.name(),
                        ///////////////////////////////////////////////////////////
                        list, System.lineSeparator());

            }

        } catch (RuntimeException e) {
            log.error(e + "  " + e.getMessage(), e);
        } catch (Exception e) {
            log.error(e + "  " + e.getMessage(), e);
        }


    }

    private static void addLocationBlock(String HTTPS, String ADDRESS, List<String> list) {
        list.add(TAB + "location / {");
        list.add("");
        list.add(TAB + TAB + "proxy_redirect off;");
        list.add(TAB + TAB + "proxy_pass_header Server;");
        list.add(TAB + TAB + "proxy_set_header Host $http_host;");
        list.add(TAB + TAB + "proxy_set_header X-Real-IP $remote_addr;");
        list.add(TAB + TAB + "proxy_set_header X-Scheme $scheme;");
        list.add(TAB + TAB + "proxy_pass " + HTTPS + ADDRESS + ";");
        list.add(TAB + "}");
        list.add("");
    }

    private static void assertFileExitst(File certFile) throws FileNotFoundException {
        try {

            if (!certFile.exists()) {
                throw new FileNotFoundException("file not exist : " + certFile.getAbsolutePath());
            }
            if (!certFile.canRead()) {
                throw new FileNotFoundException("file cannot read : " + certFile.getAbsolutePath());
            }
            if (certFile.length() < 10) {
                throw new FileNotFoundException("file too small : " + certFile.getAbsolutePath() + "\t size : " + certFile.length());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static List<String> DEFAULT_NGINX_CONF(NginxPolicyEntityDto policyEntityDto) throws Exception {

        List<String> list = new ArrayList<>();

        List<String> list1 = AskiiArtUtil.CREATE_NGINX_BANNER(DateFormatUtil.DATE_FORMAT_yyyy_MM_dd.format(new Date()));
        List<String> list2 = AskiiArtUtil.CREATE_NGINX_BANNER(DateFormatUtil.DATE_FORMAT_HH_mm_ss.format(new Date()));

        list.addAll(list1);
        list.addAll(list2);

        try {

            final int processed = policyEntityDto.getWorkerProcessed();
            final int connections = policyEntityDto.getWorkerConnections();

            String[] HEAD = HEAD(processed, connections);
            for (int i = 0; i < HEAD.length; i++) {
                list.add(HEAD[i]);
            }

            List<String> resultList = new ArrayList<>();

            resultList.add("http {");
            resultList.add("");
            resultList.add(TAB + "##");
            resultList.add(TAB + "# Basic Settings");
            resultList.add(TAB + "##");
            resultList.add("");
            resultList.add(TAB + "sendfile on;");
            resultList.add(TAB + "tcp_nopush on;");
            resultList.add(TAB + "tcp_nodelay on;");
            resultList.add(TAB + "keepalive_timeout 20;");
            resultList.add(TAB + "types_hash_max_size 2048;");
            resultList.add(TAB + "server_tokens off;");
            resultList.add(TAB + "client_max_body_size 50M; ");
            resultList.add("");
            resultList.add(TAB + "server_names_hash_bucket_size 128;");
            resultList.add(TAB + "# server_name_in_redirect off;");
            resultList.add("");
            resultList.add("");
            resultList.add(TAB + "include " + NginxConfStringContext.NGINX_EXTRA_MIME_TYPE() + ";");
            resultList.add(TAB + "default_type application/octet-stream;");
            resultList.add("");
            resultList.add(TAB + "##");
            resultList.add(TAB + "# SSL Settings");
            resultList.add(TAB + "##");
            resultList.add("");
            resultList.add(TAB + "ssl_protocols TLSv1 TLSv1.1 TLSv1.2 TLSv1.3; # Dropping SSLv3, ref: POODLE");
            resultList.add(TAB + "ssl_prefer_server_ciphers on;");
            resultList.add("");
            resultList.add(TAB + "##");
            resultList.add(TAB + "# Logging Settings");
            resultList.add(TAB + "##");
            resultList.add("");
            resultList.add(TAB + "# # nginx default log format ");
            resultList.add(TAB + "# log_format main '$remote_addr - $remote_user [$time_local] '");
            resultList.add(TAB + "# '\"$request\" $status $body_bytes_sent '");
            resultList.add(TAB + "# '\"$http_referer\" \"$http_user_agent\" \"$request_time\"';");
            resultList.add("");
            resultList.add(TAB + "# log format");
            resultList.add(TAB + "log_format main '" + GeneralConfig.ADMIN_SETTING.getNGINX_LOG_FORMAT().trim() + "';");
            resultList.add("");
            resultList.add("");
            resultList.add(TAB + "access_log /var/log/nginx/access.log main;");
            resultList.add(TAB + "error_log /var/log/nginx/error.log;");
            resultList.add("");
            resultList.add(TAB + "##");
            resultList.add(TAB + "# Gzip Settings");
            resultList.add(TAB + "##");
            resultList.add("");
            resultList.add(TAB + "gzip on;");
            resultList.add("");
            resultList.add(TAB + "# gzip_vary on;");
            resultList.add(TAB + "# gzip_proxied any;");
            resultList.add(TAB + "# gzip_comp_level 6;");
            resultList.add(TAB + "# gzip_buffers 16 8k;");
            resultList.add(TAB + "# gzip_http_version 1.1;");
            resultList.add(TAB + "# gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;");
            resultList.add("");
            resultList.add(TAB + "##");
            resultList.add(TAB + "# Virtual Host Configs");
            resultList.add(TAB + "##");
            resultList.add(TAB + "include " + NginxConfStringContext.NGINX_EXTRA_CONF_DIR() + "/*.conf;");
            resultList.add(TAB + "### root 도메인도 이 파일에서 관리한다.");
            resultList.add(TAB + "include " + NginxConfStringContext.NGINX_EXTRA_SITES_DIR() + "/*;");
            resultList.add("");
            resultList.add("}");
            resultList.add("");
            resultList.add("");

            list.addAll(resultList);

        } catch (Exception e) {
            log.error(e + "  " + e.getMessage(), e);
        }

        return list;
    }

}
