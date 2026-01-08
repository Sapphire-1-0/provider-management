package com.brihaspathee.sapphire.utils;

import java.security.SecureRandom;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 02, January 2026
 * Time: 05:40
 * Project: sapphire
 * Package Name: com.brihaspathee.sapphire.utils
 * To change this template use File | Settings | File and Code Template
 */
public final class RandomStringUtil {
    private static final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int DEFAULT_LENGTH = 15;
    private static final SecureRandom RANDOM = new SecureRandom();

    private RandomStringUtil() {
        // utility class
    }

    public static String generateRandomAlphaNumeric() {
        StringBuilder sb = new StringBuilder(DEFAULT_LENGTH);

        for (int i = 0; i < DEFAULT_LENGTH; i++) {
            int index = RANDOM.nextInt(ALPHA_NUMERIC.length());
            sb.append(ALPHA_NUMERIC.charAt(index));
        }

        return sb.toString();
    }
}
