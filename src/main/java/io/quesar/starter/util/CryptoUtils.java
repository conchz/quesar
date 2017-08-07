package io.quesar.starter.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author dolphineor@gmail.com
 * @version 1.0
 * @date 2017-08-04
 */
public class CryptoUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private static final String RANDOM_STRINGS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom secureRandom = new SecureRandom();

    private CryptoUtils() {
    }

    public static String randomString(int len) {
        StringBuilder stringBuilder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            stringBuilder.append(RANDOM_STRINGS.charAt(secureRandom.nextInt(RANDOM_STRINGS.length())));
        }
        return stringBuilder.toString();
    }

    public static String randomStringWithDateTimePrefix(int len) {
        StringBuilder stringBuilder = new StringBuilder(len + 18)
            .append(LocalDateTime.now().format(DATE_TIME_FORMATTER))
            .append("-");
        for (int i = 0; i < len; i++) {
            stringBuilder.append(RANDOM_STRINGS.charAt(secureRandom.nextInt(RANDOM_STRINGS.length())));
        }
        return stringBuilder.toString();
    }
}
