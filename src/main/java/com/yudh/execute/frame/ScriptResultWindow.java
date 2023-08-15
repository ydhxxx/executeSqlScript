package com.yudh.execute.frame;

import javax.swing.*;
import java.awt.*;

/**
 * @author yudh
 * @date 2022-03-28 15:32
 */
public class ScriptResultWindow extends JFrame {

    public ScriptResultWindow(String text) {
        // 设置大小并获取居中位置
        setSize(750, 520);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int)(toolkit.getScreenSize().getWidth() - getWidth())/2;
        int y = (int)(toolkit.getScreenSize().getHeight() - getHeight())/2;
        setLocation(x,y);
        // 设置标题
        setTitle("帮助");
        // 设置图片
        ImageIcon imageIcon = new ImageIcon("image/command.png");
        setIconImage(imageIcon.getImage());
        // 设置点击关闭退出程序
        setVisible(true);
        setAlwaysOnTop(true);
        init(text);
    }

    void init(String text){
//        JTextArea jTextArea = new JTextArea();
//        jTextArea.setLineWrap(true);
//        jTextArea.setText(text);
//        jTextArea.setFont(new Font("宋体",Font.BOLD,20));
//        jTextArea.setEditable(false);
        JPanel contentPane = new JPanel();
        contentPane.setBounds(0,0,800,400);
        setContentPane(contentPane);

        JTextArea logText = new JTextArea();
        logText.setText(text);
        logText.setFont(new Font("宋体",Font.PLAIN,20));
        // 对文本输入区设置可以滚动
        logText.setLineWrap(true);
        // 设置自动滚动
        JScrollPane jScrollPane = new JScrollPane(logText);
        jScrollPane.setPreferredSize(new Dimension(700,460));
        // 设置滚动条一直存在
        jScrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        contentPane.add(Box.createVerticalStrut(20));

        contentPane.add(jScrollPane);
    }

}
