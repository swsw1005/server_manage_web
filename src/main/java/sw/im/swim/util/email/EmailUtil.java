package sw.im.swim.util.email;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Slf4j
public class EmailUtil {

    public static void sendEmail(
            final String SMTP_USER,
            final String SMTP_PASSWORD,
            final String SMTP_HOST,
            final int SMTP_PORT,
            final boolean SMTP_STARTTLS_ENABLE,
            final boolean SMTP_AUTH,
            final boolean SMTP_SSL_ENABLE,
            final String SMTP_SSL_TRUST,
            final String TO_EMAIL,
            final String title,
            final String BODY
    ) throws Exception {
        try {

            // 2. Property에 SMTP 서버 정보 설정
            Properties prop = new Properties();
            prop.put("mail.smtp.host", SMTP_HOST);
            prop.put("mail.smtp.port", SMTP_PORT);
            prop.put("mail.smtp.auth", SMTP_AUTH);

            if (SMTP_STARTTLS_ENABLE) {
                prop.put("mail.smtp.starttls.enable", SMTP_SSL_ENABLE);
            } else {
                prop.put("mail.smtp.ssl.enable", SMTP_SSL_ENABLE);
                prop.put("mail.smtp.ssl.trust", SMTP_SSL_TRUST);
            }

            log.error(new Gson().toJson(prop));

            // 3. SMTP 서버정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스 생성

            Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
                }
            });

            // 4. Message 클래스의 객체를 사용하여 수신자와 내용, 제목의 메시지를 작성한다.
            // 5. Transport 클래스를 사용하여 작성한 메세지를 전달한다.

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER));

            // 수신자 메일 주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO_EMAIL));
            // Subject
            message.setSubject(title, "UTF-8");
            // Text
            message.setText(BODY, "UTF-8", "html");

            log.error("mail send start");

            Transport.send(message);    // send message

            log.error("mail send end");

        } catch (Exception e) {
            log.error(e.getMessage() + "=============", e);
            throw new Exception(e.getMessage() + "====");
        }
    }


    public static void main(String[] args) {
        try {

            EmailUtil.sendEmail("swim.logger92@gmail.com",
                    "swsw1005!@#",
                    "smtp.gmail.com",
                    587,
                    true,
                    true,
                    true,
                    "smtp.gmail.com",
                    "swsw1005@gmail.com",
                    "tes",
                    "test test"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
