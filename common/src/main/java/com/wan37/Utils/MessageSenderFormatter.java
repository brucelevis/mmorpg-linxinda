package com.wan37.Utils;

public class MessageSenderFormatter {

    private static final String SUFFIX = "\n";

    public static String format(Object obj) {
        return JsonUtil.parseJson(obj) + SUFFIX;
    }
}
