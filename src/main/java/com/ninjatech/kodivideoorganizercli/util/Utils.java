package com.ninjatech.kodivideoorganizercli.util;

public final class Utils {

    private Utils() {}

    public static String adaptName(String name) {
        return name.replaceAll("\\\\|/|:|\\*|\\?|\"|<|>|\\|", "");
    }

}
