package com.wan37.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Netty4 服务端代码
 *
 * @author linda
 */
public class ServerStarter {

    public static ClassPathXmlApplicationContext springContext;

    public static void main(String[] args) {
        springContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        new NettyServer().start();
    }
}
