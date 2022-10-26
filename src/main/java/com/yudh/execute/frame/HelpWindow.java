package com.yudh.execute.frame;

import javax.swing.*;
import java.awt.*;

/**
 * @author yudh
 * @date 2022-03-28 15:32
 */
public class HelpWindow extends JFrame {

    public HelpWindow() {
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
        init();
    }

    void init(){
        JTextArea jTextArea = new JTextArea();
        jTextArea.setLineWrap(true);
        jTextArea.setText("Sql脚本自动化工具=============================================\n" +
                "\n" +
                "1.修改数据库连接相关信息;\n" +
                "2.点击【连接】按钮，进行数据库连接校验;\n" +
                "3.若数据库连接正确，下次打开本工具会自动加载该配置信息;\n" +
                "4.点击【文件目录选择】按钮，可以选择单个sql脚本文件；可以选择某个目录；\n" +
                "5.点击【执行脚本】按钮，若上一步选择的是单个sql文件，则执行该文件；若为某个目录，工具会执行该目录下所有sql脚本文件（执行顺序为默认文件名排序）;\n"+
                "6.下方会显示相应操作以及提示信息。\n" +
                "\n"+
                "\n" +
                "若有问题请联系【yudh31611】\n" +
                "Sql脚本自动化工具=============================================");
        jTextArea.setFont(new Font("宋体",Font.BOLD,20));
        jTextArea.setEditable(false);

        this.add(jTextArea);
    }

}
