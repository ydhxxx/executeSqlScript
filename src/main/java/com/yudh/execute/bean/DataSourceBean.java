package com.yudh.execute.bean;

import lombok.Data;

/**
 * @Classname DataSourceBean
 * @Description TODO
 * @Date 2023/8/13 17:19
 * @Created by 叽里咕噜
 */
@Data
public class DataSourceBean {

    /**
     * 驱动名称
     */
    private String driverClassName;
    /**
     * 数据库url
     */
    private String url;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 数据库密码
     */
    private String passWord;
}
