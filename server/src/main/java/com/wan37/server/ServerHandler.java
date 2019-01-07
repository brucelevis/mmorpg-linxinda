package com.wan37.server;

import com.wan37.event.GeneralEventListenersManager;
import com.wan37.event.entity.OfflineEvent;
import com.wan37.exception.GeneralErrorException;
import com.wan37.handler.GeneralDispatchHandlerManager;
import com.wan37.handler.GeneralHandler;
import com.wan37.logic.player.Player;
import com.wan37.logic.player.PlayerGlobalManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

/**
 * 数据处理
 *
 * @author linda
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOG = Logger.getLogger(ServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Channel channel = ctx.channel();
        String command = msg.toString();

        // 请求接口名 参数，参数...
        String[] words = command.split(" ");

        GeneralHandler handler = GeneralDispatchHandlerManager.get(words[0]).orElse(null);
        if (handler == null) {
            channel.writeAndFlush("命令输入错误，请重新输入!\n");
            return;
        }

        handler.handle(new GeneralReqMsg(words, channel));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("RemoteAddress : " + ctx.channel().remoteAddress() + " active !");
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

        GeneralEventListenersManager manager = ServerStarter.springContext.getBean(GeneralEventListenersManager.class);
        manager.fireEvent(new OfflineEvent(player));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof GeneralErrorException) {
            // 自定义抛出的异常
            ctx.writeAndFlush(cause.getMessage() + "\n");
            return;
        }

        cause.printStackTrace();
    }
}