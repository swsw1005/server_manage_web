package sw.im.swim.util.server;

import lombok.extern.slf4j.Slf4j;
import sw.im.swim.bean.dto.ServerInfoEntityDto;
import sw.im.swim.util.SSHUtils;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ServerSSHUtils {

    public static final boolean serverConnected(ServerInfoEntityDto dto) {

        SSHUtils utils = null;
        try {
            String cmd = "date";
            utils = new SSHUtils(dto.getIp(), dto.getSshPort(), dto.getId(), dto.getPassword());

            String result = utils.getSSHResponse(cmd);

            return true;

        } catch (Exception e) {
            try {
                utils.disConnectSSH();
            } catch (Exception ex) {
            }
        }
        return false;
    }

    public static final void serverConnectAssert(ServerInfoEntityDto dto) throws Exception {
        SSHUtils utils = null;
        try {
            String cmd = "date";
            utils = new SSHUtils(dto.getIp(), dto.getSshPort(), dto.getId(), dto.getPassword());

            String result = utils.getSSHResponse(cmd);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            throw new Exception("SSH FAIL | " + e.getLocalizedMessage());
        } finally {
            try {
                utils.disConnectSSH();
            } catch (Exception ex) {
            }
        }
    }

    public static final boolean banIP(ServerInfoEntityDto dto, final String ip) throws Exception {

        SSHUtils utils = null;
        try {
            String cmd = "fail2ban-client set sshd banip " + ip;
            utils = new SSHUtils(dto.getIp(), dto.getSshPort(), dto.getId(), dto.getPassword());

            String result = utils.getSSHResponse(cmd);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            throw new Exception("SSH FAIL | " + e.getLocalizedMessage());
        } finally {
            try {
                utils.disConnectSSH();
            } catch (Exception ex) {
            }
        }
        return false;
    }

    public static final boolean unbanIP(ServerInfoEntityDto dto, final String ip) throws Exception {

        SSHUtils utils = null;
        try {
            String cmd = "fail2ban-client unban " + ip;
            utils = new SSHUtils(dto.getIp(), dto.getSshPort(), dto.getId(), dto.getPassword());

            String result = utils.getSSHResponse(cmd);

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            throw new Exception("SSH FAIL | " + e.getLocalizedMessage());
        } finally {
            try {
                utils.disConnectSSH();
            } catch (Exception ex) {
            }
        }
        return false;
    }

    public static final Set<String> banIPs(ServerInfoEntityDto dto) throws Exception {
        Set<String> set = new HashSet<>();
        SSHUtils utils = null;
        try {
            String cmd = "fail2ban-client status sshd | grep Banned ";

            utils = new SSHUtils(dto.getIp(), dto.getSshPort(), dto.getId(), dto.getPassword());

            String result = utils.getSSHResponse(cmd);

            String[] resultArr = result.split("Banned IP list:");

            String[] ipArr = resultArr[1].split(" ");

            for (int i = 0; i < ipArr.length; i++) {
                if (ipArr[i].length() > 4) {
                    set.add(ipArr[i].trim());
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            log.warn("Maybe No Banned IP...");
        } catch (NullPointerException e) {
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            throw new Exception("SSH FAIL | " + e.getLocalizedMessage());
        } finally {
            try {
                utils.disConnectSSH();
            } catch (Exception ex) {
            }
        }
        return set;
    }

}
