package kr.swim.nginx.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthTokenUtil {

    private static final String AUTH_TOKEN_FILE_DIR = "/opt/nginxController";
    private static final String AUTH_TOKEN_FILE_PATH = AUTH_TOKEN_FILE_DIR + "/token.txt";

    private static String AUTH_TOKEN = "";

    public static final String GET_AUTH_TOKEN() throws IOException {

        new File(AUTH_TOKEN_FILE_DIR).mkdirs();

        if (AUTH_TOKEN == null || AUTH_TOKEN.length() < 5) {
            GENERATE_NEW_TOKEN();
        }
        return AUTH_TOKEN;
    }

    private static final void GENERATE_NEW_TOKEN() throws IOException {

        new File(AUTH_TOKEN_FILE_DIR).mkdirs();

        try {

            File tokenFile = new File(AUTH_TOKEN_FILE_PATH);

            String token_ = FileUtils.readFileToString(tokenFile, "UTF-8");

            token_ = token_.replace(" ", "");
            token_ = token_.replace(" ", "");

            AUTH_TOKEN = token_;

        } catch (Exception e) {
            log.error(e + "  " + e.getMessage(), e);
        }

        if (AUTH_TOKEN == null || AUTH_TOKEN.length() < 5) {

            FileUtils.deleteQuietly(new File(AUTH_TOKEN_FILE_PATH));

            String token = UUID.randomUUID().toString().substring(0, 10);

            FileUtils.write(new File(AUTH_TOKEN_FILE_PATH), token, "UTF-8");
            AUTH_TOKEN = token;
        }

    }


}
