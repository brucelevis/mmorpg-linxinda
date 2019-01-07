package com.wan37.client;

import com.wan37.view.MainView;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端的逻辑，自己对数据处理
 *
 * @author linda
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        MainView.OUTPUT.append(msg.toString() + "\n");

        // 使用JTextArea的setCaretPosition();手动设置光标的位置为最后一行。人气颇高。使用方法也很简单
        MainView.OUTPUT.setCaretPosition(MainView.OUTPUT.getDocument().getLength());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client close ");
        super.channelInactive(ctx);
    }
}
