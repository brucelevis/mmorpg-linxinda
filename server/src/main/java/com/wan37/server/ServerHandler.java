package com.wan37.server;

import com.wan37.handler.GeneralDipatchHandlerManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 数据处理
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final String SUFFIX = "\n";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String command = msg.toString();

        // 请求接口名 参数，参数...
        String[] words = command.split(" ");

        GeneralDipatchHandlerManager.get(words[0])
                .ifPresent(h -> h.handle(new GeneralReqMsg(words, ctx.channel())));
    }

    /*
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
        super.channelActive(ctx);
    }
}