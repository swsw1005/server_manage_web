package sw.im.swim.util.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.SocketException;

@Slf4j
public class PortCheckUtil {
    public static boolean available(int port) {


        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            if (port < 10 || port > 20000) {
                throw new IllegalArgumentException("Invalid start port: " + port);
            }

            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;

        } catch (SocketException e) {
            log.warn(e + "  " + e.getMessage());
        } catch (IOException e) {
            log.warn(e + "  " + e.getMessage());
        } catch (Exception e) {
            log.warn(e + "  " + e.getMessage(), e);
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    log.warn(e + "  " + e.getMessage());
                }
            }
        }

        return false;
    }

}
