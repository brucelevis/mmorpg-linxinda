package com.wan37.view;

import com.wan37.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainView extends JFrame {

    public static final JTextArea output = new JTextArea();
    public static final JScrollPane jsp1 = new JScrollPane(output);
    public static final JTextArea intput = new JTextArea();

    public MainView() {
        this.setLayout(null);

        output.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
        output.setLineWrap(true);

        intput.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 22));
        intput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // NOOP
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == '\n') {
                    String text = intput.getText().replaceAll("\n", "");
                    output.append(text + "\n");
                    Client.channel.writeAndFlush(text + "\n");
                    intput.setText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == '\n') {
                    intput.setCaretPosition(0);
                }
            }
        });

        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        jsp1.setBounds(10, 0, 950, 750);
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jsp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //在文本框上添加滚动条
        JScrollPane jsp2 = new JScrollPane(intput);
        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        jsp2.setBounds(10, 760, 950, 200);
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jsp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //把滚动条添加到容器里面
        this.add(jsp1);
        this.add(jsp2);
        this.setSize(1000, 1030);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
