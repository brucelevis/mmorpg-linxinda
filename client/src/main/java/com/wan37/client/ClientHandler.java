package com.wan37.client;

import com.alibaba.fastjson.JSONObject;
import com.wan37.req.obj.TestJsonObj;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDate;

/**
 * 客户端的逻辑，自己对数据处理
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

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
        System.out.println("Client active ");
        ctx.writeAndFlush("我是 client " + LocalDate.now().toString() + "\n");

        String s = JSONObject.toJSONString(new TestJsonObj(111, "test"))+ "\n";
        ctx.writeAndFlush(s);
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
