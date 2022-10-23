package com.yudh.execute.utils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.yudh.execute.constant.ConstList.ZIP;


/**
 * @author yudh
 * @date 2022-03-19 18:13
 */
public class ZipFileUtils {


    /**
     * 压缩文件
     * @param targetFile
     * @throws ZipException
     */
    public static void zipAll(File targetFile, String sourceDirectory) throws ZipException {
        // 生成的压缩文件
        ZipFile zipFile = new ZipFile(targetFile);
        ZipParameters parameters = new ZipParameters();
        // 压缩方式
        parameters.setCompressionMethod(CompressionMethod.DEFLATE);
        // 压缩级别
        parameters.setCompressionLevel(CompressionLevel.NORMAL);
        // 要打包的文件夹
        File currentFile = new File(sourceDirectory);
        File[] fs = currentFile.listFiles();
        // 遍历test文件夹下所有的文件、文件夹
        for (File f : fs) {
            if (f.isDirectory()) {
                zipFile.addFolder(f, parameters);
            } else {
                zipFile.addFile(f, parameters);
            }
        }
    }

    /**
     * 解压文件
     * @param sourceFile
     * @param targetDirectory
     */
    public static void unzipAll(File sourceFile, String targetDirectory) throws ZipException{

        // 如果targetDirectory为空 说明是子文件夹 直接使用传进来的 sourceFile
        if (StringUtils.isBlank(targetDirectory)){
            targetDirectory = sourceFile.getAbsolutePath();
        }
        ZipFile zipFile = new ZipFile(sourceFile);
        if (isZipFile(targetDirectory)){
            targetDirectory = targetDirectory.substring(0, targetDirectory.length() - 4);
        }
        File parentFile = new File(targetDirectory);
        if (parentFile.exists()){
            FileUtils.deleteAllFiles(parentFile);
            parentFile.mkdirs();
        }
        zipFile.setCharset(Charset.forName("GBK"));

        // 解压
        zipFile.extractAll(targetDirectory);
        CloseStream.closed(zipFile);

        // 遍历
        List<File> fileList = new ArrayList<>();
        // 获取父目录下所有文件
        fileList = parentFile.isDirectory()? Arrays.asList(parentFile.listFiles()):fileList;
        for (File fileItem : fileList){
            if (fileItem.isFile()&& (isZipFile(fileItem.getAbsolutePath()))){
                unzipAll(fileItem,null);
            }
        }

    }

    /**
     * 判断文件路径是否包含zip
     * @param filePath
     * @return
     */
    public static boolean isZipFile(String filePath){
        return filePath.endsWith(ZIP.toLowerCase()) || filePath.endsWith(ZIP.toUpperCase());
    }


}
