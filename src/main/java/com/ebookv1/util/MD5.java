package com.ebookv1.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    //password encryption
    public static String codeEncryption(String s){
        try{
            MessageDigest message = MessageDigest.getInstance("MD5");
            message.update(s.getBytes());
            byte[] bytes = message.digest();
            int i;
            StringBuffer sb = new StringBuffer("");
            for(int off=0;off<bytes.length;off++){
                i=bytes[off];
                if(i<0) i+=265;
                if(i<16) sb.append("0");
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();

        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }
/*
for test the code encryption
 */
    public static void main(String[] args) {
        System.out.println(codeEncryption("123456"));
    }
}
