package com.wan37.util;

import com.wan37.Utils.JsonUtil;
import io.netty.channel.Channel;

public class GeneralNotifySenderUtil {

    private static final String SUFFIX = "\n";

    public static void send(Channel channel, Object object) {
        channel.writeAndFlush(JsonUtil.parseJson(object) + SUFFIX);
    }

    public static void send(Channel channel, String content) {
        channel.writeAndFlush(content + SUFFIX);
    }
}
