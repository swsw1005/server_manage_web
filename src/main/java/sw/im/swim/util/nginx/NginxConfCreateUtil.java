package sw.im.swim.util.nginx;

import sw.im.swim.bean.dto.DomainEntityDto;
import sw.im.swim.bean.dto.NginxPolicyEntityDto;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.util.date.DateFormatUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NginxConfCreateUtil {

    private static final String
            // ------------------------------------
            TAB = "    ";

    private static final String TAB2 = "    " + "    ";

    private static final String[] HEAD(int workerProcessed, int workerConnections) {

        String[] result = {"user www-data;", "worker_processes " + workerProcessed + ";", "pid /run/nginx.pid;",
                "include /etc/nginx/modules-enabled/*.conf;", "", "events {",
                // ------------------------------------
                TAB + "worker_connections " + workerConnections + ";",
                // ------------------------------------
                TAB + "# multi_accept on;", "}", ""};

        return result;
    }

    private static final List<String> HTTP_START(List<String> rootDomainList) {

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
        resultList.add("");
        resultList.add(TAB + "server_names_hash_bucket_size 128;");
        resultList.add(TAB + "# server_name_in_redirect off;");
        resultList.add("");

        resultList.add(TAB + "include /etc/nginx/mime.types;");
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
        resultList.add(TAB + "# # 이 부분을 수정");
        resultList.add(TAB + "# log_format main '$remote_addr - $remote_user [$time_local] '");
        resultList.add(TAB + "# '\"$request\" $status $body_bytes_sent '");
        resultList.add(TAB + "# '\"$http_referer\" \"$http_user_agent\" \"$request_time\"';");
        resultList.add("");
        resultList.add(TAB + "# swim_log");
        resultList.add(TAB + "log_format main '[$time_local] | $remote_addr | $remote_user '");
        resultList.add(TAB + "'\"$request\" $status $body_bytes_sent '");
        resultList.add(TAB + "'\"$http_referer\" \"$http_user_agent\" \"$request_time\"';");
        resultList.add("");
        resultList.add(TAB + "# access_log /var/log/nginx/access.log main;");
        resultList.add(TAB + "# error_log /var/log/nginx/error.log main;");
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
        resultList.add(TAB
                + "# gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;");
        resultList.add("");
        resultList.add(TAB + "##");
        resultList.add(TAB + "# Virtual Host Configs");
        resultList.add(TAB + "##");
        resultList.add(TAB + "include /etc/nginx/conf.d/*.conf;");
        resultList.add(TAB + "#include /etc/nginx/sites-enabled/*;");
        resultList.add("");
        resultList.add("");

        resultList.addAll(rootDomainList);

//        resultList.add(TAB + "server {");
//        resultList.add(TAB + "    listen 80;");
//        resultList.add(TAB + "    # server_name swfa.pw;");
//        resultList.add(TAB + "    proxy_set_header X-Scope-OrgID docker-ha;");
//        resultList.add(TAB + "    add_header      X-Frame-Options SAMEORIGIN; ");
//        resultList.add(TAB + "    add_header      X-Content-Type-Options nosniff;");
//        resultList.add(TAB + "    add_header      Cache-Control \"no-cache, no-store, must-revalidate\";");
//        resultList.add(TAB + "    proxy_cookie_path / \"/; SameSite=lax; HTTPOnly; Secure\";");
//        resultList.add("");
//        resultList.add(TAB + "    location / {");
//        resultList.add(TAB + "        access_log /var/log/nginx/ROOT/access.log;");
//        resultList.add(TAB + "        error_log /var/log/nginx/ROOT/error.log;");
//        resultList.add("");
//        resultList.add(TAB + "        root /var/lib/html;");
//        resultList.add(TAB + "        index index.html index.htm;");
//        resultList.add(TAB + "    }");
//        resultList.add("");
//        resultList.add("");
//        resultList.add(TAB + "}");


        resultList.add("");
        resultList.add("");
        resultList.add(TAB + "#mail {");
        resultList.add(TAB + "#	# See sample authentication script at:");
        resultList.add(TAB + "#	# http://wiki.nginx.org/ImapAuthenticateWithApachePhpScript");
        resultList.add(TAB + "#");
        resultList.add(TAB + "#	# auth_http localhost/auth.php;");
        resultList.add(TAB + "#	# pop3_capabilities \"TOP\" \"USER\";");
        resultList.add(TAB + "#	# imap_capabilities \"IMAP4rev1\" \"UIDPLUS\";");
        resultList.add(TAB + "#");
        resultList.add(TAB + "#	server {");
        resultList.add(TAB + "#		listen     localhost:110;");
        resultList.add(TAB + "#		protocol   pop3;");
        resultList.add(TAB + "#		proxy      on;");
        resultList.add(TAB + "#	}");
        resultList.add(TAB + "#");
        resultList.add(TAB + "#	server {");
        resultList.add(TAB + "#		listen     localhost:143;");
        resultList.add(TAB + "#		protocol   imap;");
        resultList.add(TAB + "#		proxy      on;");
        resultList.add(TAB + "#	}");
        resultList.add(TAB + "#}");
        resultList.add("");
        resultList.add("");

        return resultList;
    }

    private static final List<String> ONE_SERVER_BLOCK(NginxServerEntityDto nginxServerEntityDto) {

        List<String> list = new ArrayList<>();
        try {

            final String NAME = nginxServerEntityDto.getName();
            final String IP = nginxServerEntityDto.getWebServerEntity().getServerInfoEntity().getIp();
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

    private static final List<String> ROOT_SERVER_BLOCK(NginxServerEntityDto nginxServerEntityDto) {

        List<String> list = new ArrayList<>();
        try {

            final String NAME = nginxServerEntityDto.getName();
            final String IP = nginxServerEntityDto.getWebServerEntity().getServerInfoEntity().getIp();
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

            list.add(TAB + TAB + "root /var/www/html;");
            list.add(TAB + TAB + "index index.html index.htm;");
            list.add(TAB + "}");
            list.add("");
            list.add("}");

        } catch (Exception e) {
        }

        return list;
    }

    public final static List<String> CREATE_NEW_CONF_TEXT(NginxPolicyEntityDto policyEntityDto,
                                                          List<NginxServerEntityDto> nginxServerEntityList) throws Exception {

        List<String> list = new ArrayList<>();
        List<String> list1 = AskiiArtUtil.CREATE_NGINX_BANNER(DateFormatUtil.DATE_FORMAT_yyyy_MM_dd.format(new Date()));
        List<String> list2 = AskiiArtUtil.CREATE_NGINX_BANNER(DateFormatUtil.DATE_FORMAT_HH_mm_ss.format(new Date()));

        List<String> rootDomainList = new ArrayList<>();
        List<String> subDomainList = new ArrayList<>();

        list.addAll(list1);
        list.addAll(list2);

        final int processed = policyEntityDto.getWorkerProcessed();
        final int connections = policyEntityDto.getWorkerConnections();

        DomainEntityDto ROOT_DOMAIN = policyEntityDto.getDomainEntity();

        String pre_ip = "";

        for (int i = 0; i < nginxServerEntityList.size(); i++) {
            subDomainList.add("");
            NginxServerEntityDto nginxServerEntity = nginxServerEntityList.get(i);

            String tempIp = nginxServerEntity.getWebServerEntity().getServerInfoEntity().getIp();

            if (nginxServerEntity.getDomainEntity().getDomain().equals(ROOT_DOMAIN.getDomain())) {
                // 루트 도메인일때
                rootDomainList.add("");
                rootDomainList.add("");
                rootDomainList.add("");
                List<String> ip_banner = AskiiArtUtil.CREATE_NGINX_BANNER("MAIN NGINX =");
                rootDomainList.addAll(ip_banner);

                List<String> tempList = ROOT_SERVER_BLOCK(nginxServerEntity);

                rootDomainList.addAll(tempList);

                rootDomainList.add("");
                rootDomainList.add("");

            } else {
                // 서브 도메인일때
                if (!tempIp.equals(pre_ip)) {
                    subDomainList.add("");
                    subDomainList.add("");
                    subDomainList.add("");
                    List<String> ip_banner = AskiiArtUtil.CREATE_NGINX_BANNER(tempIp.replace("192.168", "X.X"));
                    subDomainList.addAll(ip_banner);
                }
                pre_ip = tempIp;

                List<String> tempList = ONE_SERVER_BLOCK(nginxServerEntity);

                subDomainList.addAll(tempList);
            }

        }

        String[] HEAD = HEAD(processed, connections);

        for (int i = 0; i < HEAD.length; i++) {
            list.add(HEAD[i]);
        }

        List<String> HTTP_START = HTTP_START(rootDomainList);
        list.addAll(HTTP_START);

        list.addAll(subDomainList);

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
