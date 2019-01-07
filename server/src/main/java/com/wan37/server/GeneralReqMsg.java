package com.wan37.server;

import com.wan37.exception.GeneralErrorException;
import io.netty.channel.Channel;

/**
 * 请求信息包装类
 *
 * @author linda
 */
public class GeneralReqMsg implements ParameterTransformable {

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
        checkParam(index);
        return Integer.parseInt(params[index]);
    }

    @Override
    public String getParamAsString(int index) {
        checkParam(index);
        return params[index];
    }

    @Override
    public Long getParamAsLong(int index) {
        checkParam(index);
        return Long.parseLong(params[index]);
    }

    private void checkParam(int index) {
        if (index >= params.length) {
            throw new GeneralErrorException("参数下标越界");
        }
    }
}
