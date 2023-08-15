package com.yudh.execute.option;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import com.yudh.execute.constant.DataBaseConst;
import com.yudh.execute.frame.MainFrame;
import com.yudh.execute.utils.CommandUtils;
import com.yudh.execute.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @Classname ScriptDeal
 * @Description TODO
 * @Date 2022/10/23 21:21
 * @Created by 叽里咕噜
 */
@Slf4j
public class ScriptDeal {

    /**
     * 判断窗口选择的是文件还是目录，是目录则递归解析该目录下所有的sql文件，并存储在一个新建的sql脚本中
     * 该文件中所有脚本是以 @ 或者 source 标签标记的文本
     * @param scriptFile
     * @return list 存储后续执行会用到的 文件所在目录、脚本文件名
     */
    public static List<String> generateScript(File scriptFile){
        List<String> list = new ArrayList<>(2);
        List<File> fileArrayList = new ArrayList<>();
        String parentDir;
        String dataBaseCharset = CommandUtils.getDataBaseCharset();
        boolean isGbk = dataBaseCharset.toUpperCase().contains("GBK");
        if (scriptFile.isDirectory()){
            parentDir = scriptFile.getAbsolutePath();
            FileUtils.getAllFiles(scriptFile,fileArrayList);

            list.add(parentDir);
            String fileName = scriptFile.getName() + "_" + System.currentTimeMillis() +".sql";
            File newScript = new File(parentDir+"\\"+fileName);
            writeScript(newScript,fileArrayList, isGbk ? "GBK" : "utf-8");
            list.add(fileName);
//            fileArrayList.add(newScript);
        }
        else {
            parentDir = scriptFile.getParentFile().getAbsolutePath();
            fileArrayList.add(scriptFile);
            list.add(parentDir);
            list.add(scriptFile.getName());
        }
        // 对脚本编码格式进行调整
        try {
            for (File sqlFile : fileArrayList) {
                Charset fileCharSet = CharsetUtil.defaultCharset(new FileInputStream(sqlFile), new Charset[0]);
                if (isGbk && StandardCharsets.UTF_8.equals(fileCharSet)) {
                    log.info("UTF_8转GBK的文件：{}", sqlFile);
                    CharsetUtil.convert(sqlFile, StandardCharsets.UTF_8, CharsetUtil.CHARSET_GBK); continue;
                }
                if (!isGbk && CharsetUtil.CHARSET_GBK.equals(fileCharSet)) {
                    log.info("GBK转UTF_8的文件：{}", sqlFile);
                    CharsetUtil.convert(sqlFile, CharsetUtil.CHARSET_GBK, StandardCharsets.UTF_8);
                }
            }
        } catch (FileNotFoundException e) {
            log.error("转换文本编码失败", e);
        }

        return list;
    }

    public static void writeScript(File newScript, List<File> fileList, String charset){
        String separator = " ";
        String output = "";
        String dbType = MainFrame.dbTypeText.getSelectedItem().toString();
        if (DataBaseConst.MYSQL.equalsIgnoreCase(dbType)){
            separator = "source \"";
            output = "select '";
        }
        else if (DataBaseConst.ORACLE.equalsIgnoreCase(dbType)){
            separator = "@ \"";
            output = "prompt ";
        }
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (File file : fileList){
            stringJoiner.add(output + "begin execute " + file.getAbsolutePath() + (DataBaseConst.MYSQL.equalsIgnoreCase(dbType)? "';":""));
            stringJoiner.add(separator + file.getAbsolutePath() + "\"");
            stringJoiner.add(output + "execute end: " + file.getAbsolutePath()+ (DataBaseConst.MYSQL.equalsIgnoreCase(dbType)? "';":""));
        }
        try {
            org.apache.commons.io.FileUtils.writeStringToFile(newScript, stringJoiner.toString(), "GBK", true);
        } catch (IOException e) {
            log.error("写入文件数据失败", e);
        }
    }
}
