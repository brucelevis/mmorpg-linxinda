package com.wan37.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

/**
 * 客户端
 */
public class ClientStarter {

    public static void main(String args[]) {
        new ClassPathXmlApplicationContext("applicationContext.xml");
        Client.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String cmdStr = scanner.nextLine();
            Client.channel.writeAndFlush(cmdStr + "\n");
        }
    }
}
