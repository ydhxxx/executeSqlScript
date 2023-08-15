package com.yudh.execute.bean;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @Classname DruidDataSource
 * @Description TODO
 * @Date 2023/8/13 17:14
 * @Created by 叽里咕噜
 */
@Slf4j
public class DruidDataSourceBean {

    /**
     * 数据库连接对象
     */
    public static DruidDataSource dataSource;

    public static void setDataSource(DataSourceBean dataSourceBean){
        dataSource = new DruidDataSource();
        dataSource.setName("sourceDB连接池");
        dataSource.setDriverClassName(dataSourceBean.getDriverClassName());
        dataSource.setUrl(dataSourceBean.getUrl());
        dataSource.setUsername(dataSourceBean.getUserName());
        dataSource.setPassword(dataSourceBean.getPassWord());
        dataSource.setInitialSize(0);
        dataSource.setMaxActive(5);
        dataSource.setMinIdle(2);
        dataSource.setConnectionErrorRetryAttempts(5);
        dataSource.setBreakAfterAcquireFailure(true);
    }
}
