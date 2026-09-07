package com.bank.app.common.util;

import java.util.Arrays;

public final class LogMaskingUtil {

    private LogMaskingUtil() {
    }

    public static String maskAccountNumber(String number) {
        if (number == null || number.length() <= 4) {
            return "****";
        }
        return "*".repeat(number.length() - 4) + number.substring(number.length() - 4);
    }

    public static String maskName(String name) {
        if (name == null || name.isBlank()) {
            return "****";
        }
        return Arrays.stream(name.trim().split("\\s+"))
                .map(part -> part.charAt(0) + "*".repeat(Math.max(part.length() - 1, 1)))
                .reduce((a, b) -> a + " " + b)
                .orElse("****");
    }
}
