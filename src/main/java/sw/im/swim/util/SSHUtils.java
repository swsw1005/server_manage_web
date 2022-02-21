package sw.im.swim.util;

import java.io.InputStream;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHUtils {

    /**
     * <PRE>
     * String username, String host, int port, String password
     * </PRE>
     *
     * @param username
     * @param host
     * @param port
     * @param password
     * @throws JSchException
     */
    public SSHUtils(String username, String host, int port, String password) throws JSchException {
        this.username = username;
        this.host = host;
        this.port = port;
        this.password = password;

        connectSSH();
    }

    private final String username;
    private final String host;
    private final int port;
    private final String password;

    private Session session;
    private ChannelExec channelExec;

    private void connectSSH() throws JSchException {
        session = new JSch().getSession(username, host, port);
        session.setPassword(password);
        // 호스트 정보를 검사하지 않도록 설정
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
    }

    public String getSSHResponse(String command) {
        StringBuilder response = new StringBuilder();
        try {
            connectSSH();
            channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(command);

            InputStream inputStream = channelExec.getInputStream();
            channelExec.connect();

            byte[] buffer = new byte[8192];
            int decodedLength;

            while ((decodedLength = inputStream.read(buffer, 0, buffer.length)) > 0) {
                response.append(new String(buffer, 0, decodedLength));

            }

        } catch (Exception e) {
        } finally {
            this.disConnectSSH();
        }
        return response.toString();
    }

    public void command(String command) {
        try {
            connectSSH();
            // 실행할 channel 생성
            channelExec = (ChannelExec) session.openChannel("exec");

            // 실행할 command 설정
            channelExec.setCommand(command);
            // command 실행
            channelExec.connect();

        } catch (Exception e) {
        } finally {
            this.disConnectSSH();
        }
    }

    public void disConnectSSH() {
        if (session != null) {
            session.disconnect();
        }
        if (channelExec != null) {
            channelExec.disconnect();
        }
    }

}
