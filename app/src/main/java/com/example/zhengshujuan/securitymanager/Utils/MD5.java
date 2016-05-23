package com.example.zhengshujuan.securitymanager.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zhengshujuan on 2016/5/17.
 */
public class MD5 {
    public static String encode(String password) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(password.getBytes());
            for (byte b : bytes) {
                int c=b&0xff;
                String mString = Integer.toHexString(c);
                if (mString.length() == 1) {
                    mString = 0 + mString;
                }
                sb.append(mString);

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
