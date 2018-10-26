package com.wan37.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

public class Client {

    public static Channel channel = null;

    public static void start() {
        // Bootstrap，且构造函数变化很大，这里用无参构造。
        Bootstrap bootstrap = new Bootstrap();
        // 指定channel[通道]类型
        bootstrap.channel(NioSocketChannel.class);
        // 指定Handler [操纵者]
        bootstrap.handler(new ChannelInitializer<Channel>() {

            @Override
            protected void initChannel(Channel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                channel = ch;
                /*
                 * 这个地方的 必须和服务端对应上。否则无法正常解码和编码
                 */
                pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());

                // 客户端的逻辑，自己对数据处理
                pipeline.addLast(new ClientHandler());
            }
        });
        // 指定EventLoopGroup [事件 组]
        bootstrap.group(new NioEventLoopGroup());

        // 连接到本地的8000端口的服务端
        bootstrap.connect(new InetSocketAddress("127.0.0.1", 8000));
    }
}
