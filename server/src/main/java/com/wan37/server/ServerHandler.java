package com.wan37.server;

import com.alibaba.fastjson.JSON;
import com.wan37.handler.GeneralDipatchHandlerManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetAddress;
import java.util.Map;

/**
 * 数据处理
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    //private static final Logger LOG = Logger.getLogger(ServerHandler.class);
    private static final String SUFFIX = "\n";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String msgStr = msg.toString();
        String req = (String) getParamMap(msgStr).get("req");

        GeneralDipatchHandlerManager.get(req)
                .ifPresent(h -> h.handle(new GeneralReqMsg(msgStr)));
    }

    /*
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
        ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!" + SUFFIX);
        super.channelActive(ctx);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getParamMap(String msg) {
        return (Map<String, Object>) JSON.parse(msg);
    }
}