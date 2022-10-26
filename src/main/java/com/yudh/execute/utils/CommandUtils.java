package com.yudh.execute.utils;


import com.yudh.execute.bean.JdbcConfig;
import com.yudh.execute.constant.DataBaseConst;
import com.yudh.execute.frame.MainFrame;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @Classname CommandUtils
 * @Description TODO
 * @Date 2022/10/17 22:26
 * @Created by 叽里咕噜
 */
@Slf4j
public class CommandUtils {
    public static String executeCommand(JdbcConfig jdbcConfig, String scriptPath, String scriptFileName, String dbType) throws Exception {

        String username = jdbcConfig.getUser();
        String passWord = jdbcConfig.getPassword();

        log.info("jdbcConfig:{}",jdbcConfig);
        String logPath = ".\\executeLogs\\"+scriptFileName+".log";
        File logFile = new File(logPath);
        FileUtils.createNewFile(logFile);
        String command = "";
        // cmd切换到不同磁盘时需要先切盘，比如当前在C盘，想要cd到D盘某个目录，需要先 D: 再进行cd操作
        String preDirectory = scriptPath.substring(0,2);
        if (DataBaseConst.ORACLE.equals(dbType)) {
            //执行sh脚本

            command = "cmd /c " + preDirectory + " && cd "  + scriptPath + " && echo exit | sqlplus " + username + "/" + passWord + "@" +
                    jdbcConfig.getIp() + ":" + jdbcConfig.getPort() + "/" + jdbcConfig.getDatabase() + " @" + scriptFileName + " >" + logFile.getAbsolutePath() + " 2>&1 \\r\\n";
            String logCommand = "cmd /c " + preDirectory + " && cd "  + scriptPath + " && echo exit | sqlplus " + username + "/******@" +
                    jdbcConfig.getIp() + ":" + jdbcConfig.getPort() + "/" + jdbcConfig.getDatabase() + " @" + scriptFileName + " >" + logFile.getAbsolutePath() + " 2>&1 \\r\\n";
            log.info("脚本执行命令为:{}", logCommand);
        }
        else if (DataBaseConst.MYSQL.equals(dbType)){
            command = "cmd /c " + preDirectory + " && cd " + scriptPath + " && echo exit | mysql -h" + jdbcConfig.getIp() + " -P" + jdbcConfig.getPort() + " -u" +
                    username + " -p" + passWord + " -D " + jdbcConfig.getDatabase() + " -e \"source " + scriptFileName + "\" >" + logFile.getAbsolutePath() + " 2>&1";
            String logCommand = "cmd /c " + preDirectory + " && cd " + scriptPath + " && echo exit | mysql -h" + jdbcConfig.getIp() + " -P" + jdbcConfig.getPort() + " -u" +
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

            StringBuilder message = new StringBuilder(FileUtils.readFile(logFile));
            message.append(DateUtils.getDateTime()).append(" 命令执行结束");
            log.info("执行脚本结束！");
            MainFrame.appendTextArea("执行脚本结束！执行结果请在 " + logFile.getAbsolutePath() + "查看！");
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

}
