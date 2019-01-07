package com.wan37.client;

import com.wan37.view.MainView;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 客户端
 *
 * @author linda
 */
public class ClientStarter {

    public static void main(String[] args) {
        new MainView();
        new ClassPathXmlApplicationContext("applicationContext.xml");
        Client.start();
    }
}
