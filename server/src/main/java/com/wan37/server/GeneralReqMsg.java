package com.wan37.server;

import io.netty.channel.Channel;

public class GeneralReqMsg {

    private final String[] params;
    private final Channel channel;

    public GeneralReqMsg(String[] params, Channel channel) {
        this.params = params;
        this.channel = channel;
    }

    public String[] getParams() {
        return params;
    }

    public Channel getChannel() {
        return channel;
    }
}
