package com.wan37.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.stereotype.Service;

@Service
public class NettyServer {

    public void start() {
        // EventLoop 代替原来的 ChannelFactory
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建 一个netty 服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // server端采用简洁的连写方式，client端才用分段普通写法。
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // 指定channel[通道]类型
                    .childHandler(new ChannelInitializer<SocketChannel>() {  // 指定Handler [操纵者]

                        @Override
                        public void initChannel(SocketChannel ch) {
                            // 以("\n")为结尾分割的 解码器
                            ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));

                            // 字符串 解码 和 编码   默认的 StringDecoder 字符串形式输出
                            ch.pipeline().addLast("decoder", new StringDecoder());
                            ch.pipeline().addLast("encoder", new StringEncoder());

                            // 添加自己的对上传数据的处理
                            ch.pipeline().addLast(new ServerHandler());
                        }

                    }).option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = serverBootstrap.bind(8000).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            // NOOP
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
