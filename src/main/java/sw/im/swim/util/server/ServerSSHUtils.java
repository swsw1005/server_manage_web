package sw.im.swim.util.server;

import sw.im.swim.bean.dto.ServerInfoEntityDto;
import sw.im.swim.util.SSHUtils;

import java.util.HashSet;
import java.util.Set;

public class ServerSSHUtils {


    public static final boolean serverConnected(ServerInfoEntityDto dto) {

        SSHUtils utils = null;
        try {
            String cmd = "date";
            utils = new SSHUtils(dto.getId(), dto.getIp(), dto.getInnerSSHPort(), dto.getPassword());

            String result = utils.getSSHResponse(cmd);

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
            utils = new SSHUtils(dto.getId(), dto.getIp(), dto.getInnerSSHPort(), dto.getPassword());

            String result = utils.getSSHResponse(cmd);

        } catch (Exception e) {
            try {
                utils.disConnectSSH();
            } catch (Exception ex) {
            }
            throw new Exception();
        }
    }

    public static final boolean banIP(ServerInfoEntityDto dto, final String ip) {

        SSHUtils utils = null;
        try {
            String cmd = "fail2ban-client set sshd unbanip " + ip;
            utils = new SSHUtils(dto.getId(), dto.getIp(), dto.getInnerSSHPort(), dto.getPassword());

            String result = utils.getSSHResponse(cmd);


        } catch (Exception e) {
            try {
                utils.disConnectSSH();
            } catch (Exception ex) {
            }
        }
        return false;
    }

    public static final boolean unbanIP(ServerInfoEntityDto dto, final String ip) {

        SSHUtils utils = null;
        try {
            String cmd = "fail2ban-client set sshd banip " + ip;
            utils = new SSHUtils(dto.getId(), dto.getIp(), dto.getInnerSSHPort(), dto.getPassword());

            String result = utils.getSSHResponse(cmd);

        } catch (Exception e) {
            try {
                utils.disConnectSSH();
            } catch (Exception ex) {
            }
        }
        return false;
    }

    public static final Set<String> banIPs(ServerInfoEntityDto dto, final String ip) {
        Set<String> set = new HashSet<>();
        SSHUtils utils = null;
        try {
            String cmd = "fail2ban-client status sshd | grep Banned ";

            String result = utils.getSSHResponse(cmd);

            String[] resultArr = result.split("Banned IP list:");

            String[] ipArr = resultArr[1].split(" ");

            for (int i = 0; i < ipArr.length; i++) {

                if (ipArr[i].length() > 4) {
                    set.add(ipArr[i].trim());
                }
            }

        } catch (Exception e) {
            try {
                utils.disConnectSSH();
            } catch (Exception ex) {
            }
        }
        return set;
    }

}
