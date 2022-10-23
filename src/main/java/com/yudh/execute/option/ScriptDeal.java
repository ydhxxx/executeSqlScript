package com.yudh.execute.option;

import com.yudh.execute.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname ScriptDeal
 * @Description TODO
 * @Date 2022/10/23 21:21
 * @Created by 叽里咕噜
 */
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
        if (scriptFile.isDirectory()){
            parentDir = scriptFile.getAbsolutePath();
            FileUtils.getAllFiles(scriptFile,fileArrayList);

            list.add(parentDir);
            String fileName = System.currentTimeMillis() +".sql";
            File newScript = new File(parentDir+"\\"+fileName);
            writeScript(newScript,fileArrayList);
            list.add(fileName);
        }
        else {
            parentDir = scriptFile.getParentFile().getAbsolutePath();
            fileArrayList.add(scriptFile);
            list.add(parentDir);
            list.add(scriptFile.getName());
        }

        return list;
    }

    public static void writeScript(File newScript, List<File> fileList){
        for (File file : fileList){
            FileUtils.writeTxt(newScript,"@ "+file.getAbsolutePath(),true);
        }
    }
}
