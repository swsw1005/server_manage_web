package sw.im.swim.util;

import java.util.Base64;
import java.util.Base64.*;

public class Base64Utils {

    public static String decode(byte[] origin) {
        try {
            Decoder decoder = Base64.getDecoder();
            byte[] a = decoder.decode(origin);
            return new String(a);
        } catch (Exception e) {
        }
        return null;
    }

    public static String decode(String origin) {
        try {
            return decode(origin.getBytes());
        } catch (Exception e) {
        }
        return null;
    }

    public static String encode(byte[] origin) {
        try {
            Encoder encoder = Base64.getEncoder();
            byte[] a = encoder.encode(origin);
            return new String(a);
        } catch (Exception e) {
        }
        return null;
    }

    public static String encode(String origin) {
        try {
            return encode(origin.getBytes("UTF-8"));
        } catch (Exception e) {
        }
        return null;
    }

}
