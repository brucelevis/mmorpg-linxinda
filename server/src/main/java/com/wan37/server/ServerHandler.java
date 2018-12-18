package com.wan37.server;

import com.wan37.event.GenernalEventListenersManager;
import com.wan37.event.OfflineEvent;
import com.wan37.exception.GeneralErrorExecption;
import com.wan37.handler.GeneralDipatchHandlerManager;
import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

/**
 * 数据处理
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = Logger.getLogger(ServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();
        String command = msg.toString();

        // 请求接口名 参数，参数...
        String[] words = command.split(" ");

        GeneralHandler handler = GeneralDipatchHandlerManager.get(words[0]).orElse(null);
        if (handler == null) {
            channel.writeAndFlush("命令输入错误，请重新输入!\n");
            return;
        }

        handler.handle(new GeneralReqMsg(words, channel));
    }

    /*
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        LOG.info("--- Server is inactive ---");

        PlayerGlobalManager playerGlobalManager = ServerStarter.springContext.getBean(PlayerGlobalManager.class);
        Player player = playerGlobalManager.getPlayerByChannel(ctx.channel());
        if (player == null) {
            return;
        }

        new GenernalEventListenersManager().fireEvent(new OfflineEvent(player));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof GeneralErrorExecption) {
            // 自定义抛出的异常
            ctx.writeAndFlush(cause.getMessage() + "\n");
            return;
        }

        cause.printStackTrace();
    }
}