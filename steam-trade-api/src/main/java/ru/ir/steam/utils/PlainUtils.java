package ru.ir.steam.utils;

public class PlainUtils {

    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }

    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

    public static boolean isNotNullAndTrue(final Boolean bool) {
        return bool != null && bool;
    }

    public static boolean isNullOrFalse(final Boolean bool) {
        return bool == null || !bool;
    }

}
