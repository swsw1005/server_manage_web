package kr.swim.nginx.util;

import kr.swim.nginx.controller.NginxController;
import kr.swim.util.process.ProcessExecutor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NginxConfWriteUtil {

    public static final String NGINX_CONF_ROOT_DIR = "/etc/nginx";
    public static final String NGINX_CONF_FILE_PATH = NGINX_CONF_ROOT_DIR + "/nginx.conf";
    public static final String NGINX_CONF_FILE_BACKUP_PATH = NGINX_CONF_FILE_PATH + "_back";
    public static final String NGINX_SITES_DIR = NGINX_CONF_ROOT_DIR + "/sites-available";

    public static final SimpleDateFormat sdf = new SimpleDateFormat("_yyMMdd_HHmmss");


    public static void nginxJob(MultipartFile[] multipartFiles) throws IOException, InterruptedException {

        try {
            writeConfFiles(multipartFiles);
        } catch (Exception e) {
            log.error(e + "  " + e.getMessage(), e);
            restoreNginxConf();
        }

    }


    private static void backupNginxConf() throws IOException {
        FileUtils.deleteQuietly(new File(NGINX_CONF_FILE_BACKUP_PATH));
        FileUtils.moveFile(new File(NGINX_CONF_FILE_PATH), new File(NGINX_CONF_FILE_BACKUP_PATH));
    }


    private static void restoreNginxConf() throws IOException, InterruptedException {
        FileUtils.deleteQuietly(new File(NGINX_CONF_FILE_PATH));
        FileUtils.moveFile(new File(NGINX_CONF_FILE_BACKUP_PATH), new File(NGINX_CONF_FILE_PATH));

        new File(NGINX_SITES_DIR).delete();

        NginxController.NginxJob nginxJob = NginxController.NginxJob.reload;
        ProcessExecutor.runCommand(nginxJob.getCmd());
    }


    private static void writeConfFiles(MultipartFile[] multipartFiles) throws IOException, InterruptedException {
        final String dateSuffix = sdf.format(new Date());

        backupNginxConf();

        File tempDir = new File(NGINX_SITES_DIR + dateSuffix);
        tempDir.mkdirs();

        for (MultipartFile multipartFile : multipartFiles) {
            final String fileName = multipartFile.getOriginalFilename();

            if (fileName.equals("nginx.conf")) {
                writeConfFile(multipartFile, fileName, NGINX_CONF_ROOT_DIR);
            } else {
                writeConfFile(multipartFile, fileName, tempDir.getAbsolutePath());
            }
        }

        new File(NGINX_SITES_DIR).delete();

        FileUtils.moveDirectoryToDirectory(tempDir, new File(NGINX_SITES_DIR), true);

        log.debug("  tempDir        \t " + tempDir.getAbsolutePath() + "\t exist? " + tempDir.exists());
        log.debug(" NGINX_SITES_DIR \t " + new File(NGINX_SITES_DIR).getAbsolutePath() + "\t exist? " + new File(NGINX_SITES_DIR).exists());

        NginxController.NginxJob nginxJob = NginxController.NginxJob.reload;
        ProcessExecutor.runCommand(nginxJob.getCmd());
    }


    private static void writeConfFile(MultipartFile multipartFile, final String fileName, final String dir) throws IOException {

        final String filePath = dir + "/" + fileName;

        log.debug("filePath  =>  " + filePath);

        FileUtils.writeByteArrayToFile(new File(filePath), multipartFile.getBytes());

        File file = new File(filePath);

        if (file.exists() && file.length() > 20) {
            log.debug("file write complete ??   file.exists() = " + file.exists() + "   file.length() = " + file.length());
        } else {
            throw new IOException("file write seems fail");
        }

    }


}
