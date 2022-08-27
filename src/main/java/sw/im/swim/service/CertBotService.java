package sw.im.swim.service;

import com.caffeine.lib.process.ProcessExecutor;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import sw.im.swim.bean.dto.NginxServerEntityDto;
import sw.im.swim.config.GeneralConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class CertBotService {

    public static final String CERTBOT_SCRIPT_ROOTDIR() {

        final String user = GeneralConfig.ADMIN_SETTING.getSERVER_MANAGER_USER().trim().toLowerCase();

        if (user.equals("root")) {
            return "/root/certbot";
        } else {
            return "/home/" + user + "/certbot";
        }


    }

    public static final String CERTBOT_SCRIPT_FILE() {
        return CERTBOT_SCRIPT_ROOTDIR() + "/run.sh";
    }


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

        final String certbotScriptDir = CERTBOT_SCRIPT_ROOTDIR();

        final String certbotScriptFile = CERTBOT_SCRIPT_FILE();

        try {
            File certbotRootDir = new File(certbotScriptDir);

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
            dockerCertbotScript.add("  -v '" + certbotScriptDir + "/etc/letsencrypt/:/etc/letsencrypt/' \\");
            dockerCertbotScript.add("  -v '" + certbotScriptDir + "/var/lib/letsencrypt/:/var/lib/letsencrypt/' \\");
            dockerCertbotScript.add("  certbot/dns-google  \\");
            dockerCertbotScript.add("  certonly " + domains + " \\");
            dockerCertbotScript.add("  -m " + email + "  --agree-tos  --manual --preferred-challenges dns \\");
            dockerCertbotScript.add("  --server https://acme-v02.api.letsencrypt.org/directory");

            FileUtils.deleteQuietly(new File(certbotScriptFile));
            FileUtils.writeLines(new File(certbotScriptFile), "UTF-8", dockerCertbotScript, System.lineSeparator());

            log.info("file created =>  " + certbotScriptFile + "  " + new File(certbotScriptFile).exists());

            String[] chmodArr = {"sh", "-c", "chmod 755 " + certbotScriptFile};
            ProcessExecutor.runCommand(chmodArr);

        } catch (IOException e) {
            log.error(e + "  " + e.getMessage());
        } catch (Exception e) {
            log.error(e + "  " + e.getMessage(), e);
        }

    }

}
