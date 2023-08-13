package com.yudh.execute.frame;

import com.yudh.execute.bean.JdbcConfig;
import com.yudh.execute.option.DbConnect;
import com.yudh.execute.option.ScriptDeal;
import com.yudh.execute.utils.CloseStream;
import com.yudh.execute.utils.CommandUtils;
import com.yudh.execute.utils.DateUtils;
import com.yudh.execute.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * @author yudh
 * @date 2022-03-18 23:21
 */
@Slf4j
public class MainFrame extends JFrame implements ActionListener {

    /**
     * 数据库类型
     */
    public static JComboBox<String> dbTypeText;
    /**
     * 数据库连接串
     */
    public static JTextField ipPortText;
    /**
     * 数据库名称
     */
    public static JTextField databaseText;
    /**
     * 用户名
     */
    public static JTextField userText;
    /**
     * 密码
     */
    public static JTextField passwordText;
    /**
     * 文件路径
     */
    public static JTextField fileText;
    /**
     * 显示提示信息
     */
    public static JTextArea logText;
    /**
     * 文件目录选择按钮
     */
    public static JButton fileSelect;
    /**
     * 执行脚本按钮
     */
    public static JButton execute;

    /**
     * 数据库连接对象
     */
    public static Connection connection;

    /**
     * 选择的file对象
     */
    public static File file;

    /**
     * 菜单对象
     */
    public static JMenu help;

    public MainFrame() {
        init();
        setJPanel();
        getConfig();

    }

