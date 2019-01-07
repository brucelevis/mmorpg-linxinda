package com.wan37.util;

import io.netty.channel.Channel;

/**
 * 消息推送工具类
 *
 * @author linda
 */
public class GeneralNotifySenderUtil {

    private static final String SUFFIX = "\n";

    public static void send(Channel channel, String content) {
        channel.writeAndFlush(content + SUFFIX);
    }
}
