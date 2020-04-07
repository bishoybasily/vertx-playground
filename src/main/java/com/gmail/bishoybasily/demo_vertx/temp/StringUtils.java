package com.gmail.bishoybasily.demo_vertx.temp;

import java.util.StringJoiner;

public class StringUtils {

    public static String prefixedJoiner(String prefix, StringJoiner joiner) {
        if (joiner.length() > 0)
            return prefix + joiner;
        return "";
    }

    public static String prefixedJoin(String prefix, String delimiter, String... strings) {
        StringJoiner joiner = new StringJoiner(delimiter);
        for (String s : strings)
            joiner.add(s);
        return prefixedJoiner(prefix, joiner);
    }

}
