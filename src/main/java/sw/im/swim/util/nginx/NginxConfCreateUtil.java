package sw.im.swim.util.nginx;

import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.util.date.DateFormatUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NginxConfCreateUtil {

    private static final String TAB = "    ";

    private static final String TAB2 = "    " + "    ";

    private static final String[] HEAD(int workerProcessed, int workerConnections) {

        String[] result = {"user www-data;", "worker_processes " + workerProcessed + ";", "pid /run/nginx.pid;", "include /etc/nginx/modules-enabled/*.conf;", "", "events {", TAB + "worker_connections " + workerConnections + ";", TAB + "# multi_accept on;", "}", ""};

        return result;
    }

    private static final String[] HTTP_START() {

        String[] result = {"http {", "", TAB + "##", TAB + "# Basic Settings", TAB + "##", "", TAB + "sendfile on;", TAB + "tcp_nopush on;", TAB + "tcp_nodelay on;", TAB + "keepalive_timeout 20;", TAB + "types_hash_max_size 2048;", TAB + "server_tokens off;", "", TAB + "server_names_hash_bucket_size 128;", TAB + "# server_name_in_redirect off;", "", TAB + "include /etc/nginx/mime.types;", TAB + "default_type application/octet-stream;", "", TAB + "##", TAB + "# SSL Settings", TAB + "##", "", TAB + "ssl_protocols TLSv1 TLSv1.1 TLSv1.2 TLSv1.3; # Dropping SSLv3, ref: POODLE", TAB + "ssl_prefer_server_ciphers on;", "", TAB + "##", TAB + "# Logging Settings", TAB + "##", "", TAB + "# # 이 부분을 수정", TAB + "# log_format main '$remote_addr - $remote_user [$time_local] '", TAB + "# '\"$request\" $status $body_bytes_sent '", TAB + "# '\"$http_referer\" \"$http_user_agent\" \"$request_time\"';", "", TAB + "# swim_log", TAB + "log_format main '[$time_local] | $remote_addr | $remote_user '", TAB + "'\"$request\" $status $body_bytes_sent '", TAB + "'\"$http_referer\" \"$http_user_agent\" \"$request_time\"';", "", TAB + "# access_log /var/log/nginx/access.log main;", TAB + "# error_log /var/log/nginx/error.log main;", "", TAB + "##", TAB + "# Gzip Settings", TAB + "##", "", TAB + "gzip on;", "", TAB + "# gzip_vary on;", TAB + "# gzip_proxied any;", TAB + "# gzip_comp_level 6;", TAB + "# gzip_buffers 16 8k;", TAB + "# gzip_http_version 1.1;", TAB + "# gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;", "", TAB + "##", TAB + "# Virtual Host Configs", TAB + "##", TAB + "include /etc/nginx/conf.d/*.conf;", TAB + "include /etc/nginx/sites-enabled/*;", "", "", TAB + "server {", TAB + "    listen 80;", TAB + "    # server_name swfa.pw;", TAB + "    proxy_set_header X-Scope-OrgID docker-ha;", TAB + "    add_header      X-Frame-Options SAMEORIGIN; ", TAB + "    add_header      X-Content-Type-Options nosniff;", TAB + "    add_header      Cache-Control \"no-cache, no-store, must-revalidate\";", TAB + "    proxy_cookie_path / \"/; SameSite=lax; HTTPOnly; Secure\";", "", TAB + "    location / {", TAB + "        access_log /var/log/nginx/ROOT/access.log;", TAB + "        error_log /var/log/nginx/ROOT/error.log;", "", TAB + "        root /var/lib/html;", TAB + "        index index.html index.htm;", TAB + "    }", "", "", TAB + "}", "", "", TAB + "#mail {", TAB + "#	# See sample authentication script at:", TAB + "#	# http://wiki.nginx.org/ImapAuthenticateWithApachePhpScript", TAB + "#", TAB + "#	# auth_http localhost/auth.php;", TAB + "#	# pop3_capabilities \"TOP\" \"USER\";", TAB + "#	# imap_capabilities \"IMAP4rev1\" \"UIDPLUS\";", TAB + "#", TAB + "#	server {", TAB + "#		listen     localhost:110;", TAB + "#		protocol   pop3;", TAB + "#		proxy      on;", TAB + "#	}", TAB + "#", TAB + "#	server {", TAB + "#		listen     localhost:143;", TAB + "#		protocol   imap;", TAB + "#		proxy      on;", TAB + "#	}", TAB + "#}", "", ""};

        return result;
    }

    private static final List<String> ONE_SERVER_BLOCK(NginxServerEntityDto nginxServerEntityDto) {


        List<String> list = new ArrayList<>();
        try {

            final String NAME = nginxServerEntityDto.getName();
            final String IP = nginxServerEntityDto.getWebServerEntity().getIp();
            final String HTTPS = nginxServerEntityDto.getWebServerEntity().HTTPS_PREFIX();
            final String ADDRESS = nginxServerEntityDto.getWebServerEntity().getAddress();
            final String DOMAIN = nginxServerEntityDto.getDomainEntity().getDomain();
            final String FAVICON = nginxServerEntityDto.getFaviconEntity().getPath();
            final boolean SEPERATE_LOG = nginxServerEntityDto.isSeperateLog();

            list.add("#-------------------------------------");
            list.add("## " + ADDRESS + " | " + NAME);
            list.add("#-------------------------------------");
            list.add("");
            list.add("server {");
            list.add(TAB + "server_name " + DOMAIN + ";");
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
            list.add(TAB + "location / {");
            list.add("");

            if (SEPERATE_LOG) {
                list.add(TAB + TAB + "access_log /var/log/nginx/" + DOMAIN + "/access.log  main;");
                list.add(TAB + TAB + "error_log /var/log/nginx/" + DOMAIN + "/error.log;");
                list.add("");

                try {
                    new File("/var/log/nginx/" + DOMAIN).mkdirs();
                } catch (Exception e) {
                }
            }

            list.add(TAB + TAB + "proxy_redirect off;");
            list.add(TAB + TAB + "proxy_pass_header Server;");
            list.add(TAB + TAB + "proxy_set_header Host $http_host;");
            list.add(TAB + TAB + "proxy_set_header X-Real-IP $remote_addr;");
            list.add(TAB + TAB + "proxy_set_header X-Scheme $scheme;");
            list.add(TAB + TAB + "proxy_pass " + HTTPS + ADDRESS + ";");
            list.add(TAB + "}");
            list.add("");
            list.add("}");


        } catch (Exception e) {
        }

        return list;
    }


    public final static List<String> CREATE_NEW_CONF_TEXT(NginxPolicyEntityDto policyEntityDto, List<NginxServerEntityDto> nginxServerEntityList) throws Exception {

        List<String> list = AskiiArtUtil.CREATE_NGINX_BANNER(DateFormatUtil.DATE_FORMAT_yyyyMMdd_HHmmss.format(new Date()));
        final int processed = policyEntityDto.getWorkerProcessed();
        final int connections = policyEntityDto.getWorkerConnections();

        String[] HEAD = HEAD(processed, connections);

        for (int i = 0; i < HEAD.length; i++) {
            list.add(HEAD[i]);
        }

        String[] HTTP_START = HTTP_START();

        for (int i = 0; i < HTTP_START.length; i++) {
            list.add(HTTP_START[i]);
        }

        String pre_ip = "";

        for (int i = 0; i < nginxServerEntityList.size(); i++) {
            list.add("");
            NginxServerEntityDto nginxServerEntity = nginxServerEntityList.get(i);

            String tempIp = nginxServerEntity.getWebServerEntity().getIp();
            if (!tempIp.equals(pre_ip)) {
                list.add("");
                list.add("");
                list.add("");
                List<String> ip_banner = AskiiArtUtil.CREATE_NGINX_BANNER(tempIp);
                list.addAll(ip_banner);
            }
            pre_ip = tempIp;

            List<String> tempList = ONE_SERVER_BLOCK(nginxServerEntity);

            list.addAll(tempList);
        }

        list.add("");
        list.add("");
        list.add("###################################");
        list.add("");
        list.add("}");
        list.add("");
        list.add("");

        return list;
    }

}
