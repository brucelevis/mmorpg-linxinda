package com.wan37.client;

import com.wan37.view.MainView;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 客户端
 */
public class ClientStarter {

    public static final MainView mainView = new MainView();

    public static void main(String args[]) {
        new ClassPathXmlApplicationContext("applicationContext.xml");
        Client.start();
    }
}
