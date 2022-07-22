package com.bca.adam.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class MD5Crypto {

    public static String hash(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            // for simplicity, if md5 hashing fails, return default hash with value = qwepoi
            // DON'T DO THIS IN PRODUCTION!!! YOU'VE BEEN WARNED!!!
            return "14c9c680b61b8aa0f591a51367eabf9b";
        }
    }
}
