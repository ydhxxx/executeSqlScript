package com.yudh.execute.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

public static void release(ResultSet rs, Statement stmt, Connection conn) {
    if (rs != null) {
        try {
            rs.close();
        } catch (SQLException e) {
            log.error("resultSet关闭时报错：", e);
        }
        rs = null;
    }
    if (stmt != null) {
        try {
            stmt.close();
        } catch (SQLException e) {
            log.error("statement关闭时报错：", e);
        }
        stmt = null;
    }
    if (conn != null) {
        try {
            conn.close();
        } catch (SQLException e) {
            log.error("connection关闭时报错：", e);
        }
        conn = null;
    }
}

}
