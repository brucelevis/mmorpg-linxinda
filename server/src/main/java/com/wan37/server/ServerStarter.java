package com.wan37.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Netty4 服务端代码
 */
public class ServerStarter {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("applicationContext.xml");
        new NettyServer().start();
    }
}