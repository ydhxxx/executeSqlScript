package com.yudh.execute.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author yudh
 * @date 2022-03-19 18:13
 */
@Slf4j
public class CloseStream {

    /**
     * 关闭各类文件输入输出流
     *
     * @param closeable
     */
    public static void closed(Closeable closeable){
        if (closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                log.error("IO失败",e);
            }

        }
    }
    public static void closeConnection(Connection connection){
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("数据库连接关闭失败",e);
        }
    }
}
