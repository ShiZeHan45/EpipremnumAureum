package com.gr.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @program: wwis-kunming
 * @description: 获得数据源工具类
 * @author: Shizh
 * @create: 2018-07-23 15:02
 **/
@Component
public class DataSourceUtil {
    //链接数据库的参数
    @Value("${jdbc.driverClass}")
    public String driver;
    @Value("${jdbc.url}")
    public String url;
    @Value("${jdbc.username}")
    public String userName;
    @Value("${jdbc.password}")
    public String userPs;

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPs() {
        return userPs;
    }

    //操作数据库的对象
    Statement sta;
    ResultSet rs;

    /**
     * 获得链接
     */
    public Connection getConnection() {
        try {
            Properties props = new Properties();
            props.put("remarksReporting", "true");
            props.put("user",userName);
            props.put("password",userPs);
            Class.forName(driver);
            return DriverManager.getConnection(url, props);
        } catch (SQLException | ClassNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭资源方法
     */
    public void closeRes(ResultSet resultSet,Statement sta,Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (sta != null) {
            try {
                sta.close();
            } catch (SQLException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
