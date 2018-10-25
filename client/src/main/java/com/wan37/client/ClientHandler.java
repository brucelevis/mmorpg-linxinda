package com.wan37.client;

import com.alibaba.fastjson.JSONObject;
import com.wan37.req.obj.ReqRegisterPlayer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端的逻辑，自己对数据处理
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static final String SUFFIX = "\n";

    /*
     * 监听 服务器 发送来的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Server say : " + msg.toString());
    }

    /*
     * 启动客户端 时触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 发送注册请求
        ReqRegisterPlayer req = new ReqRegisterPlayer("player_Register", 111, "123");
        ctx.writeAndFlush(JSONObject.toJSONString(req) + SUFFIX);

        super.channelActive(ctx);
    }

    /*
     * 关闭 客户端 触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client close ");
        super.channelInactive(ctx);
    }
}
