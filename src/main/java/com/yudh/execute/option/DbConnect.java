package com.yudh.execute.option;

import com.yudh.execute.bean.DataSourceBean;
import com.yudh.execute.bean.DruidDataSourceBean;
import com.yudh.execute.bean.JdbcConfig;
import com.yudh.execute.constant.DataBaseConst;
import com.yudh.execute.frame.MainFrame;
import com.yudh.execute.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Classname MysqlConnect
 * @Description TODO
 * @Date 2022/10/17 21:54
 * @Created by 叽里咕噜
 */
@Slf4j
public class DbConnect {

    private String dbType;
    private String ipPort;
    private String database;
    private String user;
    private String password;

    public Connection connect(){
        ipPort = MainFrame.ipPortText.getText().trim();
        database = MainFrame.databaseText.getText().trim();
        user = MainFrame.userText.getText().trim();
        password = MainFrame.passwordText.getText().trim();
        dbType = MainFrame.dbTypeText.getSelectedItem().toString();

        if ("".equals(ipPort) || "".equals(database) || "".equals(user) || "".equals(password)){
            MainFrame.appendTextArea("ERROR 表单不能为空,请检查");
            JOptionPane.showMessageDialog(null,"表单不能为空","连接",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        JdbcConfig jdbcConfig = new JdbcConfig(ipPort,database,user,password);

        DataSourceBean dataSourceBean = getDataBaseConfig(jdbcConfig, dbType);
        DruidDataSourceBean.setDataSource(dataSourceBean);
        Connection connection = null;
        try {
            connection = DruidDataSourceBean.dataSource.getConnection();
        } catch (SQLException e) {
            log.error("获取数据库连接失败,", e);
            MainFrame.appendTextArea("ERROR "+e.getMessage());
        }

        // 验证连接的正确性
        if (connection == null){
            MainFrame.appendTextArea("ERROR 数据库连接错误,请检查");
            JOptionPane.showMessageDialog(null,"数据库连接错误","连接",JOptionPane.ERROR_MESSAGE);
        }
        else {
            MainFrame.appendTextArea("连接成功！");
            JOptionPane.showMessageDialog(null,"连接成功！","连接",JOptionPane.INFORMATION_MESSAGE);
            writFile();
            // 将文件目录选择按钮以及执行脚本按钮置为可用状态
            MainFrame.fileSelect.setEnabled(true);
            MainFrame.execute.setEnabled(true);
        }
        return connection;
    }



    /**
     * 获取数据库连接配置，填充到DruidDataSource中
     * @param jdbcConfig
     * @param dbType
     * @return
     */
    public static DataSourceBean getDataBaseConfig(JdbcConfig jdbcConfig, String dbType){
        DataSourceBean dataSourceBean = new DataSourceBean();
        String classDriver = "";
        String url = "";
        if (DataBaseConst.MYSQL.equalsIgnoreCase(dbType)){
            classDriver = DataBaseConst.MYSQL_DRIVER;
            url = getMysqlUrl(jdbcConfig);
        }
        else if (DataBaseConst.ORACLE.equalsIgnoreCase(dbType)){
            classDriver = DataBaseConst.ORACLE_DRIVER;
            url = getOracleUrl(jdbcConfig);
        }
        dataSourceBean.setDriverClassName(classDriver);
        dataSourceBean.setUrl(url);
        dataSourceBean.setUserName(jdbcConfig.getUser());
        dataSourceBean.setPassWord(jdbcConfig.getPassword());
        return dataSourceBean;
    }

    /**
     * 获取MySQL连接串
     * @param jdbcConfig
     * @return
     */
    public static String getMysqlUrl(JdbcConfig jdbcConfig){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("jdbc:mysql://").append(jdbcConfig.getIpPort())
                .append("/").append(jdbcConfig.getDatabase())
                .append("?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8&");
        return stringBuilder.toString();
    }

    /**
     * 获取Oracle连接串
     * @param jdbcConfig
     * @return
     */
    public static String getOracleUrl(JdbcConfig jdbcConfig){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("jdbc:oracle:thin:@").append(jdbcConfig.getIpPort())
                .append("/").append(jdbcConfig.getDatabase());
        return stringBuilder.toString();
    }

    /**
     * 将正确的数据库连接信息写入文件
     */
    public void writFile(){
        File file = new File("file/connect.txt");
        FileUtils.createNewFile(file);
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(dbType).append("\n").append(ipPort).append("\n").append(database).append("\n").append(user).
                append("\n").append(password).append("\n");
        FileUtils.writeTxt(file,stringBuilder.toString(),false);
        MainFrame.appendTextArea("连接信息成功写入本地文件");
    }
}
