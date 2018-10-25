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

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String msgStr = msg.toString();
        Map<String, Object> map = (Map<String, Object>) JSON.parse(msg.toString());
        String req = (String) map.get("req");

        GeneralDipatchHandlerManager.get(req)
                .ifPresent(h -> h.handle(new GeneralReqMsg(msgStr)));


        // 返回客户端消息 - 我已经接收到了你的消息
        ctx.writeAndFlush("server Received your message !\n");
    }

    /*
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
        ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");
        super.channelActive(ctx);
    }
}