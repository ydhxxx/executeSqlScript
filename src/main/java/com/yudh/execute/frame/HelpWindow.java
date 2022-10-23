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
        jTextArea.setText("MySql脚本自动化工具=============================================\n" +
                "\n" +
                "1.修改数据库连接相关信息;\n" +
                "2.点击【连接】按钮，进行数据库连接校验;\n" +
                "3.若数据库连接正确，下次打开本工具会自动加载该配置信息;\n" +
                "4.点击【文件目录选择】按钮，可以选择单个sql脚本文件；可以选择某个目录；也可以选择'天鉴部署包'（必须严格按照部署包格式）的zip文件;\n" +
                "5.点击【执行脚本】按钮，若上一步选择的是单个sql文件，则执行该文件；若为某个目录，工具会执行该目录下所有sql脚本文件（执行顺序为默认文件名排序）;" +
                "若选择是'天鉴部署包'，则会临时解压zip包并自动寻找与数据库名匹配的脚本目录并执行,执行结束删除临时目录;\n"+
                "6.下方会显示相应操作以及提示信息。\n" +
                "\n"+
                "！！！！！！若脚本执行报错，请打开源文件检查是否为简单的语法错误，手动修改保存再次点击执行即可！！！！！！！！！！！\n" +
                "\n" +
                "若有问题请联系【yudh31611】\n" +
                "MySql脚本自动化工具=============================================");
        jTextArea.setFont(new Font("宋体",Font.BOLD,20));
        jTextArea.setEditable(false);

        this.add(jTextArea);
    }

}
