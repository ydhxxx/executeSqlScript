package com.yudh.execute.utils;




import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static com.yudh.execute.utils.CloseStream.closed;


/**
 * @author yudh
 * @date 2022-03-19 18:13
 */
@Slf4j
public class FileUtils {

    /**
     * 创建文件
     *
     * @param file
     */
    public static void createNewFile(File file){
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if (file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            log.error("创建文件失败",e);
        }
    }

    /**
     * 将数据写入文件
     *
     * @param file
     * @param str
     */
    public static void writeTxt(File file, String str, boolean append){
        BufferedWriter bufferedWriter = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file,append),"utf-8");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(str+"\n");
        } catch (IOException e) {
            log.error("读取文件数据失败",e);
        } finally {
            closed(bufferedWriter);
            closed(outputStreamWriter);
        }
    }



    /**
     * 获取文件内容
     *
     * @param file
     * @return
     */
    public static String readFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String tempStr;
            while ((tempStr = bufferedReader.readLine())!=null){
                stringBuilder.append(tempStr).append(" ");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            log.error("读取文件内容失败",e);
        } finally {
            closed(bufferedReader);
            closed(inputStreamReader);
        }
        return null;
    }

    /**
     * 递归获取目录下所有sql脚本
     * @param file
     * @param list
     */
    public static void getAllFiles(File file, List<File> list){
        if (!file.exists()){
            return;
        }
        if (file.isFile()){
            int index = file.getName().lastIndexOf(".");
            String extName = file.getName().substring(index + 1);
            if (extName.equals("sql")){
                list.add(file);
            }
        }
        else {
            File[] files = file.listFiles();
            for (File newFile:files){
                if (newFile.isDirectory()){
                    //若是目录，则递归打印该目录下的文件
                    getAllFiles(newFile,list);
                }
                else if (newFile.isFile()){
                    int index = newFile.getName().lastIndexOf(".");
                    String extName = newFile.getName().substring(index + 1);
                    if (extName.equals("sql")){
                        list.add(newFile);
                    }

                }

            }
        }
    }

    /**
     * 获取当前目录下所有文件、目录（可直接简写）
     * @param file
     * @param list
     */
    public static void getCurrentFiles(File file, List<File> list){
        if (file.isDirectory()){
            File[] files = file.listFiles();
            list.addAll(Arrays.asList(files));
        }
    }



    /**
     * 递归删除所有文件
     * @param file
     */
    public static void deleteAllFiles(File file){
        if (!file.exists()){
            return;
        }
        if (!file.isFile()) {
            File[] files = file.listFiles();
            for (File newFile : files) {
                if (newFile.isDirectory()) {
                    deleteAllFiles(newFile);
                } else if (newFile.isFile()) {
                    newFile.delete();
                }
            }
        }
        // 删除文件
        file.delete();
    }


    /**
     * 获取jar所在根目录
     * @return
     */
    public static String getRootPath() {
        String path = FileUtils.class.getClassLoader().getResource("").getPath();
        if (StringUtils.contains(path, "BOOT-INF")) {
            path = path.substring("file:/".length() - 1, path.indexOf("/BOOT-INF/"));
            path = path.substring(0, path.lastIndexOf("/") + 1);
        }

        return path;
    }
}
