package sw.im.swim.service;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.config.GeneralConfig;
import sw.im.swim.util.process.ProcessExecUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class CertBotService {

    public static final String CERTBOT_SCRIPT_ROOTDIR = "/root/certbot";
    public static final String CERTBOT_SCRIPT_FILE = CERTBOT_SCRIPT_ROOTDIR + "/run.sh";


    /**
     * <PRE>
     * docker certbot 스크립트 파일 만든다.
     * <p>
     * docker run -it --rm --name certbot \
     * -v '$PWD/etc/letsencrypt:/etc/letsencrypt' \
     * -v '$PWD/var/lib/letsencrypt:/var/lib/letsencrypt' \
     * certbot/dns-google \
     * certonly -d 'swim-playground.com' -d '*.swim-playground.com' \
     * -m email@gmail.com \
     * --agree-tos  \
     * --manual --preferred-challenges dns \
     * --server https://acme-v02.api.letsencrypt.org/directory
     *
     * </PRE>
     *
     * @param list
     */
    public static final void CREATE_CERTBOT_FILE(List<NginxServerEntityDto> list) {

        try {
            File certbotRootDir = new File(CERTBOT_SCRIPT_ROOTDIR);

            if (!certbotRootDir.exists()) {
                try {
                    FileUtils.forceMkdir(certbotRootDir);
                } catch (IOException e) {
                    log.warn("mkdir exception  " + e.getMessage());
                } catch (Exception e) {
                    log.warn("mkdir exception  " + e.getMessage(), e);
                }
            }

            String email = GeneralConfig.ADMIN_SETTING.getADMIN_EMAIL();

            String rootDomain = GeneralConfig.ADMIN_SETTING.getROOT_DOMAIN();

            Set<String> domainSet = new HashSet<>();

            list.forEach(nginxServerEntityDto -> {
                domainSet.add(nginxServerEntityDto.getDomainEntity().getCertDomain());
            });

            log.info("domain Set :: " + new Gson().toJson(domainSet));

            String domains = " -d " + rootDomain + " ";
            for (String s : domainSet) {
                domains += (" -d " + s + " ");
            }

            List<String> dockerCertbotScript = new ArrayList<>();

            dockerCertbotScript.add("docker run -it --rm --name certbot  \\");
            dockerCertbotScript.add("  -v '" + CERTBOT_SCRIPT_ROOTDIR + "/etc/letsencrypt/:/etc/letsencrypt/' \\");
            dockerCertbotScript.add("  -v '" + CERTBOT_SCRIPT_ROOTDIR + "/var/lib/letsencrypt/:/var/lib/letsencrypt/' \\");
            dockerCertbotScript.add("  certbot/dns-google  \\");
            dockerCertbotScript.add("  certonly " + domains + " \\");
            dockerCertbotScript.add("  -m " + email + "  --agree-tos  --manual --preferred-challenges dns \\");
            dockerCertbotScript.add("  --server https://acme-v02.api.letsencrypt.org/directory");

            FileUtils.deleteQuietly(new File(CERTBOT_SCRIPT_FILE));
            FileUtils.writeLines(new File(CERTBOT_SCRIPT_FILE), "UTF-8", dockerCertbotScript, System.lineSeparator());

            log.info("file created =>  " + CERTBOT_SCRIPT_FILE + "  " + new File(CERTBOT_SCRIPT_FILE).exists());

            String[] chmodArr = {
                    "sh", "-c", "chmod 755 " + CERTBOT_SCRIPT_FILE
            };
            ProcessExecUtil.RUN_READ_COMMAND(chmodArr);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}
