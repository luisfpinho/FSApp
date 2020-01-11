package com.gmail.luisfpinho99.bysidefs;

public final class Preconditions {

    /**
     * Private constructor to hide the implicit public one.
     */
    private Preconditions() {
        // Should be empty.
    }

    public static void ensure(String message, boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void ensure(boolean condition) {
        if (!condition) {
            throw new IllegalArgumentException();
        }
    }

    public static void ensureNotNull(Object... objects) {
        ensureNotNull("Cannot be null.", objects);
    }

    public static void ensureNotNull(String message, Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    public static void ensureNotEmpty(String message, String... strings) {
        for (String string : strings) {
            ensureNotNull(message, string);

            if (string.isEmpty()) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    public static void ensureBetween(String message, double minIncl, double maxIncl, double... values) {
        for (Double value : values) {
            if (value < minIncl || value > maxIncl) {
                throw new IllegalArgumentException(message);
            }
        }
    }
}
