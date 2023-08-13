package com.yudh.execute.utils;


import com.yudh.execute.bean.JdbcConfig;
import com.yudh.execute.constant.DataBaseConst;
import com.yudh.execute.frame.MainFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Classname CommandUtils
 * @Description TODO
 * @Date 2022/10/17 22:26
 * @Created by 叽里咕噜
 */
@Slf4j
public class CommandUtils {
    /**
     * 数据库编码格式
     */
    private static String charset;

    public static String executeCommand(JdbcConfig jdbcConfig, String scriptPath, String scriptFileName, String dbType) throws Exception {

        String username = jdbcConfig.getUser();
        String passWord = jdbcConfig.getPassword();

        log.info("jdbcConfig:{}",jdbcConfig);
        String logPath = ".\\executeLogs\\"+scriptFileName+".log";
        File logFile = new File(logPath);
        logFile.getParentFile().mkdirs();
        log.info("logPath:{}", logFile.getAbsolutePath());

        String command = "";
        // cmd切换到不同磁盘时需要先切盘，比如当前在C盘，想要cd到D盘某个目录，需要先 D: 再进行cd操作
        String preDirectory = scriptPath.substring(0,2);
        String charset = getDataBaseCharset();

        if (DataBaseConst.ORACLE.equals(dbType)) {
            //执行sh脚本

            command = "cmd /c " + preDirectory + " && SET NLS_LANG=" + charset + " && cd "  + scriptPath + " && echo exit | sqlplus " + username + "/" + passWord + "@" +
                    jdbcConfig.getIp() + ":" + jdbcConfig.getPort() + "/" + jdbcConfig.getDatabase() + " @" + scriptFileName + " >" + logFile.getAbsolutePath() + " 2>&1 ";
            String logCommand = "cmd /c " + preDirectory + " && SET NLS_LANG=" + charset + " && cd "  + scriptPath + " && echo exit | sqlplus " + username + "/******@" +
                    jdbcConfig.getIp() + ":" + jdbcConfig.getPort() + "/" + jdbcConfig.getDatabase() + " @" + scriptFileName + " >" + logFile.getAbsolutePath() + " 2>&1 ";
            log.info("脚本执行命令为:{}", logCommand);
        }
        else if (DataBaseConst.MYSQL.equals(dbType)){
            command = "cmd /c " + preDirectory + " && SET NLS_LANG=" + charset + " && cd " + scriptPath + " && echo exit | mysql -h" + jdbcConfig.getIp() + " -P" + jdbcConfig.getPort() + " -u" +
                    username + " -p" + passWord + " -D " + jdbcConfig.getDatabase() + " -e \"source " + scriptFileName + "\" >" + logFile.getAbsolutePath() + " 2>&1";
            String logCommand = "cmd /c " + preDirectory + " && SET NLS_LANG=" + charset + " && cd " + scriptPath + " && echo exit | mysql -h" + jdbcConfig.getIp() + " -P" + jdbcConfig.getPort() + " -u" +
                    username + " -p****** -D " + jdbcConfig.getDatabase() + " -e \"source " + scriptFileName + "\" >" + logFile.getAbsolutePath() + " 2>&1";
            log.info("脚本执行命令为:{}",logCommand);
        }
        StringBuilder result = new StringBuilder("");
        BufferedReader br = null;
        Process process = null;


        try {
            // 执行sh脚本
//            process = Runtime.getRuntime().exec(new String[]{"/bin/sh",command});
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ((line = br.readLine()) != null){
                result.append(line).append("\n");
            }
            if (!logFile.exists() || !logFile.isFile()) {
                throw new Exception("命令执行结果文件不存在!");
            }
            StringBuilder message = new StringBuilder();
            String utf = "UTF";
            if (charset.toUpperCase().contains("GBK")) {
              message = FileUtils.readFile(logFile, "GBK");
            }
            else if (charset.toUpperCase().contains(utf)) {
              message = FileUtils.readFile(logFile, "UTF-8");
            }
            message.append(DateUtils.getDateTime()).append(" 命令执行结束");
            log.info("执行脚本结束！");
//            MainFrame.appendTextArea("执行脚本结束！执行结果请在 " + logFile.getAbsolutePath() + "查看！");
            MainFrame.appendTextArea("执行脚本结束！执行结果:\n " + message);

            return message.toString();
        }
        catch (Exception e){
            log.error("执行脚本失败",e);
        }
        finally {
            CloseStream.closed(br);
            process.destroy();
        }
        return null;
    }

    public static String getDataBaseCharset() {
        String dbType = MainFrame.dbTypeText.getSelectedItem().toString();
        if (StringUtils.isNotBlank(charset)){
            return charset;
        }
         Connection connection = MainFrame.connection;
         PreparedStatement preparedStatement = null;
         ResultSet resultSet = null;
         String oracleSql = "SELECT t.lang||'_'||t.territy||'.'||t.characterset NLS_LANG        FROM (SELECT        (SELECT value FROM nls_database_parameters WHERE parameter='NLS_LANGUAGE') lang,        (SELECT value FROM nls_database_parameters WHERE parameter='NLS_TERRITORY') territy ,        (SELECT value FROM nls_database_parameters WHERE parameter='NLS_CHARACTERSET') characterset        FROM DUAL) t";

         String mysqlSql = "show variables like 'character_set_database'";
         try {
               if (DataBaseConst.ORACLE.equalsIgnoreCase(dbType)) {
                 preparedStatement = connection.prepareStatement(oracleSql);
                 resultSet = preparedStatement.executeQuery();
                 while (resultSet.next()) {
                       charset = resultSet.getString("NLS_LANG");
                     }
               }
               else if (DataBaseConst.MYSQL.equalsIgnoreCase(dbType)) {
                 preparedStatement = connection.prepareStatement(mysqlSql);
                 resultSet = preparedStatement.executeQuery();
                 while (resultSet.next()) {
                       charset = resultSet.getString("Value");
                     }
               }
               log.info("获取 {} 数据库编码成功 ：{}", dbType, charset);
                MainFrame.appendTextArea("获取 " + dbType +" 数据库编码成功 ： " + charset);
                CloseStream.release(resultSet, preparedStatement, null);
             } catch (SQLException e) {
               log.error("获取数据库连接失败", e);
             }
         return charset;
    }


}
