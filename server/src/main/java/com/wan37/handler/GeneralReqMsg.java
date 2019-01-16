package com.wan37.handler;

import com.wan37.logic.player.Player;
import com.wan37.server.ParameterTransformable;
import io.netty.channel.Channel;

/**
 * 请求信息包装类
 *
 * @author linda
 */
public class GeneralReqMsg implements ParameterTransformable {

    private final String[] params;
    private final Channel channel;
    private Player player;

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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
            channel.writeAndFlush("参数下标越界" + "\n");
        }
    }
}
