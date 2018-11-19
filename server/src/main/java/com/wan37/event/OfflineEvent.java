package com.wan37.event;

import io.netty.channel.Channel;

public class OfflineEvent {

    public OfflineEvent(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    private final Channel channel;
}
