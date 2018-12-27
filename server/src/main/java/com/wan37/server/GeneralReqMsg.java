package com.wan37.server;

import io.netty.channel.Channel;

public class GeneralReqMsg implements ParameterFormator {

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

    @Override
    public Integer getParamAsInt(int index) {
        if (index >= params.length) {
            return null;
        }

        return Integer.parseInt(params[index]);
    }

    @Override
    public String getParamAsString(int index) {
        if (index >= params.length) {
            return null;
        }

        return params[index];
    }

    @Override
    public Long getParamAsLong(int index) {
        if (index >= params.length) {
            return null;
        }

        return Long.parseLong(params[index]);
    }

    @Override
    public Double getParamAsDouble(int index) {
        if (index >= params.length) {
            return null;
        }

        return Double.parseDouble(params[index]);
    }
}
