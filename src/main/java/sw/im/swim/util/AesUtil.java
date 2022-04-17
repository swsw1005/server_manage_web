package sw.im.swim.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import lombok.extern.slf4j.Slf4j;

/**
 * <ul>AES256Util
 * <p>
 * 암호화와 복호화 과정에서 동일한 키를 사용하는 대칭 키 알고리즘
 */
@Slf4j
public class AesUtil {

    public static byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    //암호화
    public static String encrypt(String str, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        String secretKey = key.substring(0, 32); //32byte 이하는 에러발생
        byte[] textBytes = str.getBytes("UTF-8");

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //AES모드는 AS-IS그대로 셋팅.
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
        return Base64.encodeBase64String(cipher.doFinal(textBytes));
    }

    //복호화
    public static String decrypt(String str, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        String secretKey = key.substring(0, 32);
        //byte[] textBytes = str.getBytes("UTF-8");
        byte[] textBytes = Base64.decodeBase64(str); //에러 방지를 위해 Base64로 인코딩 처리.

        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //AES모드는 AS-IS그대로 셋팅.
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
        return new String(cipher.doFinal(textBytes), "UTF-8");
    }


    public static void main(String[] args) {
        try {

            String[] arr = {
                    "privacy!@34",
                    "dntjddla0772",
                    "dntjddla0772!@",
                    "dntjddla0772!@!",
                    "q1w2e3",
                    "swkl0772",
                    "kl0772swkl",
                    "kl0772swkl!@",
                    "aaaaa"
            };

            HashMap<String, String> map = new HashMap<>();


            for (int i = 0; i < arr.length; i++) {

                final String a = arr[i];
                final String b = AesUtil.encrypt(a, "swsw1005swsw1005swsw1005swsw1005swsw1005swsw1005");

                map.put(a, b);

                log.debug("update admin_info set  password = '" + b + "'  where password = '" + a + "' ;");
                log.debug("update db_server_info set  password = '" + b + "'  where password = '" + a + "' ;");
                log.debug("update server_info set  ssh_password = '" + b + "'  where ssh_password = '" + a + "' ;");

            }

            for (int i = 0; i < arr.length; i++) {
                try {

                    final String a = arr[i];
                    final String b = AesUtil.encrypt(a, "swsw1005swsw1005swsw1005swsw1005swsw1005swsw1005");

                    final String c = AesUtil.decrypt(b, "swsw1005swsw1005swsw1005swsw1005swsw1005swsw1005");

                    log.debug(a + " \t " + b + " \t " + c);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}