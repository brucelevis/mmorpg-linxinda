package com.wan37.event;

public class OfflineEvent {

    public OfflineEvent(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelId() {
        return channelId;
    }

    private final String channelId;
}
