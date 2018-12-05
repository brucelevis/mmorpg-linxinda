package com.wan37.client;

import com.wan37.view.MainView;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端的逻辑，自己对数据处理
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    /*
     * 监听 服务器 发送来的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MainView.output.append(msg.toString() + "\n");

        // 使用JTextArea的setCaretPosition();手动设置光标的位置为最后一行。人气颇高。使用方法也很简单
        MainView.output.setCaretPosition(MainView.output.getDocument().getLength());
    }

    /*
     * 启动客户端 时触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
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
