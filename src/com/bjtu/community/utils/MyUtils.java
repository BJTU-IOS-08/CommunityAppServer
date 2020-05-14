package com.bjtu.community.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MyUtils {
    public static String getRandomKey() {
        String src = "ZXCVBNMASDFGHJKLQWERTYUIOPzxcvbnmasdfghjklpouiytrewq";
        String random = "";
        for (int i = 0; i < 10; i++) {
            int temp = (int) ((Math.random() * 10000) % 52);
            random = random + src.charAt(temp);
        }
        return getMd5(random);
    }


    //获取MD5字符串
    public static String getMd5(String plainText) {

        StringBuilder buf = new StringBuilder("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public static boolean isTextEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isListEmpty(List list) {
        return list == null || list.size() == 0;
    }
}