    private void init(){
        // 设置大小并获取居中位置
        setSize(800, 800);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int)(toolkit.getScreenSize().getWidth() - getWidth())/2;
        int y = (int)(toolkit.getScreenSize().getHeight() - getHeight())/2;
        setLocation(x,y);
        // 设置标题
        setTitle("Sql脚本自动化工具");
        // 设置图片
        ImageIcon imageIcon = new ImageIcon("image/command.png");
        setIconImage(imageIcon.getImage());
        // 设置里面控件的布局方式
        setLayout(new FlowLayout());
        // 设置点击关闭退出程序
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setJPanel(){

        JPanel contentPane = new JPanel();
        contentPane.setBounds(0,0,800,400);
        setContentPane(contentPane);

        // 所有的标签以及设置字体
        JLabel dbType = new JLabel("数据库类型:");
        JLabel ipPort = new JLabel("数据库连接串:");
        JLabel database = new JLabel("数据库名称:");
        JLabel user = new JLabel("用户名:");
        JLabel password = new JLabel("密码:");
        dbType.setFont(new Font("宋体",Font.BOLD,20));
        ipPort.setFont(new Font("宋体",Font.BOLD,20));
        database.setFont(new Font("宋体",Font.BOLD,20));
        user.setFont(new Font("宋体",Font.BOLD,20));
        password.setFont(new Font("宋体",Font.BOLD,20));

        // 所有的输入框以及设置字体
        dbTypeText = new JComboBox<>();
        dbTypeText.addItem("mysql");
        dbTypeText.addItem("oracle");
        ipPortText = new JTextField();
        databaseText = new JTextField();
        userText = new JTextField();
        passwordText = new JPasswordField();
        dbTypeText.setFont(new Font("宋体",Font.BOLD,20));
        ipPortText.setFont(new Font("宋体",Font.BOLD,20));
        databaseText.setFont(new Font("宋体",Font.BOLD,20));
        userText.setFont(new Font("宋体",Font.BOLD,20));
        passwordText.setFont(new Font("宋体",Font.BOLD,20));

        // 所有的按钮以及设置字体
        JButton connect = new JButton("连接");
        JButton loadConfig = new JButton("加载配置");
        connect.setFont(new Font("宋体",Font.BOLD,20));
        loadConfig.setFont(new Font("宋体",Font.BOLD,20));

        // 数据库连接信息表单所在的VerticalBox
        Box vertical1 = Box.createVerticalBox();
        Box horizontalBox0 = Box.createHorizontalBox();
        Box horizontalBox1 = Box.createHorizontalBox();
        Box horizontalBox2 = Box.createHorizontalBox();
        Box horizontalBox3 = Box.createHorizontalBox();
        Box horizontalBox4 = Box.createHorizontalBox();
        Box horizontalBox5 = Box.createHorizontalBox();
        // 真正限制表单的高度宽度设置
        horizontalBox0.setPreferredSize(new Dimension(400,30));
        horizontalBox1.setPreferredSize(new Dimension(400,30));
        horizontalBox2.setPreferredSize(new Dimension(400,30));
        horizontalBox3.setPreferredSize(new Dimension(400,30));
        horizontalBox4.setPreferredSize(new Dimension(400,30));
        horizontalBox5.setPreferredSize(new Dimension(400,30));
        horizontalBox1.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox2.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox3.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox4.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalBox5.setAlignmentX(Component.CENTER_ALIGNMENT);

        horizontalBox0.add(Box.createHorizontalStrut(20));
        horizontalBox0.add(dbType);
        horizontalBox0.add(Box.createHorizontalStrut(5));
        horizontalBox0.add(dbTypeText);

        horizontalBox1.add(ipPort);
        horizontalBox1.add(Box.createHorizontalStrut(5));
        horizontalBox1.add(ipPortText);

        horizontalBox2.add(Box.createHorizontalStrut(22));
        horizontalBox2.add(database);
        horizontalBox2.add(Box.createHorizontalStrut(5));
        horizontalBox2.add(databaseText);

        horizontalBox3.add(Box.createHorizontalStrut(63));
        horizontalBox3.add(user);
        horizontalBox3.add(Box.createHorizontalStrut(5));
        horizontalBox3.add(userText);

        horizontalBox4.add(Box.createHorizontalStrut(83));
        horizontalBox4.add(password);
        horizontalBox4.add(Box.createHorizontalStrut(5));
        horizontalBox4.add(passwordText);

        horizontalBox5.add(connect);
        horizontalBox5.add(Box.createHorizontalGlue());
        horizontalBox5.add(loadConfig);

        // vertical1将五个horizontalBox添加进去
        vertical1.add(Box.createVerticalStrut(20));
        vertical1.add(horizontalBox0);
        vertical1.add(Box.createVerticalStrut(5));
        vertical1.add(horizontalBox1);
        vertical1.add(Box.createVerticalStrut(5));
        vertical1.add(horizontalBox2);
        vertical1.add(Box.createVerticalStrut(5));
        vertical1.add(horizontalBox3);
        vertical1.add(Box.createVerticalStrut(5));
        vertical1.add(horizontalBox4);
        vertical1.add(Box.createVerticalStrut(20));
        vertical1.add(horizontalBox5);
        // vertical1设置最大长度和宽度
        vertical1.setMaximumSize(new Dimension(400,300));

        // 文件选择框所在VerticalBox，因为需要设置的宽度与表单VerticalBox不一致，所以重新定义一个
        Box vertical2 = Box.createVerticalBox();
        Box horizontal1 = Box.createHorizontalBox();
        fileText = new JTextField();
        fileText.setFont(new Font("宋体",Font.BOLD,20));
        fileSelect = new JButton("文件目录选择");
        fileSelect.setFont(new Font("宋体",Font.BOLD,20));
        execute = new JButton("执行脚本");
        execute.setFont(new Font("宋体",Font.BOLD,20));
        logText = new JTextArea();
        logText.setFont(new Font("宋体",Font.PLAIN,20));
        // 对文本输入区设置可以滚动
        logText.setLineWrap(true);
        // 设置自动滚动
        JScrollPane jScrollPane = new JScrollPane(logText);
        jScrollPane.setPreferredSize(new Dimension(700,370));
        // 设置滚动条一直存在
        jScrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // 初始将文件选择按钮以及执行脚本按钮置为不可用状态
        fileSelect.setEnabled(false);
        execute.setEnabled(false);

        horizontal1.add(fileText);
        horizontal1.add(Box.createHorizontalStrut(30));
        horizontal1.add(fileSelect);
        horizontal1.add(Box.createHorizontalStrut(30));
        horizontal1.add(execute);
        horizontal1.setPreferredSize(new Dimension(700,30));
        vertical2.add(horizontal1);
        vertical2.add(Box.createVerticalStrut(30));
        vertical2.add(jScrollPane);
        vertical2.setMaximumSize(new Dimension(700,500));

        // 总的VerticalBox,包含所有Box
        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(vertical1);
        verticalBox.add(Box.createVerticalStrut(30));
        verticalBox.add(vertical2);
        contentPane.add(verticalBox);

        // 菜单提示
        JMenuBar jMenuBar = new JMenuBar();
        help = new JMenu("帮助");
        help.setFont(new Font("宋体",Font.BOLD,20));
        jMenuBar.add(help);
        this.setJMenuBar(jMenuBar);

        fileSelect.addActionListener(this);
        connect.addActionListener(this);
        loadConfig.addActionListener(this);
        execute.addActionListener(this);
        help.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                HelpWindow helpWindow = new HelpWindow();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("连接".equals(e.getActionCommand())) {
            DbConnect dbConnect = new DbConnect();
            connection = dbConnect.connect();
        }
        else if ("加载配置".equals(e.getActionCommand())){
            // 主动从配置文件加载数据，需要提示信息
            getConfig();
        }
        else if ("文件目录选择".equals(e.getActionCommand())) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int returnVal = fileChooser.showOpenDialog(this);
            file = fileChooser.getSelectedFile();
            String path = file.getAbsolutePath();
            fileText.setText(path);
            if (file.isFile()){
                appendTextArea("选择文件："+path);
            }
            else if (file.isDirectory()){
                appendTextArea("选择目录："+path);
            }
        }
        else if ("执行脚本".equals(e.getActionCommand())){
            if (file == null){
                appendTextArea("ERROR 文件不存在!");
                JOptionPane.showMessageDialog(null,"文件不存在","执行",JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (connection == null){
                appendTextArea("ERROR 数据库连接错误!");
                JOptionPane.showMessageDialog(null,"数据库连接错误","执行",JOptionPane.ERROR_MESSAGE);
                return;
            }
            String ipPort = MainFrame.ipPortText.getText().trim();
            String database = MainFrame.databaseText.getText().trim();
            String user = MainFrame.userText.getText().trim();
            String password = MainFrame.passwordText.getText().trim();
            String dbType = MainFrame.dbTypeText.getSelectedItem().toString();
            JdbcConfig jdbcConfig = new JdbcConfig(ipPort,database,user,password);
            List<String> fileConfig = ScriptDeal.generateScript(file);
            log.info("fileConfig,{}",fileConfig);
            try {
                CommandUtils.executeCommand(jdbcConfig,fileConfig.get(0),fileConfig.get(1),dbType);
            } catch (Exception ex) {
                appendTextArea("ERROR 数据库脚本执行错误!");
                appendTextArea("ERROR "+ex.getMessage());
            }
            finally {
                CloseStream.closeConnection(connection);
                if (file.isDirectory()){
                    // 删除前面生成的脚本文件
                    File files = new File(fileConfig.get(0)+"/"+fileConfig.get(1));
                    log.info("脚本文件：{}",files.getAbsolutePath());
                    FileUtils.deleteAllFiles(files);
                }

            }

        }

    }

    public void getConfig(){
        // 默认从配置文件加载数据，不需要提示信息
        File file = new File("file/connect.txt");
        String config = null;
        try {
            config = FileUtils.readFile(file, "GBK").toString();
        } catch (IOException e) {
            log.error("读取文件失败", e);
        }
        if (!StringUtils.isEmpty(config)){
            String[] configArr = config.split("\\n");
            dbTypeText.setSelectedItem(configArr[0]);
            ipPortText.setText(configArr[1]);
            databaseText.setText(configArr[2]);
            userText.setText(configArr[3]);
            passwordText.setText(configArr[4]);
        }
        else {
            dbTypeText.setSelectedItem("mysql");
            ipPortText.setText("127.0.0.1:3306");
            databaseText.setText("dbtrade");
            userText.setText("dbtrade");
            passwordText.setText("dbtrade");
        }

    }

    public static void appendTextArea(String info){
        logText.append(DateUtils.getDateTime()+info+"\n");
        logText.setCaretPosition(logText.getText().length());
        // 获取焦点
        logText.paintImmediately(logText.getBounds());

    }
}
