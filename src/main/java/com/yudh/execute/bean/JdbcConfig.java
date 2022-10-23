package com.yudh.execute.bean;

import lombok.Data;

/**
 * @Classname Jdbc
 * @Description TODO
 * @Date 2022/10/17 21:59
 * @Created by 叽里咕噜
 */
@Data
public class JdbcConfig {

    /**
     * ip:port
     */
    private String ipPort;
    /**
     * 数据库名称
     */
    private String database;
    /**
     * 用户名
     */
    private String user;
    /**
     * 密码
     */
    private String password;

    public JdbcConfig(String ipPort, String database, String user, String password) {
        this.ipPort = ipPort;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public String getIp(){
        int index = this.ipPort.indexOf(":");
        return this.ipPort.substring(0,index);
    }

    public String getPort(){
        int index = this.ipPort.indexOf(":");
        return this.ipPort.substring(index+1);
    }

}
