package gambol.examples.adminauth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhenbao.zhou on 16/12/14.
 */
public class Auth {
    private final static String SPLITTER = "@@";
    private final static String SALT = "BBAE";

    public static void main(String[] args) {
        long localTime = System.currentTimeMillis() / 1000 + 5 * 60;
        String kk = getMD5Code(SALT+ localTime);

        System.err.println("" + localTime + SPLITTER + kk );
    }


    public static String getMD5Code(String str) {
        String result = null;
        try {
            result = new String(str);
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteToString(md.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    /**全局数组**/
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

}
