package com.example.projeto1.util;

import java.util.Random;

public final class StringUtil {

    /**
     * Fonte: https://www.baeldung.com/java-random-string
     * */
    public static String randomAlphanumericString() {

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;

        return new Random().ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

    }

}
