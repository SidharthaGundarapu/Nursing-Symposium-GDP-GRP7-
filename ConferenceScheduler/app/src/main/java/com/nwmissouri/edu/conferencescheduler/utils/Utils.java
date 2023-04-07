package com.nwmissouri.edu.conferencescheduler.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String encrypt(String input) {
        try {
            // Generate password hash using SHA256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passwordData = input.getBytes(StandardCharsets.UTF_8);
            md.update(passwordData);
            byte[] digest = md.digest();
            String passwordHash = bytesToHex(digest);

            System.out.println(passwordHash);
            return passwordHash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String dateFormat(Date date) {
        String dateString;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy, hh:mm a");
            dateString = dateFormat.format(date);
        } catch (Exception e) {
            dateString = "";
        }
        return dateString;
    }


}
